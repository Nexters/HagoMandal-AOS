package com.greedy0110.hagomandal.ui.share

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t12
import com.greedy0110.hagomandal.ui.theme.t18
import com.greedy0110.hagomandal.util.coloredShadow

class ShareAction(
    @DrawableRes val iconRes: Int,
    val title: String,
    val action: () -> Unit = {},
)

private val actions = listOf(
    ShareAction(iconRes = R.drawable.ic_user, title = "이름 수정"),
    ShareAction(iconRes = R.drawable.ic_edit_2, title = "목표 수정"),
    ShareAction(iconRes = R.drawable.ic_trash, title = "목표 삭제"),
    ShareAction(iconRes = R.drawable.ic_image, title = "이미지로 저장"),
)

@Composable
fun ShareTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    actions: List<ShareAction> = emptyList(),
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.End
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = t18,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier
                    .clickable { expanded.value = expanded.value.not() },
                painter = painterResource(id = R.drawable.ic_more_vertical),
                contentDescription = null,
                tint = Color.White,
            )
        }
        if (expanded.value) {
            Spacer(modifier = Modifier.height(12.dp))
            ShareTopBarDropDown(actions = actions)
        }
    }
}

@Preview
@Composable
fun PreviewShareTopBar() {
    HagoMandalTheme {
        ShareTopBar(
            title = "D-53",
            actions = actions,
            expanded = remember { mutableStateOf(true) },
        )
    }
}

@Composable
fun ShareTopBarDropDown(
    modifier: Modifier = Modifier,
    actions: List<ShareAction>
) {
    Column(
        modifier = modifier
            .width(140.dp)
            .coloredShadow(
                color = Color.Black,
                alpha = 0.16f,
                borderRadius = 8.dp,
                shadowRadius = 8.dp,
                offsetY = 1.dp,
                offsetX = 8.dp
            )
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ) {
        actions.forEachIndexed { index, action ->
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = action.iconRes),
                    contentDescription = null,
                    tint = Color(51, 51, 51)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = action.title,
                    style = t12.copy(color = Color(0xff333333))
                )
            }
            if (index != actions.lastIndex) {
                Divider(color = Color(0xff18202e).copy(alpha = 0.1f))
            }
        }
    }
}

@Preview
@Composable
fun PreviewShareTopBarDropDown() {
    HagoMandalTheme {
        ShareTopBarDropDown(actions = actions)
    }
}
