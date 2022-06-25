package org.techtown.one.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.techtown.one.MainActivity
import org.techtown.one.R
import org.techtown.one.auth.UserModel
import org.techtown.one.board.BoardEditActivity
import org.techtown.one.board.BoardModel
import org.techtown.one.databinding.ActivityBoardEditBinding
import org.techtown.one.databinding.ActivityNicknameEditBinding
import org.techtown.one.fragments.UserFragment
import org.techtown.one.utils.FBAuth
import org.techtown.one.utils.FBRef
import org.techtown.one.utils.FBRef.Companion.userRef

class NicknameEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNicknameEditBinding

    private val TAG = NicknameEditActivity::class.java.simpleName

    private var key = FBAuth.getUid()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_nickname_edit)


        getNickName(key)

        //수정버튼 누르면
        binding.editBtn.setOnClickListener {
            editNickName(key)
        }


    }

    private fun editNickName(key : String){
        FBRef.userRef.child(key).child("nickName").setValue(binding.nicknameArea.text.toString())
        MainActivity.nickName = binding.nicknameArea.text.toString()

        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()

    }


    private fun getNickName(key : String){

        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(UserModel::class.java)

                binding.nicknameArea.setText(dataModel?.nickName)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }

        userRef.child(key).addValueEventListener(postListener)
    }
}