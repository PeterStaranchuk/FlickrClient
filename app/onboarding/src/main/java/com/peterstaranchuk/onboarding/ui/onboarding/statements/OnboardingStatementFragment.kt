package com.peterstaranchuk.onboarding.ui.onboarding.statements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class OnboardingStatementFragment : Fragment() {

    private val title by lazy { arguments?.getString(TITLE) ?: throw IllegalStateException("title should be passed in arguments") }
    private val description by lazy { arguments?.getString(DESCRIPTION) ?: throw IllegalStateException("description should be passed in arguments") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StatementItem(title = title, description = description)
            }
        }
    }

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"

        fun getInstance(title: String, description: String): Fragment {
            val fragment = OnboardingStatementFragment()
            fragment.arguments = Bundle().apply {
                putString(TITLE, title)
                putString(DESCRIPTION, description)
            }
            return fragment
        }
    }
}