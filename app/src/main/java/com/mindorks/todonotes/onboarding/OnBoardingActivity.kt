package com.mindorks.todonotes.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.mindorks.todonotes.R
import com.mindorks.todonotes.utils.AppConsant.ON_BOARDED_SUCCESSFULLY
import com.mindorks.todonotes.utils.AppConsant.SHARED_PREFERENCES_NAME
import com.mindorks.todonotes.view.LoginActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        setupSharedPreferences()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        // 2nd fragment

        editor = sharedPreferences.edit()
        editor.putBoolean(ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()

        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}
