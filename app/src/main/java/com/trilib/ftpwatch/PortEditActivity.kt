package com.trilib.ftpwatch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.heytap.wearable.support.widget.HeyBackTitleBar
import com.heytap.wearable.support.widget.HeyToast
import com.trilib.ftpwatch.fragment.SettingFragment
import com.trilib.ftpwatch.utils.SettingManager
import java.lang.Exception

class PortEditActivity : BaseActivity() {
    private var inputPort : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_port_edit)

        val backTitleBar = this.findViewById<HeyBackTitleBar>(R.id.back_titlebar)
        backTitleBar.setBackListener(null, this)

        val currentPort = SettingManager.build(this).getInt(Constants.PreferenceConsts.PORT_NUMBER, Constants.PreferenceConsts.PORT_NUMBER_DEFAULT)
        inputPort = this.findViewById<EditText>(R.id.input_port)
        inputPort!!.setText(currentPort.toString())
    }

    fun clickOk(v: View) {
        var port = 0;
        try {
            port = inputPort!!.text.toString().toInt()
        } finally {
            if (port < 1024 || port > 65535) {
                HeyToast.showToast(
                    this,
                    this.getString(R.string.toast_invalid_port),
                    Toast.LENGTH_SHORT
                );
                return
            }
        }
        SettingManager.build(this).edit().putInt(Constants.PreferenceConsts.PORT_NUMBER, port).commit()


        // 返回上一Activity
        val intent = Intent(this, SettingFragment::class.java)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}