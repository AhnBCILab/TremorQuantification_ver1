package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ThridTask()
            }
            1 -> FirstSurvey()
            else -> {
                return  SecondSurvey()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "STATE"
            1 -> "MOTOR SCALE"
            else -> {
                return "CRTS"
            }
        }
    }
}