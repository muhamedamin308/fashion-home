package com.example.fashionhome.ui.screens.list_item_screen

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.fashionhome.ui.screens.BaseActivity
import com.example.fashionhome.viewmodel.MainViewModel

class ListItemActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private var id: String = ""
    private var title: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id") ?: ""
        title = intent.getStringExtra("title") ?: ""

        setContent {
            ListItemScreen(
                title = title,
                onBackClicked = { finish() },
                viewModel = viewModel,
                id = id
            )
        }
    }
}
