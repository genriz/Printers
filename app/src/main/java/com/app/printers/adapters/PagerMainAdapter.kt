package com.app.printers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.printers.ui.main.PrintersListFragment
import com.app.printers.ui.main.TonersListFragment

class PagerMainAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> PrintersListFragment()
            else -> TonersListFragment()
        }
    }
}