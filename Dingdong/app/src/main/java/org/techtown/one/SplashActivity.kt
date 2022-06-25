package org.techtown.one

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.techtown.one.auth.IntroActivity
import org.techtown.one.auth.LoginActivity
import org.techtown.one.auth.UserModel
import org.techtown.one.board.BoardInsideActivity
import org.techtown.one.fragments.HomeFragment
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        if(auth.currentUser?.uid == null){
            //로그인이 되어있지 않은 경우
            Log.d("SplashActivity", "null")

            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 1500)

        } else {
            //로그인이 되어있는 경우
            Log.d("SplashActivity", "not null")

            getMajorData(FBAuth.getUid())
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                finish()
            }, 1500)


        }

    }

    private fun getMajorData(key : String) {
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(UserModel::class.java)

                try{
                    MainActivity.major = dataModel!!.major

                } catch (e: Exception){
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.userRef.child(key).addValueEventListener(postListener)

    }


}