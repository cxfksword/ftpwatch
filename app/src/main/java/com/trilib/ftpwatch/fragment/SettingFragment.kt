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
import com.heytap.wearable.support.widget.HeySingleItemWithSwitch
import com.heytap.wearable.support.widget.HeyToast
import com.trilib.ftpwatch.*
import com.trilib.ftpwatch.services.FtpService
import com.trilib.ftpwatch.utils.CommonUtils
import com.trilib.ftpwatch.utils.SettingManager

class SettingFragment : Fragment(), View.OnClickListener {

    private var settingPort : HeyMultipleDefaultItem? = null
    private var settingChartset : HeyMultipleDefaultItem? = null
    private var settingAbout : HeyMultipleDefaultItem? = null
    private var setttingHiddenFiles : HeySingleItemWithSwitch? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_setting, container, false)
        settingPort = v.findViewById<HeyMultipleDefaultItem>(R.id.setting_port)
        settingChartset = v.findViewById<HeyMultipleDefaultItem>(R.id.settting_charset)
        settingAbout = v.findViewById<HeyMultipleDefaultItem>(R.id.about)
        setttingHiddenFiles = v.findViewById<HeySingleItemWithSwitch>(R.id.setting_hidden_files)

        val settingMgr = SettingManager.build(context!!);
        settingPort!!.summaryTextView.text = settingMgr.getInt(Constants.PreferenceConsts.PORT_NUMBER, Constants.PreferenceConsts.PORT_NUMBER_DEFAULT).toString()
        settingChartset!!.summaryTextView.text = settingMgr.getString(Constants.PreferenceConsts.CHARSET_TYPE, Constants.PreferenceConsts.CHARSET_TYPE_DEFAULT)
        settingAbout!!.summaryTextView.text = "v" + CommonUtils.getAppVersionName(context!!)
        setttingHiddenFiles!!.heySwitch.isChecked = settingMgr.getBoolean(Constants.PreferenceConsts.SHOW_HIDDEN_FILES, Constants.PreferenceConsts.SHOW_HIDDEN_FILES_DEFAULT)

        settingPort!!.setOnClickListener(this)
        settingChartset!!.setOnClickListener(this)
        settingAbout!!.setOnClickListener(this)
        setttingHiddenFiles!!.heySwitch.setOnClickListener(this)
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
            setttingHiddenFiles!!.heySwitch.id -> {
                SettingManager.build(context).edit().putBoolean(Constants.PreferenceConsts.SHOW_HIDDEN_FILES, setttingHiddenFiles!!.heySwitch.isChecked).commit()
                FtpService.updateShowHiddenFiles(setttingHiddenFiles!!.heySwitch.isChecked)
            }
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