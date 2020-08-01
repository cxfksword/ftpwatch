package com.trilib.ftpwatch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.heytap.wearable.support.widget.HeyBackTitleBar
import com.heytap.wearable.support.widget.HeyNumberPicker
import com.heytap.wearable.support.widget.HeyTextView
import com.heytap.wearable.support.widget.HeyToast
import com.trilib.ftpwatch.fragment.SettingFragment


class OptionActivity : BaseActivity(), HeyNumberPicker.OnValueChangeListener {
    private var selectedValue = ""
    private var optionType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_option)

        val settings = this.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE);

        val titleBar = this.findViewById<HeyBackTitleBar>(R.id.option_titlebar)
        titleBar.setBackListener(null, this)

        val optionPicker = this.findViewById<HeyNumberPicker>(R.id.option_picker)
        optionPicker.setOnValueChangedListener(this)

        val unitText = this.findViewById<HeyTextView>(R.id.unit)
        optionType = intent.getIntExtra(Constants.IntentDataKey.OPTION_TYPE, 0);
        when(optionType) {
            Constants.RequestCode.CHARSET -> {
                val currentCharset = settings.getString(Constants.PreferenceConsts.CHARSET_TYPE, Constants.PreferenceConsts.CHARSET_TYPE_DEFAULT)
                var currentIndex = Constants.Charset.STRING_ARRAY.indexOf(currentCharset)
                if (currentIndex < 0) {
                    currentIndex =  0
                }
                optionPicker.setDisplayedValuesAndPickedIndex(Constants.Charset.STRING_ARRAY, currentIndex, true)
                unitText.text = "";
            }
        }

    }

    fun clickOk(v: View) {
        val settings = this.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE);
        val editor = settings.edit()
        when(optionType) {
            Constants.RequestCode.CHARSET -> {
                editor.putString(Constants.PreferenceConsts.CHARSET_TYPE, selectedValue)
                editor.commit()
            }
        }

        // 返回上一Activity
        val intent = Intent(this, SettingFragment::class.java)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onValueChange(p0: HeyNumberPicker?, p1: Int, p2: Int) {
        when(optionType) {
            Constants.RequestCode.CHARSET -> selectedValue = Constants.Charset.STRING_ARRAY[p2]
        }
    }
}