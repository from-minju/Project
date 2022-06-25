package org.techtown.one.board

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.comment.CommentLVAdapter
import org.techtown.one.comment.CommentModel
import org.techtown.one.databinding.ActivityBoardInsideBinding
import org.techtown.one.databinding.ActivityBoardListBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity(){
    lateinit var myRef : DatabaseReference


    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key : String

    private lateinit var category : String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    private lateinit var commentRef : DatabaseReference

    private lateinit var isBookmarked : String

    val bookmarkIdList = mutableListOf<String>()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener{
            showDialog()
        }



        //방법2 : 파이어베이스에 있는 board에 있는 데이터의 id를 기반으로 불러오는
        key = intent.getStringExtra("key").toString()
        getBoardData(key)

        commentRef = FBRef.majorRef.child("comment").child(category).child(key)

        binding.commentBtn.setOnClickListener{
            insertComment(key)
        }

        getCommentData(key)

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

        getImageData(key)



        //북마크 기능
        getBookmarkData()

        //북마크 버튼을 누른 경우
        binding.bookmarkBtn.setOnClickListener{

            //북마크되어있으면
            if(isBookmarked == "true"){
                FBRef.bookmarkRef
                    .child(FBAuth.getUid())
                    .child(key)
                    .removeValue()
            }
            //북마크 되어있지 않으면
            else if(isBookmarked == "false"){
                FBRef.bookmarkRef
                    .child(FBAuth.getUid())
                    .child(key)
                    .setValue(BookmarkModel("true"))
            }

        }


    }

    //북마크가 되어있는지 확인
    private fun getBookmarkData(){

        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }

                if(bookmarkIdList.contains(key)){
                    //북마크가 되어있음
                    isBookmarked = "true"
                    binding.bookmarkBtn.setImageResource(R.drawable.bookmark_color)

                }else {
                    //북마크가 되어있지 않음
                    isBookmarked = "false"
                    binding.bookmarkBtn.setImageResource(R.drawable.bookmark_white)
                }
//                isBookmarked= dataModel?.bookmarkIsTrue.toString()
//
//                //북마크가 있으면
//                if(isBookmarked == "true") {
//                    binding.bookmarkBtn.setImageResource(R.drawable.bookmark_color)
//
//                }
//                //북마크 안되어 있으면
//                else if(isBookmarked == "false"){
//                    binding.bookmarkBtn.setImageResource(R.drawable.bookmark_white)
//
//                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)



    }


    fun getCommentData(key : String) {
        val postListener =object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }

                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        commentRef.addValueEventListener(postListener)
    }

    fun insertComment(key : String) {
        //comment
        //  > Board의 키값
        //      > 자동생성되는 Comment 키값
        //          >> comment data

        commentRef
            .push()
            .setValue(
                CommentModel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime(),
                    MainActivity.nickName
                )
            )

        binding.commentArea.setText("")
    }


    private fun showDialog(){

        category = intent.getStringExtra("category").toString()

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        //수정
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener{
            Toast.makeText(this, "수정 버튼을 눌렀습니다.", Toast.LENGTH_LONG).show()

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            intent.putExtra("category", category)
            startActivity(intent)

        }
        //삭제
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener{
            FBRef.majorRef.child("board").child(category).child(key).removeValue()
            FBRef.majorRef.child("comment").child(category).child(key).removeValue() //게시물 삭제 시 댓글도 함께 삭

            Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show()
            finish()
        }

    }


    private fun getImageData(key : String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {
                binding.getImageArea.isVisible = true

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {
                binding.getImageArea.isVisible = false
            }
        })
    }


    private fun getBoardData(key : String){

        category = intent.getStringExtra("category").toString()


        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try{
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG, dataModel!!.title)

                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time
                    binding.nickNameArea.text = dataModel!!.nickName
                    binding.categoryArea.text = category

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)) {
//                        Toast.makeText(baseContext, "내가 글쓴이임", Toast.LENGTH_LONG).show()
                        binding.boardSettingIcon.isVisible = true
                    } else {
//                        Toast.makeText(baseContext, "내가 쓴 글 아님 ", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception){
                    Log.d(TAG, "삭제 완료")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }

        FBRef.majorRef.child("board").child(category).child(key).addValueEventListener(postListener)

    }
}