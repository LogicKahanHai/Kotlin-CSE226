package com.example.gridrecyclerproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var gridViewList: ArrayList<GridPojo>
    lateinit var recyclerView: RecyclerView
    var adapterRecycler: GridViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseGridViewList()
        initialiseRecyclerView()
    }

    private fun initialiseRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
        adapterRecycler = GridViewAdapter(this, gridViewList)
        recyclerView.adapter = adapterRecycler
    }

    private fun initialiseGridViewList() {
        gridViewList = ArrayList()
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 1"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 2"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 3"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 4"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 5"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 6"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 7"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 8"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 9"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 10"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 11"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 12"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 13"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 14"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 15"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 16"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 17"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 18"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_background, "BG Image 19"))
        gridViewList.add(GridPojo(R.drawable.ic_launcher_foreground, "FG Image 20"))
    }
}