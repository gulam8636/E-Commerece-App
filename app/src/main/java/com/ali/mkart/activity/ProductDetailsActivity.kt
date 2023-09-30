package com.ali.mkart.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ali.mkart.MainActivity
import com.ali.mkart.R
import com.ali.mkart.databinding.ActivityProductDetailsBinding
import com.ali.mkart.roomdb.AppDatabase
import com.ali.mkart.roomdb.ProductDao
import com.ali.mkart.roomdb.ProductModel
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        getProductDetails(intent.getStringExtra("id"))
        setContentView(binding.root)

    }

    private fun getProductDetails(proId: String?) {
Firebase.firestore.collection("Product")
    .document(proId!!).get().addOnSuccessListener {
      val list = it.get("productImages") as ArrayList<String>
        val name= it.getString("productName")
        val productSp = it.getString("productSp")
        val productDescription=it.getString("productDescription")
        binding.textView5.text =name
        binding.textView7.text =productSp
        binding.textView8.text =productDescription
         val slideList = ArrayList<SlideModel>()
        for(data in list){
            slideList.add(SlideModel(data, ScaleTypes.CENTER_INSIDE))

        }
        cartAction(proId,name,productSp,it.getString("productCoverImg"))
        binding.imageSlider.setImageList(slideList)
    }.addOnFailureListener {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun cartAction(proId: String, name: String?, productSp: String?, CoverImg: String?) {
    val productDao = AppDatabase.getInstance(this).productDao()

        if (productDao.isExist(proId)!=null){
            binding.textView3.text ="Go to Cart"
        }
        else{
            binding.textView3.text = "Add to Cart"
        }
        binding.textView3.setOnClickListener {
            if (productDao.isExist(proId)!=null){
               openCart()
            }
            else{
                addToCart(productDao,proId,name,productSp,CoverImg)
            }
        }
    }

    private fun addToCart(productDao: ProductDao, proId: String, name: String?, productSp: String?, coverImg: String?) {
val data = ProductModel(proId,name,coverImg,productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView3.text ="Go to Cart"
        }
    }

    private fun openCart() {
     val preference=this.getSharedPreferences("info", MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}