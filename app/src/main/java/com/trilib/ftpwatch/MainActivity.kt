package com.trilib.ftpwatch

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.heytap.wearable.support.widget.pageindicator.HeyPageIndicator
import com.trilib.ftpwatch.adapter.FragmentViewPagerAdapter


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 是否隐藏android标题栏
//        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {

        val viewPager = this.findViewById<ViewPager>(R.id.viewpager)
        val indicator = this.findViewById<HeyPageIndicator>(R.id.indicator)
        val fragmentViewPagerAdapter = FragmentViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = fragmentViewPagerAdapter
        viewPager.addOnPageChangeListener( object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                indicator.onPageSelected(position);
            }
            override fun onPageScrollStateChanged(state: Int) {
                indicator.onPageScrollStateChanged(state);
            }
        });

        indicator.setDotsCount(fragmentViewPagerAdapter.count, true)
        indicator.setOnDotClickListener { position -> viewPager.currentItem = position };


    }


}
