package com.ali.mkart.model

import java.sql.DataTruncation

data class UserModel(
   val userName : String? = "",
  val userPhoneNumber : String? = "",
  val village  : String?="",
   val state : String?="",
   val city : String? ="",
   val pincode :String?="",


)
