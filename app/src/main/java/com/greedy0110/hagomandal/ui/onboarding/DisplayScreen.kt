package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun DisplayScreen(resId: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = resId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red),
        )
    }
}

@Preview(showBackground = true, widthDp = 150, heightDp = 150)
@Composable
fun PreviewDisplayScreen() {
    HagoMandalTheme {
        DisplayScreen(resId = R.drawable.ic_launcher_foreground)
    }
}
