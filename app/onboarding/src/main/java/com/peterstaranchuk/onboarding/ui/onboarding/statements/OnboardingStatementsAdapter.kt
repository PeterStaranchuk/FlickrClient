package com.peterstaranchuk.onboarding.ui.onboarding.statements

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingStatementsAdapter(fragment: Fragment, private val titles: Array<String>, private val descriptions: Array<String>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        if (titles.size != descriptions.size) {
            throw IllegalStateException("amount of titles and descriptions should be the same")
        }
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnboardingStatementFragment.getInstance(title = titles[position], description = descriptions[position])
    }

}