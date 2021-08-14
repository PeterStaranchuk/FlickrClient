package com.peterstaranchuk.onboarding.ui.onboarding.statements

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.peterstaranchuk.onboarding.R

@Composable
fun StatementItem(title : String, description : String) {
    val context = LocalContext.current
    val dpInPx = context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    val widthInPixels = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    val padding = widthInPixels / dpInPx * 0.2f

    Column(horizontalAlignment = CenterHorizontally) {
        Text(
            text = title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontFamily = FontFamily(ResourcesCompat.getFont(context, R.font.nunito_sans_bold)!!),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontFamily = FontFamily(ResourcesCompat.getFont(context, R.font.nunito_sans_regular)!!),
            modifier = Modifier.padding(start = padding.dp, end = padding.dp)
        )
    }
}

@Preview
@Composable
fun Preview() {
    StatementItem("title", "description")
}