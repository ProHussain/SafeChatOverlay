package com.training.safechatoverlay

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AppForegroundDetectorService : AccessibilityService() {

    private val WHATSAPP_PACKAGE = "com.whatsapp.w4b"
    companion object {var isPasswordCorrect = false}


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            Log.e("AppForegroundDetector", "Package name: $packageName")
            if (packageName == WHATSAPP_PACKAGE) {
                if (!isPasswordCorrect) {
                    startService(Intent(this, OverlayService::class.java))
                    // We also have an option to start the activity here instead of the service to make it more visible and handle back button
                } else {
                    isPasswordCorrect = false
                }

            }
        }
    }

    override fun onInterrupt() {
        // Not used
    }
}