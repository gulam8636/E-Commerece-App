package com.ali.mkart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ali.mkart.R
import com.ali.mkart.adapter.CategoryAdapter
import com.ali.mkart.adapter.ProductAdapter
import com.ali.mkart.databinding.FragmentHomeBinding
import com.ali.mkart.model.AddProductModel
import com.ali.mkart.model.CategoryModel
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if(preference.getBoolean("isCart",false)){
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
        getCategory()
        getProducts()
        getSliderImage()
        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("Slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImg)
            }
    }

    private fun getProducts() {
        var list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("Product")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data =doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.recyclerProduct.adapter = ProductAdapter(requireContext(),list )
            }
    }

    private fun getCategory() {
        var list=ArrayList<CategoryModel>()
        Firebase.firestore.collection("Category")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data =doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(),list)
            }
    }


}