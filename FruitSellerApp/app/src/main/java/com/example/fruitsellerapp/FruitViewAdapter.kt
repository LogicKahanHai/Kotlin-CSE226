package com.example.fruitsellerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class FruitViewAdapter(
    private val fruitList: List<FruitViewModel>,
    private val context: Context,
): BaseAdapter() {

    private var layoutInflater: LayoutInflater ?= null;
    private lateinit var fruitTextView1: TextView
    private lateinit var fruitTextView2: TextView
    private lateinit var fruitImageView: ImageView


    override fun getCount(): Int {
        return fruitList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.fruit_card_view, null)
        }

        fruitTextView1 = convertView!!.findViewById(R.id.TV1FC)
        fruitTextView2 = convertView.findViewById(R.id.TV2FC)
        fruitImageView = convertView.findViewById(R.id.IVFC)

        fruitImageView.setImageResource(fruitList[position].fruitImg)
        fruitTextView1.text = fruitList[position].fruitName
        fruitTextView2.text = fruitList[position].fruitPrice

        return convertView
    }

}