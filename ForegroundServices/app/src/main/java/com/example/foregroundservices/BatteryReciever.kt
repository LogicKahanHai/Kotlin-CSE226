package com.example.foregroundservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.TextView
import android.widget.Toast

class BatteryReciever constructor(var tv: TextView, val context: Context) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val perc = intent.getIntExtra("level", 0)
        if (perc != 0) {
            tv.text = "$perc"
        }
        val batteryStatus: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0)
        val isCharging: Boolean = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING || batteryStatus == BatteryManager.BATTERY_STATUS_FULL

        if(isCharging) {
            Toast.makeText(context, "Charger Connected", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(context, "Charger Disconnected", Toast.LENGTH_LONG).show()
        }

    }

}