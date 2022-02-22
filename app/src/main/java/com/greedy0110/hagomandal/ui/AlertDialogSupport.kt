package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
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
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t20

@Composable
fun AlertDialogSupport(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    leftButtonText: String = "",
    onLeftClick: () -> Unit = { openDialog.value = false },
    rightButtonText: String = "",
    onRightClick: () -> Unit = { openDialog.value = false },
    openDialog: MutableState<Boolean> = remember { mutableStateOf(false) },
    screen: @Composable () -> Unit = {},
) {
    Box(modifier = modifier) {
        screen()

        val interactionSource = remember { MutableInteractionSource() }
        if (openDialog.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { /* 클릭 이벤트 먹기 */ }
                    .background(Color.Black.copy(0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .padding(horizontal = 58.dp)
                        .background(
                            HagoMandalTheme.colors.secondary,
                            RoundedCornerShape(16.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    HagoMandalText(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = title,
                        style = t20,
                        color = Color(0xff141414),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HagoMandalText(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = content,
                        style = t14,
                        color = Color(0xff666666),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp, top = 12.dp)) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onLeftClick() }
                                .padding(12.dp),
                            text = leftButtonText,
                            style = t14,
                            color = Color(0xff999999),
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onRightClick() }
                                .padding(12.dp),
                            text = rightButtonText,
                            style = t14,
                            color = HagoMandalTheme.colors.primary,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlertDialogSupport() {
    HagoMandalTheme {
        AlertDialogSupport(
            title = "완성된 목표를 삭제할까?",
            content = "완성된 목표는 한 번 삭제하면\n되돌릴 수 없어",
            leftButtonText = "취소",
            rightButtonText = "삭제할래",
            openDialog = remember { mutableStateOf(true) }
        ) {
            Scaffold {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null
                )
            }
        }
    }
}
