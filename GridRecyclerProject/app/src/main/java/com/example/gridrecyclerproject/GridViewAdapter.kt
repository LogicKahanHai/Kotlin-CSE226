package com.example.gridrecyclerproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.contract

class GridViewAdapter(context: Context, arrayList: ArrayList<GridPojo>) : RecyclerView.Adapter<GridViewAdapter.MyHolder>() {
    private val context: Context
    private val arrayList: ArrayList<GridPojo>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewAdapter.MyHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewAdapter.MyHolder, position: Int) {
        holder.name.text = arrayList[position].title
        holder.image.setImageResource(arrayList[position].image)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "" + arrayList[position].title, Toast.LENGTH_SHORT).show()
        }
        holder.btn.setOnClickListener {
            arrayList.removeAt(position)
            notifyItemRemoved(position);
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var image: ImageView
        var btn: Button
        init {
            name = itemView.findViewById(R.id.tvTitle)
            image = itemView.findViewById(R.id.ivImage)
            btn = itemView.findViewById(R.id.deleteButton)
        }
    }

    init {
        this.context = context
        this.arrayList = arrayList
    }

}