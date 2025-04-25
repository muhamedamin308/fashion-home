package com.example.fashionhome.model

import java.io.Serializable

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
data class Item(
    val categoryId: String = "",
    val description: String = "",
    val model: ArrayList<String> = ArrayList(),
    val picUrl: ArrayList<String> = ArrayList(),
    val price: Double = 0.0,
    val rating: Float = 0.0f,
    val showRecommended: Boolean = false,
    val title: String = "",
    var numberInCart: Int = 0
) : Serializable
