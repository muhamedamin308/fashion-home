package com.example.fashionhome.helper

import android.content.Context
import android.widget.Toast
import com.example.fashionhome.model.Item

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ManagementCart(val context: Context) {
    private val tinyDb = TinyDB(context)

    fun insertItem(item: Item) {
        var listItem = getListCart()
        val existAlready = listItem.any { it.title == item.title }
        val index = listItem.indexOfFirst { it.title == item.title }

        if (existAlready)
            listItem[index].numberInCart = item.numberInCart
        else
            listItem.add(item)
        tinyDb.putListObject("CartList", listItem)
        Toast.makeText(context, "Added to your cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart() =
        tinyDb.getListObject("CartList") ?: arrayListOf()

    fun minusItem(position: Int, listener: ChangeNumberItemsListener) {
        val listItem = getListCart() // reload fresh copy
        if (listItem[position].numberInCart == 1)
            listItem.removeAt(position)
        else
            listItem[position].numberInCart--
        tinyDb.putListObject("CartList", listItem)
        listener.onChange()
    }

    fun plusItem(position: Int, listener: ChangeNumberItemsListener) {
        val listItem = getListCart() // reload fresh copy
        listItem[position].numberInCart++
        tinyDb.putListObject("CartList", listItem)
        listener.onChange()
    }


    fun getTotalFee(): Double {
        val listItem = getListCart()
        var fee = 0.0
        for (item in listItem)
            fee = fee + item.price * item.numberInCart
        return fee
    }
}