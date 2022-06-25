package org.techtown.one.utils

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.one.MainActivity
import java.lang.Exception


class FBRef {
    companion object{

        private val database = Firebase.database


        var majorRef = database.getReference(MainActivity.major)
//        var gradeRef = database.getReference(MainActivity.grade)
        var bookmarkRef = database.getReference(MainActivity.major).child("bookmark_list")

        val userRef = database.getReference("user")


    }

}