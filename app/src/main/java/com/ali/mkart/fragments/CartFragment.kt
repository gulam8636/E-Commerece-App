package com.ali.mkart.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ali.mkart.activity.AddressActivity
import com.ali.mkart.adapter.CartAdapter
import com.ali.mkart.databinding.FragmentCartBinding
import com.ali.mkart.roomdb.AppDatabase
import com.ali.mkart.roomdb.ProductModel


class CartFragment : Fragment() {
private lateinit var binding : FragmentCartBinding
private lateinit var list : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()
        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(),it)

            list.clear()
            for(data in it){
                list.add(data.productId)
            }

            totalCost(it)
        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
      var total = 0
        for(item in data!!){
            total +=item.productSp!!.toInt()
        }
        binding.textView10.text = "Total item in this cart is ${data.size}"
        binding.textView11.text = "Total Cost : $total"
        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds",list)
            b.putString("totalCoast",total.toString())
            intent.putExtras(b)
           startActivity(intent)
        }
    }

}