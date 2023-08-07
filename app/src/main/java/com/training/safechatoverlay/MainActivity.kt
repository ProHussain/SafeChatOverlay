package com.training.safechatoverlay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_OVERLAY_PERMISSION = 101
    private val REQUEST_ACCESSIBILITY = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var openAppAppear = findViewById<TextView>(R.id.tv_open_app_appear)
        val openAssessibility = findViewById<TextView>(R.id.tv_open_accessibility)

        openAppAppear.setOnClickListener {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:${this.packageName}")
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
        }

        openAssessibility.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivityForResult(intent, REQUEST_ACCESSIBILITY)
        }
    }
}
