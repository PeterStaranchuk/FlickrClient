package com.peterstaranchuk.onboarding.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.peterstaranchuk.onboarding.R

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
                    .background(Color(ContextCompat.getColor(LocalContext.current, R.color.flickr_blue)))
            )

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(ContextCompat.getColor(LocalContext.current, R.color.flickr_purple)))
            )
        }
    }
}

@Preview
@Composable
fun ScreenPreview() {
    SplashScreen()
}