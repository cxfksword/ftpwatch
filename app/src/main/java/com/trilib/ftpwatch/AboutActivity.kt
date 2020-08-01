package com.trilib.ftpwatch

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.heytap.wearable.support.widget.HeyBackTitleBar

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)

        val about = this.findViewById<TextView>(R.id.about_content)
        about.movementMethod = LinkMovementMethod.getInstance()

        val aboutTitleBar = this.findViewById<HeyBackTitleBar>(R.id.about_titlebar)
        aboutTitleBar.setBackListener(null, this)
    }
}