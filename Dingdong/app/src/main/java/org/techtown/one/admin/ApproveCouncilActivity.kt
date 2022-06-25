package org.techtown.one.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.R
import org.techtown.one.auth.MemberModel
import org.techtown.one.databinding.ActivityApproveMemberBinding
import org.techtown.one.utils.FBRef

class ApproveCouncilActivity : AppCompatActivity() {
    private lateinit var myRef : DatabaseReference

    private val database = Firebase.database

    private lateinit var binding : ActivityApproveMemberBinding

    private val memberDataList = mutableListOf<MemberModel>()
    private val memberKeyList = mutableListOf<String>()

    private lateinit var major : String

    private val TAG = ApproveMemberActivity::class.java.simpleName

    lateinit var memberRVAdapter : MemberLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve_member)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_approve_member)

        memberRVAdapter = MemberLVAdapter(memberDataList)
        binding.memberListView.adapter = memberRVAdapter

        major = intent.getStringExtra("major").toString()

        myRef = FBRef.majorRef.child("apply_council")



        //리스트 하나를 클릭하면
        binding.memberListView.setOnItemClickListener{parent, view, position, id ->
            //아이템을 눌렀을 때 - 승인 dialog

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.member_approve_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("학생회 권한 승인")

            val alertDialog = mBuilder.show()
            //승인 버튼을 누르면
            alertDialog.findViewById<Button>(R.id.approveBtn)?.setOnClickListener{
                Toast.makeText(this, "승인하였습니다.", Toast.LENGTH_LONG).show()

                //applay_member/council 에서 삭제
                myRef.child(memberKeyList[position]).removeValue()

                FBRef.userRef.child(memberKeyList[position]).child("permission").setValue("council")

                finish()
                finish()


            }
        }

        getFBMemberData()

    }


    private fun getFBMemberData(){

        //파이어베이스에서 데이터를 가져옴(받아옴)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                memberDataList.clear() //boardDataList초기화시킴(같은 데이터가 중복되어 들어가기 때문)

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())

                    //BoarModel타입으로 데이터를 받아옴
                    val item = dataModel.getValue(MemberModel::class.java)
                    memberDataList.add(item!!)
                    memberKeyList.add(dataModel.key.toString())
                }

                memberKeyList.reverse()
                memberDataList.reverse()

                //데이터 동기화
                memberRVAdapter.notifyDataSetChanged()
                Log.d(TAG, memberDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", databaseError.toException())
            }
        }

        myRef.addValueEventListener(postListener)

    }
}