package org.techtown.one.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.techtown.one.R
import org.techtown.one.board.*
import org.techtown.one.databinding.FragmentChatBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val TAG = ChatFragment::class.java.simpleName


    lateinit var myRef : DatabaseReference

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    lateinit var boardRVAdapter: BoardListLVAdapter

    private lateinit var category : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
//
//        boardRVAdapter = BoardListLVAdapter(boardDataList) //Adapter에 값을 넘겨준다.
//        binding.bookmarkListView.adapter = boardRVAdapter
//
//        myRef = FBRef.bookmarkRef.child(FBAuth.getUid())
//
//        binding.bookmarkListView.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(context, BoardInsideActivity::class.java)
//            intent.putExtra("key", boardKeyList[position])
//            startActivity(intent)
//        }
//
//        getFBBoardData()



        binding.homeTap.setOnClickListener{
            findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }

        binding.writeTap.setOnClickListener{
//            findNavController().navigate(R.id.action_chatFragment_to_writeFragment)
            activity?.let {
                val intent = Intent(context, BoardWriteActivity::class.java)
                intent.putExtra("category", "게시판 선택")
                startActivity(intent)
            }
        }

        binding.listTap.setOnClickListener{
            findNavController().navigate(R.id.action_chatFragment_to_listFragment)
        }

        binding.userTap.setOnClickListener{
            findNavController().navigate(R.id.action_chatFragment_to_userFragment)
        }

        return binding.root
    }

    //파이어베이스에서 데이터를 가져옴(받아옴)
//    private fun getFBBoardData(){
//        //파이어베이스에서 데이터를 가져옴(받아옴)
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                boardDataList.clear() //boardDataList초기화시킴(같은 데이터가 중복되어 들어가기 때문)
//
//                for (dataModel in dataSnapshot.children) {
//
//                    //BoarModel타입으로 데이터를 받아옴
//                    val item = dataModel.getValue(BoardModel::class.java)
//                    boardDataList.add(item!!)
//                    boardKeyList.add(dataModel.key.toString())
//
//                }
//
//
//                boardKeyList.reverse()
//                boardDataList.reverse()
//                //데이터 동기화
//                boardRVAdapter.notifyDataSetChanged()
//                Log.d(TAG, boardDataList.toString())
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
//            }
//        }
//
//        myRef.addValueEventListener(postListener)
//
//    }

//    private fun getBoardData(key : String){
//
//        //파이어베이스에서 데이터를 가져옴(받아옴)
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                try{
//                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
//                    Log.d(TAG, dataModel!!.title)
//
//                    category = dataModel!!.category
//
//                } catch (e: Exception){
//
//                }
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
//            }
//        }
//
//        FBRef.majorRef.child("board").child(category).child(key).addValueEventListener(postListener)
//
//    }


}