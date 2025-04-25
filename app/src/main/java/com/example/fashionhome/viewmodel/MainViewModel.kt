package com.example.fashionhome.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fashionhome.repo.MainRepository

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadBanner() = repository.loadBanners()
    fun loadCategories() = repository.loadCategories()
    fun loadPopularProducts() = repository.loadPopularProducts()
    fun filterByCategory(category: String) = repository.loadFilteredProducts(category)
}