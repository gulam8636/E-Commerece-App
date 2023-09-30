package com.ali.mkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat.getCategory
import androidx.recyclerview.widget.RecyclerView
import com.ali.mkart.R
import com.ali.mkart.adapter.CategoryProductAdapter
import com.ali.mkart.adapter.ProductAdapter
import com.ali.mkart.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        getProduct(intent.getStringExtra("cat"))
    }

    private fun getProduct(category: String?) {
        var list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("Product").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data =doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = CategoryProductAdapter(this,list )
            }
    }
}