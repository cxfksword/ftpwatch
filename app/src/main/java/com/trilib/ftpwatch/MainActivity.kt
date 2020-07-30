package com.trilib.ftpwatch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.heytap.wearable.support.widget.HeyGradientButton
import com.trilib.ftpwatch.services.FtpService
import com.trilib.ftpwatch.utils.CommonUtils
import java.lang.Exception


class MainActivity : Activity(), FtpService.OnFTPServiceStatusChangedListener {

    private var ftpStatus : TextView? = null
    private var ftpBtn : HeyGradientButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        setContentView(R.layout.activity_main)

        FtpService.addOnFtpServiceStatusChangedListener(this);
        initUI()
    }

    private fun initUI() {
        ftpStatus = this.findViewById<TextView>(R.id.ftp_status)
        ftpStatus!!.text = resources.getString(R.string.ftp_status_not_running)

        ftpBtn =  this.findViewById<HeyGradientButton>(R.id.ftp_btn)
    }


    @Override
    fun toggleFtp(v: View)
    {
        if (!FtpService.isFTPServiceRunning()) {
            startFtp()
        } else {
            closeFtp()
        }
    }



    override fun onRemainingSeconds(seconds: Int) {
        TODO("Not yet implemented")
    }

    override fun onFTPServiceDestroyed() {
        hideProgress()

        ftpBtn!!.setImageResource(R.drawable.ic_start)
        ftpBtn!!.setBackgroundColor(ContextCompat.getColor(this, R.color.theme1))

        ftpStatus!!.text = FtpService.getFTPStatusDescription(this)
    }

    override fun onFTPServiceStarted() {
       hideProgress()

        ftpBtn!!.setImageResource(R.drawable.ic_stop)
        ftpBtn!!.setBackgroundColor(ContextCompat.getColor(this, R.color.theme8))

        ftpStatus!!.text = FtpService.getFTPStatusDescription(this)
    }

    override fun onFTPServiceStartError(e: Exception?) {
        TODO("Not yet implemented")
    }

    private fun startFtp() {
        if (!FtpService.isFTPServiceRunning()) {
            if (!CommonUtils.isWifiConnected(this)) {
                var intent =  Intent()
                intent.action = Settings.ACTION_WIFI_SETTINGS
                startActivity(intent)
                return
            }
            showProgress()


            if (FtpService.startService(this)) {
                ftpStatus!!.text = resources.getString(R.string.attention_opening_ftp)
            }
        }
    }

    private fun closeFtp() {
        if (FtpService.isFTPServiceRunning()) {
            hideProgress()

            ftpStatus!!.text = resources.getString(R.string.attention_closing_ftp)
            FtpService.stopService();
        }
    }

    private fun showProgress() {
        try {
//            var fabProgressCircle = this.findViewById<FABProgressCircle>(R.id.fabProgressCircle)
//            fabProgressCircle?.show()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private fun hideProgress() {
        try {
//            var fabProgressCircle = this.findViewById<FABProgressCircle>(R.id.fabProgressCircle)
//            fabProgressCircle?.hide()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

}
