package org.techtown.one.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.board.BoardModel
import org.techtown.one.databinding.ActivityJoinBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef

class JoinActivity : AppCompatActivity() {


    private lateinit var major : String

    private lateinit var grade : String

    private var isGoToJoin = true

    private val database = Firebase.database

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    private val TAG = JoinActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //전공 선택 스피너
        var data_major = listOf("전공", "성서학과", "사회복지학과", "영유아보육학과", "컴퓨터소프트웨어학과", "간호학과")
        var major_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_major)
        binding.userMajor.adapter = major_adapter

        binding.userMajor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                major = data_major.get(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //학년 선택 스피너
        var data_grade = listOf("학년", "1", "2", "3", "4")
        var grade_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_grade)
        binding.userGrade.adapter = grade_adapter

        binding.userGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                grade = data_grade.get(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        binding.joinBtn.setOnClickListener{

            isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()
            val name = binding.userName.text.toString()
            val studentId = binding.userStudentID.text.toString()
            val nickName = binding.userNickName.text.toString()


            //값이 비어있는지 확인
            if(email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

//            //학교 이메일을 사용했는지 확인
//            if(!email.contains("@bible.ac.kr")){
//                Toast.makeText(this, "한국성서대학교 이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
//                isGoToJoin = false
//            }

            if(password1.isEmpty()){
                Toast.makeText(this, "Password1을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(password2.isEmpty()){
                Toast.makeText(this, "Password2를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            //비밀번호 2개가 같은지 확인
            if(!password1.equals(password2)){
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            //비밀번호는 6자리가 넘어야함
            if(password1.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(name.isEmpty() || nickName.isEmpty() || major.equals("전공") || grade.equals("학년")) {
                Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }


            if(isGoToJoin){
                //신규사용자 가입
                auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        FBRef.userRef
                            .child(FBAuth.getUid())
                            .setValue(UserModel(name, studentId, nickName, major, grade, "nonmember"))

                        database.getReference(major)
                            .child("apply_member")
                            .child(FBAuth.getUid())
                            .setValue(MemberModel(name, studentId))



//사용자에게 인증메일 보내기
                        val user = Firebase.auth.currentUser

                        user!!.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Email sent.")
                                    Toast.makeText(this, "이메일 인증을 완료해주세요", Toast.LENGTH_LONG).show()


                                } else {
                                    Log.d(TAG, "Email sending failed")
                                }
                            }

//



                        // Sign in success
//                        Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show()

                        auth.signOut() //이메일 인증을 받은  메인화면으로 넘어가기 위해 로그인 정보를 지운다.

                        val intent = Intent(this, IntroActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)



                    } else {
                        // If sign in fails
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                    }

                }

            }
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

    }
}