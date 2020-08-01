package com.trilib.ftpwatch

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_SWIPE_TO_DISMISS);
    }
}