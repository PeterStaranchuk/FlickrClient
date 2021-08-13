package com.peterstaranchuk.onboarding.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class OnboardingStatementFragment : Fragment() {

    private val title by lazy { arguments?.getString(TITLE) ?: throw IllegalStateException("title should be passed in arguments") }
    private val description by lazy { arguments?.getString(DESCRIPTION) ?: throw IllegalStateException("description should be passed in arguments") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(start = 40.dp, end = 40.dp)) {
                    Text(
                        text = title,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }
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