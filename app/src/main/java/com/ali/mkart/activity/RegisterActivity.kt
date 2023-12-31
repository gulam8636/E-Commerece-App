package com.ali.mkart.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ali.mkart.R
import com.ali.mkart.databinding.ActivityRegisterBinding
import com.ali.mkart.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button4.setOnClickListener {
           openLogin()
        }
        binding.button3.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
      if (binding.userName.text!!.isEmpty() || binding.userNumber.text!!.isEmpty()){
          Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show()
      }
        else{
            storeData()
      }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun storeData() {
       val builder = AlertDialog.Builder(this)
           .setTitle("Loading.....")
           .setMessage("Please wait")
           .setCancelable(false)
           .create()
           builder.show()
        val preference = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putString("number", binding.userNumber.text.toString())
        editor.putString("name",binding.userName.text.toString())
        editor.apply()
        val data = UserModel(userName = binding.userName.text.toString(), userPhoneNumber = binding.userNumber.text.toString())
        Firebase.firestore.collection("users").document(binding.userNumber.text.toString())
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "User registered.", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}