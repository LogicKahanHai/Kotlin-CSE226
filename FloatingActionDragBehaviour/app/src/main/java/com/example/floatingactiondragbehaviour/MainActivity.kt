package com.example.floatingactiondragbehaviour

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var xDelta = 0f
    private var yDelta = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       val fab = findViewById<FloatingActionButton>(R.id.fab)
//        Set a touch listener for the FAB to allow dragging
        fab.setOnTouchListener { view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xDelta = view.x - event.rawX
                    yDelta = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(event.rawX + xDelta)
                        .y(event.rawY + yDelta)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {
//                    Optional: handle any action after the movement is done
                }
            }
            true
        }

    }
}