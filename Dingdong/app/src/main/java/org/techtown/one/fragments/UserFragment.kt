package org.techtown.one.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import org.techtown.one.MainActivity
import org.techtown.one.MainActivity.Companion.major
import org.techtown.one.R
import org.techtown.one.admin.ApproveCouncilActivity
import org.techtown.one.admin.ApproveMemberActivity
import org.techtown.one.auth.IntroActivity
import org.techtown.one.board.BoardEditActivity
import org.techtown.one.board.BoardWriteActivity
import org.techtown.one.databinding.FragmentUserBinding
import org.techtown.one.setting.ApplyCouncilActivity
import org.techtown.one.setting.NicknameEditActivity
import org.techtown.one.utils.FBRef


class UserFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onResume() {
        super.onResume()

        //닉네임 수정 액티비티 -> 다시 user페이지로 돌아왔을 때 바뀐 닉네임으로 초기화시켜주기 위해
        binding.nickName.setText(MainActivity.nickName)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.nickName.setText(MainActivity.nickName)
        binding.idName.setText(MainActivity.studentId + " " + MainActivity.name)
        binding.major.setText(MainActivity.major)
        binding.approveJoinBtn.visibility = View.GONE
        binding.approveCouncilBtn.visibility = View.GONE


        auth = Firebase.auth //초기화를 시켜

        //관리자 계정으로 로그인을 했을 경우 - 회원승인 버튼 노출시키기
        if(MainActivity.permission == "admin"){
            binding.applyCouncilBtn.visibility = View.GONE

            binding.approveJoinBtn.visibility = View.VISIBLE
            binding.approveCouncilBtn.visibility = View.VISIBLE
        }

        //사용자가 학생회 권한인 경우
        if(MainActivity.permission == "council"){
            binding.applyCouncilBtn.visibility = View.GONE
        }

        //로그아웃 버튼
        binding.logoutBtn.setOnClickListener {

            auth.signOut()

            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()

            val intent = Intent(context, IntroActivity::class.java)

            //기존 액티비티들을 모두 지움(로그아웃 후 뒤로가기 했을 때 이동 못하도록)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        //닉네임 변경 버튼
        binding.changeNickNameBtn.setOnClickListener {
            val intent = Intent(context, NicknameEditActivity::class.java)
            startActivity(intent)
        }

        //학생회 신청 버튼
        binding.applyCouncilBtn.setOnClickListener{

            val intent = Intent(context, ApplyCouncilActivity::class.java)
            intent.putExtra("major", major)
            startActivity(intent)
        }

        //관리자 - 회원 승인받는 버튼
        binding.approveJoinBtn.setOnClickListener{
            val intent = Intent(context, ApproveMemberActivity::class.java)
            intent.putExtra("major", major)
            startActivity(intent)
        }

        //관리자 - 학생회 승인받는 버튼
        binding.approveCouncilBtn.setOnClickListener{
            val intent = Intent(context, ApproveCouncilActivity::class.java)
            intent.putExtra("major", major)
            startActivity(intent)
        }







        binding.homeTap.setOnClickListener{
            findNavController().navigate(R.id.action_userFragment_to_homeFragment)
        }

        binding.writeTap.setOnClickListener{
//            findNavController().navigate(R.id.action_userFragment_to_writeFragment)

            activity?.let {
                val intent = Intent(context, BoardWriteActivity::class.java)
                intent.putExtra("category", "게시판 선택")
                startActivity(intent)
            }
        }

        binding.listTap.setOnClickListener{
            findNavController().navigate(R.id.action_userFragment_to_listFragment)
        }

        binding.chatTap.setOnClickListener{
            findNavController().navigate(R.id.action_userFragment_to_chatFragment)
        }

        return binding.root
    }



}