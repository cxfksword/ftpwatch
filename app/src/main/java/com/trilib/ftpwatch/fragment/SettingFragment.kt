package com.trilib.ftpwatch.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.heytap.wearable.support.widget.HeyMultipleDefaultItem
import com.heytap.wearable.support.widget.HeySingleDefaultItem
import com.heytap.wearable.support.widget.HeyToast
import com.trilib.ftpwatch.*

class SettingFragment : Fragment(), View.OnClickListener {

    private var settingPort : HeyMultipleDefaultItem? = null
    private var settingChartset : HeyMultipleDefaultItem? = null
    private var settingAbout : HeySingleDefaultItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_setting, container, false)
        settingPort = v.findViewById<HeyMultipleDefaultItem>(R.id.setting_port)
        settingChartset = v.findViewById<HeyMultipleDefaultItem>(R.id.settting_charset)
        settingAbout = v.findViewById<HeySingleDefaultItem>(R.id.about)

        val settings = context!!.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE);
        settingPort!!.summaryTextView.text = settings.getInt(Constants.PreferenceConsts.PORT_NUMBER, Constants.PreferenceConsts.PORT_NUMBER_DEFAULT).toString()
        settingChartset!!.summaryTextView.text = settings.getString(Constants.PreferenceConsts.CHARSET_TYPE, Constants.PreferenceConsts.CHARSET_TYPE_DEFAULT)

        settingPort!!.setOnClickListener(this)
        settingChartset!!.setOnClickListener(this)
        settingAbout!!.setOnClickListener(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.setting_port -> startActivityForResult(Intent(context, PortEditActivity::class.java), Constants.RequestCode.PORT)
            R.id.settting_charset -> {
                val intent = Intent(context, OptionActivity::class.java)
                intent.putExtra(Constants.IntentDataKey.OPTION_TYPE, Constants.RequestCode.CHARSET)
                startActivityForResult(intent, Constants.RequestCode.CHARSET)
            }
            R.id.about -> startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK ) {
            val settings = context!!.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE);
            when(requestCode) {
                Constants.RequestCode.CHARSET -> {
                    settingChartset!!.summaryTextView.text = settings.getString(Constants.PreferenceConsts.CHARSET_TYPE, Constants.PreferenceConsts.CHARSET_TYPE_DEFAULT)
                }
                Constants.RequestCode.PORT -> {
                    settingPort!!.summaryTextView.text = settings.getInt(Constants.PreferenceConsts.PORT_NUMBER, Constants.PreferenceConsts.PORT_NUMBER_DEFAULT).toString()
                }
            }

        }
    }

}