package com.training.safechatoverlay

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private lateinit var codeEditText: EditText

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        showOverlay()
    }

    private fun showOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_layout, null)
        codeEditText = overlayView.findViewById(R.id.editTextCode)
        val submitBtn = overlayView.findViewById<Button>(R.id.btn_submit)
        val closeBtn = overlayView.findViewById<Button>(R.id.btn_exit)

        submitBtn.setOnClickListener {
            if (codeEditText.text.toString() == "1122qwert") {
                AppForegroundDetectorService.isPasswordCorrect = true
                closeOverlay()
            } else {
                codeEditText.error = "Wrong code"
            }
        }

        closeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            closeOverlay()
        }

        windowManager.addView(overlayView, params)
    }

    private fun closeOverlay() {
        if (overlayView.isAttachedToWindow) {
            windowManager.removeView(overlayView)
        }
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeOverlay()
    }

}