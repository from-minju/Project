package org.techtown.one.setting

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity.Companion.major
import org.techtown.one.MainActivity.Companion.name
import org.techtown.one.MainActivity.Companion.studentId
import org.techtown.one.R
import org.techtown.one.auth.MemberModel
import org.techtown.one.utils.FBAuth

class ApplyCouncilActivity : AppCompatActivity() {

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showDialog()

    }

    private fun showDialog(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.council_apply_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("학생회 권한 신청")

        val alertDialog = mBuilder.show()
        //신청 버튼을 누른 경우
        alertDialog.findViewById<Button>(R.id.applyBtn)?.setOnClickListener{
            Toast.makeText(this, "신청되었습니다.", Toast.LENGTH_LONG).show()

            database.getReference(major)
                .child("apply_council")
                .child(FBAuth.getUid())
                .setValue(MemberModel(name, studentId))

            finish()
        }

    }
}