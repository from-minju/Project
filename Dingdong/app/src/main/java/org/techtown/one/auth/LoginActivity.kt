package org.techtown.one.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.databinding.ActivityLoginBinding
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    private val database = Firebase.database

    private lateinit var result : String

    private lateinit var key : String



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()



            //로그인
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        //파이어베이스에서 데이터를 가져옴(받아옴)
                        val postListener = object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val dataModel = dataSnapshot.getValue(UserModel::class.java)

                                try{
                                    var permission = dataModel!!.permission

                                    if(permission == "user" || permission == "council" || permission == "admin"){

                                        val intent = Intent(baseContext, MainActivity::class.java)
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        startActivity(intent)


                                    } else if(permission == "nonmember"){
                                        Toast.makeText(baseContext, "회원 승인이 되지 않았습니다.", Toast.LENGTH_LONG).show()

                                    }else{

                                    }

                                } catch (e: Exception){
                                    Log.d("LoginActivity", "메인액티비")
                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.w("LoginActivity", "LoadPost:onCanceled", databaseError.toException())
                            }
                        }



//이메일 인증 필요
                        if(auth.currentUser?.isEmailVerified!!){
                            database.getReference("user").child(FBAuth.getUid()).addValueEventListener(postListener)

                        }else{
                            Toast.makeText(this, "이메일을 인증해주세요.", Toast.LENGTH_LONG).show()

                        }



//이메일 인증 안하는 경우
                        database.getReference("user").child(FBAuth.getUid()).addValueEventListener(postListener)








                    } else {
                        Log.d("login","failed")

                        // If sign in fails
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }


        }
    }

}