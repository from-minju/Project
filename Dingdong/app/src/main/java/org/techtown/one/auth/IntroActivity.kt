package org.techtown.one.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.techtown.one.R
import org.techtown.one.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        binding.loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.joinBtn.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

    }
}