package com.peterstaranchuk.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SplashScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    @Preview(showSystemUi = true)
    @Composable
    fun SplashScreen() {
        Column {
            Spacer(
                modifier = Modifier
                    .height(120.dp)
            )

            Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(ContextCompat.getColor(requireContext(), R.color.flickr_blue)))
                )

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(ContextCompat.getColor(requireContext(), R.color.flickr_purple)))
                )
            }
        }
    }

    @Preview
    @Composable
    fun ScreenPreview() {
        SplashScreen()
    }
}