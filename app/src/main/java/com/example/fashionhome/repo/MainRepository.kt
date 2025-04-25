package com.example.fashionhome.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fashionhome.model.Category
import com.example.fashionhome.model.Item
import com.example.fashionhome.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanners(): LiveData<MutableList<SliderModel>> {
        val liveData = MutableLiveData<MutableList<SliderModel>>()
        val ref = firebaseDatabase.getReference("Banner")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val banners = mutableListOf<SliderModel>()
                for (child in snapshot.children) {
                    val banner = child.getValue(SliderModel::class.java)
                    banner?.let { banners.add(it) }
                }
                liveData.value = banners
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return liveData
    }

    fun loadCategories(): LiveData<MutableList<Category>> {
        val liveData = MutableLiveData<MutableList<Category>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = mutableListOf<Category>()
                for (child in snapshot.children) {
                    val category = child.getValue(Category::class.java)
                    category?.let { categories.add(it) }
                }
                liveData.value = categories
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return liveData
    }

    fun loadPopularProducts(): LiveData<MutableList<Item>> {
        val liveData = MutableLiveData<MutableList<Item>>()
        val ref = firebaseDatabase.getReference("Items")
        val query = ref.orderByChild("showRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Item>()
                for (child in snapshot.children) {
                    val product = child.getValue(Item::class.java)
                    product?.let { products.add(it) }
                }
                liveData.value = products
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return liveData
    }

    fun loadFilteredProducts(id: String): LiveData<MutableList<Item>> {
        val liveData = MutableLiveData<MutableList<Item>>()
        val ref = firebaseDatabase.getReference("Items")
        val query = ref.orderByChild("categoryId").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Item>()
                for (child in snapshot.children) {
                    val product = child.getValue(Item::class.java)
                    product?.let { products.add(it) }
                }
                liveData.value = products
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return liveData
    }
}