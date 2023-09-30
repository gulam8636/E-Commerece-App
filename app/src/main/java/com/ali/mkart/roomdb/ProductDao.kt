package com.ali.mkart.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
  suspend  fun insertProduct(product: ProductModel)

    @Delete
  suspend  fun deleteProduct(product: ProductModel)

    @Query("select * from products")
   fun getAllProducts () :LiveData <List<ProductModel>>
   @Query("select * from products where productId = :id")
   fun isExist(id : String) : ProductModel
}