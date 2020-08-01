package com.trilib.ftpwatch.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.trilib.ftpwatch.fragment.FtpFragment
import com.trilib.ftpwatch.fragment.SettingFragment
import com.trilib.ftpwatch.R

class FragmentViewPagerAdapter(ctx: Context, fm: FragmentManager): FragmentPagerAdapter(fm)
{
    private var context: Context? = ctx

    override fun getItem(position: Int): Fragment {
        when(position){
            0 ->return FtpFragment()
            else->return SettingFragment()
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return context!!.getString(R.string.title_ftp)
            else->return context!!.getString(R.string.title_setting)
        }
    }

    override fun getCount(): Int {
        return 2
    }


}