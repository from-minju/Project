package org.techtown.one.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.databinding.ActivityBoardWriteBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import org.techtown.one.utils.FBRef.Companion.majorRef
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class BoardWriteActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    private var isImageUpload = false

    val storage = Firebase.storage

    private lateinit var board_category : String //db의 category항목에 들어갈 값

    private var isGoToWrite = true

    private lateinit var nickName : String

//    private lateinit var data_category : List<String>
//    private lateinit var category_adapter : ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        var category = intent.getStringExtra("category").toString() //게시판 스피너에서 선택된 값
//        val intent = Intent(this, BoardNoticeWriteActivity::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)


        //게시판 선택 스피너
        var data_category =
            listOf("게시판 선택", "공지 게시판", "자유 게시판", "1학년", "2학년", "3학년", "4학년", "Q&A","중고 마켓", "학생회")
        var category_adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item, data_category)
        binding.categorySpinner.adapter = category_adapter
        binding.categorySpinner.setSelection(data_category.indexOf(category))

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                binding.noticeCategorySpinner.visibility = View.GONE
                category = data_category.get(position)

                //공지 게시판에 글을 쓰는 경우
                if(category.equals("공지 게시판")){

                    //관리자 계정이 맞다면
                    if(MainActivity.permission == "admin" || MainActivity.permission == "council"){

                        binding.noticeCategorySpinner.visibility = View.VISIBLE
                        nickName = MainActivity.name //공지게시판은 실명으로 등록된다.

                        //게시판 선택 스피너
                        var data_category = listOf("전체", "1학년", "2학년", "3학년", "4학년")
                        var category_adapter = ArrayAdapter<String>(baseContext, R.layout.simple_spinner_item, data_category)

                        binding.noticeCategorySpinner.adapter = category_adapter
                        binding.noticeCategorySpinner.setSelection(data_category.indexOf(category))

                        binding.noticeCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                                board_category = data_category.get(position)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }
                    }
                    //공지게시판에 관리자 아닌 사람이 글을 쓰는 경우
                    else {
                        finish()
                        Toast.makeText(baseContext, "관리자만 글쓰기가 가능합니다.", Toast.LENGTH_LONG).show()
                    }

                }
                //학생회가 아닌 사용자가 학생회 게시판을 누른 경우
                else if(category.equals("학생회")){
                    nickName = MainActivity.name //실명으로 등록된다.
                    board_category = ""


                    if (MainActivity.permission != "council"){
                        finish()
                        Toast.makeText(baseContext, "학생회만 글쓰기가 가능합니다.", Toast.LENGTH_LONG).show()

                    }
                }
                //공지게시판 외 다른 게시판을 선택했을 때
                else {
//                    board_category = data_category.get(position)
                    nickName = MainActivity.nickName

                    board_category = ""

                }

                myRef = majorRef.child("board").child(category)



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        //등록 버튼을 눌렀을 경우
        binding.writeBtn.setOnClickListener{

            isGoToWrite = true

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            val key = myRef.push().key.toString()

            if(board_category.equals("게시판 선택")){
                isGoToWrite = false
                Toast.makeText(this, "게시판을 선택하세요", Toast.LENGTH_LONG).show()
            }

            if(isGoToWrite){
                myRef
                    .child(key)
                    .setValue(BoardModel(title, content, uid, nickName, time, board_category))

                Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()

                if(isImageUpload == true) {
                    imageUpload(key)
                }

                finish()
            }


        }


        //이미지 추가 버튼 눌렀을 때
        binding.picBtn.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }
    }


    private fun imageUpload(key : String){
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")
        val imageView = binding.imageArea
        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode==100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }

}
