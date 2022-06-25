package org.techtown.one.auth

data class UserModel(
    val name : String = "",
    val studentId : String = "",
    val nickName : String = "",
    val major : String = "",
    val grade : String = "",
    val permission : String = ""  //user, council, admin
)