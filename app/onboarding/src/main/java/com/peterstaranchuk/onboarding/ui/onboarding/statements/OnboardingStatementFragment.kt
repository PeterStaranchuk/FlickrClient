package com.peterstaranchuk.onboarding.ui.onboarding.statements

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import android.util.DisplayMetrics

class OnboardingStatementFragment : Fragment() {

    private val title by lazy { arguments?.getString(TITLE) ?: throw IllegalStateException("title should be passed in arguments") }
    private val description by lazy { arguments?.getString(DESCRIPTION) ?: throw IllegalStateException("description should be passed in arguments") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            val dpInPx = context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
            val widthInPixels = Resources.getSystem().displayMetrics.widthPixels.toFloat()
            val padding = widthInPixels / dpInPx * 0.2f

            setContent {
                Column(horizontalAlignment = CenterHorizontally) {
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
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(start = padding.dp, end = padding.dp)
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