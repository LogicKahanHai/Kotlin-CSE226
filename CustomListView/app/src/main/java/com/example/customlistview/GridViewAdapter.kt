package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService

class GridViewAdapter(
    private val courseList: List<GridViewModel>,
    private val context: Context
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater ?= null;
    private lateinit var courseTextView: TextView
    private lateinit var courseImageView: ImageView


    override fun getCount(): Int {
        return courseList.size
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
            convertView = layoutInflater!!.inflate(R.layout.grid_layout_card_view, null)
        }

        courseImageView = convertView!!.findViewById(R.id.IVCourse)
        courseTextView = convertView.findViewById(R.id.TVCourse)

        courseImageView.setImageResource(courseList[position].courseImg)
        courseTextView.text = courseList[position].courseName;

        return convertView

    }

}