package org.techtown.one.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.board.BoardListActivity
import org.techtown.one.board.BoardNoticeListActivity
import org.techtown.one.board.BoardWriteActivity
import org.techtown.one.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        binding.boardStudentCouncil.visibility = View.GONE

        if(MainActivity.permission == "council"){
            binding.boardStudentCouncil.visibility = View.VISIBLE

        }

        //공지게시판 클릭
        binding.boardNotice.setOnClickListener {
            val intent = Intent(context, BoardNoticeListActivity::class.java)
            intent.putExtra("category", "공지 게시판")
            startActivity(intent)
        }

        //자유게시판 클릭
        binding.boardFree.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "자유 게시판")
            startActivity(intent)
        }

        //1학년 게시판 클릭
        binding.boardGrade1.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "1학년")
            startActivity(intent)
        }

        //2학년 게시판 클릭
        binding.boardGrade2.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "2학년")
            startActivity(intent)
        }

        //3학년 게시판 클릭
        binding.boardGrade3.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "3학년")
            startActivity(intent)
        }

        //4학년 게시판 클릭
        binding.boardGrade4.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "4학년")
            startActivity(intent)
        }

        //QnA 게시판 클
        binding.boardQna.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "Q&A")
            startActivity(intent)
        }

        //중고마켓게시판 클릭
        binding.boardMarket.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "중고 마켓")
            startActivity(intent)
        }

        //학생회게시판 클릭
        binding.boardStudentCouncil.setOnClickListener{
            val intent = Intent(context, BoardListActivity::class.java)
            intent.putExtra("category", "학생회")
            startActivity(intent)
        }





        binding.homeTap.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_homeFragment)
        }

        binding.writeTap.setOnClickListener{
//            findNavController().navigate(R.id.action_listFragment_to_writeFragment)
            activity?.let {
                val intent = Intent(context, BoardWriteActivity::class.java)
                intent.putExtra("category", "게시판 선택")
                startActivity(intent)
            }
        }

        binding.chatTap.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_chatFragment)
        }

        binding.userTap.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_userFragment)
        }

        return binding.root
    }

}