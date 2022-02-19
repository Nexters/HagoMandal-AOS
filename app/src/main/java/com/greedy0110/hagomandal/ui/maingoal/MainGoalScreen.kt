package com.greedy0110.hagomandal.ui.maingoal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.SingleTextField
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.backgroundColor
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24

private val durations = listOf(
    "1개월",
    "3개월",
    "6개월",
    "1년",
    "기간 없이"
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainGoalScreen(
    modifier: Modifier = Modifier,
    mainGoal: String,
    setMainGoal: (String) -> Unit,
    onDone: () -> Unit = {},
) {
    val boxColor = Color(0xff374151)
    var selectedDurations by remember { mutableStateOf("") }
    val hintText = "기간을 선택해봐!"
    val boxTextColor = when {
        selectedDurations.isEmpty() -> Color.White.copy(alpha = 0.3f)
        else -> Color.White
    }
    var showDurationSelector by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "지금 생각하고 있는 목표를 \n" +
                "언제까지 이루고 싶어?",
            style = t24.copy(textAlign = TextAlign.Start)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(boxColor, RoundedCornerShape(8.dp))
                .clickable { showDurationSelector = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedDurations.takeIf { it.isNotBlank() } ?: hintText,
                style = t16.copy(color = boxTextColor)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
                painter = painterResource(id = R.drawable.ic_chevron_down),
                contentDescription = null,
                tint = Color.White
            )
        }
        if (showDurationSelector) {
            Spacer(modifier = Modifier.height(13.dp))
            ListSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                texts = durations,
                onSelected = { index ->
                    selectedDurations = durations[index]
                    showDurationSelector = false
                }
            )
        } else {
            val keyboardController = LocalSoftwareKeyboardController.current
            Spacer(modifier = Modifier.height(20.dp))
            SingleTextField(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = mainGoal,
                onTextChanged = setMainGoal,
                trailingText = "최대 15자",
                maxLength = 15,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onDone()
                    keyboardController?.hide()
                })
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainGoalScreen() {
    HagoMandalTheme {
        MainGoalScreen(
            mainGoal = "", setMainGoal = {}
        )
    }
}

@Composable
fun ListSelector(
    modifier: Modifier = Modifier,
    texts: List<String>,
    onSelected: (index: Int) -> Unit = {},
) {
    val backgroundColor = Color(0xff2d3849)
    val dividerColor = Color(0xff18202e).copy(alpha = 0.4f)

    LazyColumn(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
    ) {
        itemsIndexed(texts) { index, text ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(index) }
                    .padding(horizontal = 22.dp, vertical = 18.dp),
                text = text,
                style = t16
            )
            if (index != texts.lastIndex) {
                Divider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = dividerColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewListSelector() {
    HagoMandalTheme {
        ListSelector(texts = durations)
    }
}
