package com.example.fruitsellerapp

import android.os.Bundle
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FruitGridView : AppCompatActivity() {
    lateinit var fruitGV: GridView
    lateinit var fruitList: List<FruitViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fruit_grid_view)

        fruitGV = findViewById(R.id.GRV)
        fruitList = ArrayList<FruitViewModel>()

        fruitList = fruitList + FruitViewModel("Apple", R.drawable.apple, "Rs. 100/kg")
        fruitList = fruitList + FruitViewModel("Orange", R.drawable.orange, "Rs. 80/kg")
        fruitList = fruitList + FruitViewModel("Strawberry", R.drawable.strawberry, "Rs. 100/box")
        fruitList = fruitList + FruitViewModel("Watermelon", R.drawable.watermelon, "Rs. 70/pc")

        val fruitViewAdapter = FruitViewAdapter(fruitList, this)
        fruitGV.adapter = fruitViewAdapter


    }
}