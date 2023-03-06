package com.app.printers.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.printers.view.main.PrintersListFragment
import com.app.printers.view.main.TonersListFragment

class PagerMainAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            1 -> PrintersListFragment()
            else -> TonersListFragment()
        }
    }
}