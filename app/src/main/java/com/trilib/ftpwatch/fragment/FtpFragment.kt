package com.trilib.ftpwatch.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.heytap.wearable.support.widget.HeyDialog
import com.heytap.wearable.support.widget.HeyDialog.HeyBuilder
import com.heytap.wearable.support.widget.HeyGradientButton
import com.heytap.wearable.support.widget.HeyToast
import com.trilib.ftpwatch.R
import com.trilib.ftpwatch.services.FtpService
import com.trilib.ftpwatch.utils.CommonUtils
import com.trilib.ftpwatch.utils.MediaScanner
import java.io.File


class FtpFragment : Fragment(), FtpService.OnFTPServiceStatusChangedListener, View.OnClickListener{

    private var ftpStatus : TextView? = null
    private var ftpBtn : HeyGradientButton? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FtpService.addOnFtpServiceStatusChangedListener(this);

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_ftp, container, false)
        ftpStatus = v.findViewById<TextView>(R.id.ftp_status)
        ftpStatus!!.text = resources.getString(R.string.ftp_status_not_running)

        ftpBtn =  v.findViewById<HeyGradientButton>(R.id.ftp_btn)
        ftpBtn!!.setOnClickListener(this)
        if (FtpService.isFTPServiceRunning()) {
            // 假如wifi已被系统关闭，需要停止服务
            if (!CommonUtils.isWifiConnected(context)) {
                FtpService.stopService()
            }
            refreshFtpStatus(true)
        }
        return v
    }

    override fun onResume() {
        super.onResume()

        if (FtpService.isFTPServiceRunning()) {
            // 假如wifi已被系统关闭，需要停止服务
            if (!CommonUtils.isWifiConnected(context)) {
                FtpService.stopService()
            }
            refreshFtpStatus(true)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.ftp_btn -> toggleFtp(v)
        }
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
        refreshFtpStatus(false)
    }

    override fun onFTPServiceStarted() {
        refreshFtpStatus(true)
    }

    override fun onFTPServiceUploadEnd(filepath: String?) {
        if (!filepath.isNullOrEmpty()) {
//            mediaScanner.scan(filepath);
//            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//            intent.data = Uri.fromFile(File(filepath))
//            context!!.sendBroadcast(intent)

            val rootPath = Environment.getExternalStorageDirectory().absolutePath
            MediaScanner(context!!).scanFilesSimply(File(rootPath + filepath))
        }
    }


    private fun getExtensionFromUrl(url: String): String? {
        var ext = ""
        val i = url.lastIndexOf('.')
        if (i > 0) {
            ext = url.substring(i + 1)
        }
        return ext.toLowerCase()
    }

    override fun onFTPServiceStartError(e: Exception?) {
        HeyToast.showToast(context, context?.getString(R.string.notification_ftp_start_error_title) + e?.message , Toast.LENGTH_SHORT);
    }

    private fun startFtp() {
        if (FtpService.isFTPServiceRunning()) {
            return
        }

        if (!CommonUtils.isWifiConnected(context)) {
            val builder: HeyBuilder = HeyBuilder(context)
            builder.setContentViewStyle(HeyDialog.STYLE_CONTENT) //设置对话框样式，不含标题
                    .setMessage(context!!.getString(R.string.wifi_permission_content))
                    .setButtonOrientation(LinearLayout.HORIZONTAL) //设置按钮方向
                    .setNegativeButton(context!!.getString(R.string.action_cancel), null)
                    .setPositiveButton(context!!.getString(R.string.dialog_action_goto),
                        View.OnClickListener {
                            var intent =  Intent()
                            intent.action = Settings.ACTION_WIFI_SETTINGS
                            startActivity(intent)
                        })
            val dialog = builder.create()
            dialog.show()
            return
        }
        showProgress()

        // 传fragment是为了响应权限操作事件onRequestPermissionsResult
        if (FtpService.startService(context!!, this)) {
            ftpStatus!!.text = resources.getString(R.string.attention_opening_ftp)
        }

    }

    private fun closeFtp() {
        if (FtpService.isFTPServiceRunning()) {
            hideProgress()

            ftpStatus!!.text = resources.getString(R.string.attention_closing_ftp)
            FtpService.stopService();
        }
    }

    private fun refreshFtpStatus(isRunning: Boolean) {
        hideProgress()
        if (isRunning) {
            ftpBtn!!.setImageResource(R.drawable.ic_stop)
            ftpBtn!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.theme8))
        } else {
            ftpBtn!!.setImageResource(R.drawable.ic_start)
            ftpBtn!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.theme1))
        }
        ftpStatus!!.text = FtpService.getFTPStatusDescription(context)
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

    override fun onDestroy() {
        super.onDestroy()

        FtpService.removeOnFtpServiceStatusChangedListener(this);
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 授权后，自动打开 ftp服务
            startFtp()
        }
    }

}