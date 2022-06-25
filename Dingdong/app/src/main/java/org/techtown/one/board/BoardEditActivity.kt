package org.techtown.one.board

import android.os.Bundle
import android.os.PersistableBundle
import android.os.health.UidHealthStats
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.databinding.ActivityBoardEditBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception

class BoardEditActivity : AppCompatActivity(){
    private val database = Firebase.database
    var majorRef = database.getReference(MainActivity.major)

    private lateinit var key : String

    private lateinit var binding: ActivityBoardEditBinding

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    private lateinit var category : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_board_edit)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener{
            editBoardData(key)
        }
    }

    private fun editBoardData(key : String){
        category = intent.getStringExtra("category").toString()

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        val uid = FBAuth.getUid()
        var nickName = MainActivity.nickName
        val time = FBAuth.getTime()

        if(category == "학생회" || category == "공지 게시판"){
            nickName = MainActivity.name
        }

        FBRef.majorRef.child("board")
            .child(category)
            .child(key)
            .setValue(BoardModel(title, content, uid, nickName, time, category))
        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()
    }


    private fun getImageData(key : String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {

            }
        })

    }



    private fun getBoardData(key : String){
        category = intent.getStringExtra("category").toString()
        binding.categoryArea.setText(category)

        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
                writerUid = dataModel!!.uid

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }
        majorRef.child("board").child(category).child(key).addValueEventListener(postListener)
    }
}