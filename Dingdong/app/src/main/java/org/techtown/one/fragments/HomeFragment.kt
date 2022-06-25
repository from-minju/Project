package org.techtown.one.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.auth.UserModel
import org.techtown.one.board.*
import org.techtown.one.databinding.FragmentHomeBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import org.techtown.one.utils.FBRef.Companion.majorRef
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding


    private val TAG = HomeFragment::class.java.simpleName

    lateinit var noticeRef : DatabaseReference

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    lateinit var boardRVAdapter: BoardListLVAdapter

    lateinit var freeRef : DatabaseReference

    private val freeDataList = mutableListOf<BoardModel>()
    private val freeKeyList = mutableListOf<String>()
    lateinit var freeRVAdapter: BoardListLVAdapter


    private val database = Firebase.database



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HomeFragment", "onCreateView")
        Log.d("HomeFragment", MainActivity.major)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        boardRVAdapter = BoardListLVAdapter(boardDataList) //Adapter에 값을 넘겨준다.
        binding.boardListView.adapter = boardRVAdapter

        noticeRef = database.getReference(MainActivity.major).child("board").child("공지 게시판")

        binding.boardListView.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            intent.putExtra("category", "공지 게시판")
            startActivity(intent)
        }

        getFBNoticeBoardData()





        freeRVAdapter = BoardListLVAdapter(freeDataList)
        binding.freeListView.adapter = freeRVAdapter

        freeRef = database.getReference(MainActivity.major).child("board").child("자유 게시판")

        binding.freeListView.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", freeKeyList[position])
            intent.putExtra("category", "자유 게시판")
            startActivity(intent)
        }

        getFBFreeBoardData()



        //URL 버튼
        binding.univBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bible.ac.kr/"))
            startActivity(intent)
        }
        binding.lmsBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://lms.bible.ac.kr/"))
            startActivity(intent)
        }
        binding.intranetBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kbuis.bible.ac.kr/ble_login3.aspx"))
            startActivity(intent)
        }
        binding.ainaviBtn.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ainavi.bible.ac.kr/"))
            startActivity(intent)
        }


        //하단 바
        binding.writeTap.setOnClickListener{
            activity?.let {
                val intent = Intent(context, BoardWriteActivity::class.java)
                intent.putExtra("category", "게시판 선택")
                startActivity(intent)
            }
        }

        binding.listTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_listFragment)

        }

        binding.chatTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)

        }

        binding.userTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_userFragment)

        }

        return binding.root
    }

    private fun getMajorData(key : String) {
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(UserModel::class.java)

                try{
                    MainActivity.major = dataModel!!.major

                } catch (e: Exception){
                    Log.d(TAG, "메인액티비")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }
        FBRef.userRef.child(key).addValueEventListener(postListener)

    }


    private fun getFBFreeBoardData(){
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                freeDataList.clear() //boardDataList초기화시킴(같은 데이터가 중복되어 들어가기 때문)

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())

                    //BoarModel타입으로 데이터를 받아옴
                    val item = dataModel.getValue(BoardModel::class.java)
                    freeDataList.add(item!!)
                    freeKeyList.add(dataModel.key.toString())
                }


                freeKeyList.reverse()
                freeDataList.reverse()
                //데이터 동기화
                freeRVAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }

        freeRef.addValueEventListener(postListener)
    }

    private fun getFBNoticeBoardData(){
        Log.d(TAG, "시작 ")

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

        noticeRef.addValueEventListener(postListener)
    }



}