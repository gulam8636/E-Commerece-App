package com.ali.mkart.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ali.mkart.R
import com.ali.mkart.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAddressBinding
    private lateinit var preference: SharedPreferences
    private lateinit var totalCost : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

         preference = this.getSharedPreferences("user", MODE_PRIVATE)
        binding.proceed.setOnClickListener {
            validateData( binding.userName.text.toString(),binding.userNubmber.text.toString()
            ,binding.userVillage.text.toString(),binding.userCity.text.toString(),
            binding.userState.text.toString(),binding.userPincode.text.toString())
        }
        totalCost = intent.getStringExtra("totalCoast")!!
        loadUserInfo()
    }

    private fun validateData(name: String,  number: String, village: String, city: String, state: String, pincode: String) {
        if (name.isEmpty() || number.isEmpty() || village.isEmpty() || city.isEmpty() || state.isEmpty() || pincode.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
    }
        else{
            storeData(village,city,state,pincode)
        }


    }

    private fun storeData(
        village: String,
        city: String,
        state: String,
        pincode: String
    ) {
       val map = hashMapOf<String,Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pincode"] = pincode

        Firebase.firestore.collection("users").
        document(preference.getString("number","")!!)
            .update(map)
            .addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCoast",totalCost)
                val intent = Intent(this, CheckOutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users").
        document(preference.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNubmber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.userPincode.setText(it.getString("pincode"))
            }
            .addOnFailureListener {

            }
    }
}