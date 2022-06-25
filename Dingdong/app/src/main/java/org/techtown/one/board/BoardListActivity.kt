package org.techtown.one.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.databinding.ActivityBoardListBinding

class BoardListActivity : AppCompatActivity(){

    lateinit var myRef : DatabaseReference

    private val database = Firebase.database
    var majorRef = database.getReference(MainActivity.major)


    private lateinit var binding: ActivityBoardListBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardListActivity::class.java.simpleName

    lateinit var boardRVAdapter: BoardListLVAdapter



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_list)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_list)

        boardRVAdapter = BoardListLVAdapter(boardDataList) //Adapter에 값을 넘겨준다.
        binding.boardListView.adapter = boardRVAdapter


        val category = intent.getStringExtra("category")
        binding.categoryArea.setText(category.toString())

        myRef = majorRef.child("board").child(category.toString())

        binding.boardListView.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(this, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            intent.putExtra("category", category)
            startActivity(intent)
        }

        //공지게시판일 경우 관리자와 학회에게만 글쓰기 버튼 노출
        if(category == "공지 게시판"){
            binding.writeBtn.visibility = View.GONE

            if(MainActivity.permission == "admin" || MainActivity.permission == "council"){
                binding.writeBtn.visibility = View.VISIBLE
            }
        }


        //글쓰기 버튼을 눌렀을 경우 -> BoardWriteActivity로 이동시킴
        binding.writeBtn.setOnClickListener{

            val intent = Intent(this, BoardWriteActivity::class.java)
            intent.putExtra("category", category.toString())
            startActivity(intent)

        }


        getFBBoardData()


    }





    //파이어베이스에서 데이터를 가져옴(받아옴)
    private fun getFBBoardData(){
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear() //boardDataList초기화시킴(같은 데이터가 중복되어 들어가기 때문)

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())

                    //BoarModel타입으로 데이터를 받아옴
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }


                boardKeyList.reverse()
                boardDataList.reverse()
                //데이터 동기화
                boardRVAdapter.notifyDataSetChanged()
                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }

//        FBRef.boardRef.addValueEventListener(postListener)
        myRef.addValueEventListener(postListener)

    }



}