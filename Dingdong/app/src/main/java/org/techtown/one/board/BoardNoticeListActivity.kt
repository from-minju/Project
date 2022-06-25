package org.techtown.one.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
import org.techtown.one.databinding.ActivityBoardNoticeListBinding

class BoardNoticeListActivity : AppCompatActivity() {
    lateinit var myRef : DatabaseReference

    private val database = Firebase.database
    var majorRef = database.getReference(MainActivity.major)


    private lateinit var binding: ActivityBoardListBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardNoticeListActivity::class.java.simpleName

    lateinit var boardRVAdapter: BoardListLVAdapter

    private lateinit var board_category : String



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_board_notice_list)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_list)

        binding.categorySpinner.isVisible = true

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

        //게시판 선택 스피너
        var data_category =
            listOf("전체", "1학년", "2학년", "3학년", "4학년")
        var category_adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item, data_category)


        binding.categorySpinner.adapter = category_adapter
        binding.categorySpinner.setSelection(data_category.indexOf(category.toString()))

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                board_category = data_category.get(position)

                if(MainActivity.permission == "admin" || MainActivity.permission == "council"){
                    getFBNoticeBoardData(board_category)

                } else{
                    if(board_category == MainActivity.grade + "학년" || board_category == "전체"){
                        getFBNoticeBoardData(board_category)

                    }else {
                        Toast.makeText(baseContext, "해당 학년이 아닙니다.", Toast.LENGTH_LONG).show()
                    }
                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //글쓰기 버튼을 눌렀을 경우 -> BoardWriteActivity로 이동시킴
        binding.writeBtn.setOnClickListener{

            val intent = Intent(this, BoardWriteActivity::class.java)
            intent.putExtra("category", category.toString())
            startActivity(intent)

        }

        if(MainActivity.permission == "user") {
            binding.writeBtn.visibility = View.GONE
        }


    }

    private fun getFBNoticeBoardData(board_category : String){
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear() //boardDataList초기화시킴(같은 데이터가 중복되어 들어가기 때문)

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())

                    //BoarModel타입으로 데이터를 받아옴
                    val item = dataModel.getValue(BoardModel::class.java)
                    val category = item!!.category

                    if(board_category == "전체"){
                        if(category=="전체" || category == MainActivity.grade + "학년"){
                            boardDataList.add(item!!)
                            boardKeyList.add(dataModel.key.toString())
                        }
                    }else {
                        if(category == board_category){
                            boardDataList.add(item!!)
                            boardKeyList.add(dataModel.key.toString())
                        }
                    }

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

        myRef.addValueEventListener(postListener)

    }

}