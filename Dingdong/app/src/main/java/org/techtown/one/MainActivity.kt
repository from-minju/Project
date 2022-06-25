package org.techtown.one

import android.content.Intent
import android.net.ipsec.ike.exceptions.InvalidMajorVersionException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.ktx.Firebase
import org.techtown.one.auth.IntroActivity
import org.techtown.one.auth.JoinActivity
import org.techtown.one.auth.UserModel
import org.techtown.one.fragments.HomeFragment
import org.techtown.one.setting.SettingActivity
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database

    private val TAG = MainActivity::class.java.simpleName

    companion object {

        var name : String = ""
        var studentId : String =""
        var major : String = ""
        var nickName : String = ""
        var grade : String = ""
        var permission : String = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val uid = FBAuth.getUid()

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //로그아웃
        findViewById<ImageView>(R.id.settingBtn).setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        getUserData(uid)


    }



    private fun getUserData(key : String) {
        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(UserModel::class.java)

                try{

                    name = dataModel!!.name
                    studentId = dataModel!!.studentId
                    nickName = dataModel!!.nickName
                    major = dataModel!!.major
                    grade = dataModel!!.grade
                    permission = dataModel!!.permission


                    if(major.equals("컴퓨터소프트웨어학과")) {
//                        Toast.makeText(baseContext, "컴퓨터소프트웨어학과", Toast.LENGTH_SHORT).show()
                        FBRef.majorRef = database.getReference("컴퓨터소프트웨어학과")

                    } else if(major.equals("간호학과")) {
//                        Toast.makeText(baseContext, "간호학과", Toast.LENGTH_SHORT).show()
                        FBRef.majorRef = database.getReference("간호학과")

                    } else if(major.equals("성서학과")) {
//                        Toast.makeText(baseContext, "성서학과", Toast.LENGTH_SHORT).show()
                        FBRef.majorRef = database.getReference("성서학과")

                    } else if(major.equals("사회복지학과")) {
//                        Toast.makeText(baseContext, "사회복지학과", Toast.LENGTH_SHORT).show()
                        FBRef.majorRef = database.getReference("사회복지학과")

                    } else if(major.equals("영유아보육학과")) {
//                        Toast.makeText(baseContext, "영유아보육학과", Toast.LENGTH_SHORT).show()
                        FBRef.majorRef = database.getReference("영유아보육학과")
                    }
                    else {
                        Toast.makeText(baseContext, "학과 없음", Toast.LENGTH_LONG).show()
                    }

                    //상단 바에 전공 표시하기
                    findViewById<TextView>(R.id.main_title).setText(major)

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


}