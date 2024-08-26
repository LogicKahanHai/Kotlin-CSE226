package com.example.recyclerviewexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var arrayList: ArrayList<LinearPojo>
    private lateinit var favList: ArrayList<LinearPojo>
    private lateinit var favRecycler: RecyclerView
    private lateinit var recyclerView: RecyclerView
    private var adapterRecycler: AdapterRecycler? = null
    private var favAdapterRecycler: AdapterRecycler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearListData()
        initRecyclerView()
    }
    private fun linearListData() {
        favList = ArrayList()
        arrayList = ArrayList()
        arrayList.add(LinearPojo("Android 11", "11"))
        arrayList.add(LinearPojo("Android 10", "10.0"))
        arrayList.add(LinearPojo("Pie", "9.0"))
        arrayList.add(LinearPojo("Oreo", "8.0"))
        arrayList.add(LinearPojo("Nougat", "7.0"))
        arrayList.add(LinearPojo("Marshmallow", "6.0"))
        arrayList.add(LinearPojo("Lollipop", "5.0"))
        arrayList.add(LinearPojo("KitKat", "4.4"))
        arrayList.add(LinearPojo("Jelly Bean", "4.1"))
        arrayList.add(LinearPojo("Ice Cream Sandwich", "4.0"))
        arrayList.add(LinearPojo("Honeycomb", "3.0"))
        arrayList.add(LinearPojo("Gingerbread", "2.3"))
        arrayList.add(LinearPojo("Froyo", "2.2"))
        arrayList.add(LinearPojo("Eclair", "2.0"))
        arrayList.add(LinearPojo("Donut", "1.6"))
    }

    private fun sortListByVersionCode(array: ArrayList<LinearPojo> ): ArrayList<LinearPojo> {
        array.sortBy { it.versionCode.toFloat() }
        return array
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        favRecycler = findViewById(R.id.favouriteRecyclerView)
        recyclerView.setHasFixedSize(true)
        favRecycler.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        favRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapterRecycler = AdapterRecycler(this, sortListByVersionCode(arrayList))
        favAdapterRecycler = AdapterRecycler(this, sortListByVersionCode(favList))
        recyclerView.adapter = adapterRecycler
        favRecycler.adapter = favAdapterRecycler

        /**
         * This is the code for the swipe 'LEFT' to delete the item from the Main List
         * */
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  val deletedCourse: LinearPojo =
                      arrayList[viewHolder.adapterPosition]

                    val position = viewHolder.adapterPosition

                    arrayList.removeAt(viewHolder.adapterPosition)

                    arrayList = sortListByVersionCode(arrayList)


                    adapterRecycler!!.notifyItemRemoved(viewHolder.adapterPosition)


                    Snackbar.make(recyclerView, "Deleted " + deletedCourse.versionName, Snackbar.LENGTH_LONG)
                        .setAction(
                            "Undo"
                        ) {
                            arrayList.add(position, deletedCourse)
                            arrayList = sortListByVersionCode(arrayList)
                            adapterRecycler!!.notifyItemInserted(position)
                        }.show()
                }

        }).attachToRecyclerView(recyclerView)

        //TODO: add the ItemTouchHelper to the recyclerview. for adding favourites and then also to the new list to remove favourites.
        /**
         * This is the code for the swipe 'RIGHT' to add the item to the Favourite List
         * */
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val favCourse: LinearPojo =
                        arrayList[viewHolder.adapterPosition]

                    val position = viewHolder.adapterPosition

                    arrayList.removeAt(position)
                    adapterRecycler!!.notifyItemRemoved(position)
                    arrayList.add(position, favCourse)
                    adapterRecycler!!.notifyItemInserted(position)

                    for (i in favList.indices) {
                        if (favList[i].versionName == favCourse.versionName) {
                            Snackbar.make(recyclerView, "Already in Favourite: " + favCourse.versionName, Snackbar.LENGTH_LONG).show()
                            return
                        }
                    }

                    favList.add(favCourse)
                    favList.sortByDescending { it.versionCode.toFloat() }
//                    favList = sortListByVersionCode(favList)
                    favAdapterRecycler!!.notifyItemInserted(favList.size - 1)

                    Snackbar.make(recyclerView, "Added to Favourite " + favCourse.versionName, Snackbar.LENGTH_LONG)
                        .setAction(
                            "Undo"
                        ) {
                            favList.removeAt(favList.size - 1)
                            sortListByVersionCode(favList)
                            favAdapterRecycler!!.notifyItemRemoved(favList.size - 1)
                        }.show()
                }
            }
        ).attachToRecyclerView(recyclerView)



        /**
         * This is the code for the swipe 'LEFT' to delete the item from the Favourite List
         * */
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val deletedCourse: LinearPojo =
                        favList[viewHolder.adapterPosition]

                    val position = viewHolder.adapterPosition

                    favList.removeAt(viewHolder.adapterPosition)

                    favAdapterRecycler!!.notifyItemRemoved(viewHolder.adapterPosition)

                    Snackbar.make(recyclerView, "Favourite Deleted " + deletedCourse.versionName, Snackbar.LENGTH_LONG)
                        .setAction(
                            "Undo"
                        ) {
                            favList.add(position, deletedCourse)
                            favAdapterRecycler!!.notifyItemInserted(position)
                        }.show()
                }
            }
        ).attachToRecyclerView(favRecycler)


    }
}