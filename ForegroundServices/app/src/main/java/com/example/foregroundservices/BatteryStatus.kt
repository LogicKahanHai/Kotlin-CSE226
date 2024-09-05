package com.example.foregroundservices

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class BatteryStatus : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var br: BatteryReciever

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_status)
        tv = findViewById(R.id.tv)
        br = BatteryReciever(tv, this)
        registerReceiver(br, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
}