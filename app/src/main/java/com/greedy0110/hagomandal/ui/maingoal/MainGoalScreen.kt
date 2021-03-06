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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.SingleTextField
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24

private val durations = listOf(
    "1??????",
    "3??????",
    "6??????",
    "1???",
    "?????? ??????"
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainGoalScreen(
    modifier: Modifier = Modifier,
    mainGoal: String,
    setMainGoal: (String) -> Unit,
    onDone: () -> Unit = {},
) {
    var selectedDurations by remember { mutableStateOf("") }
    val hintText = "????????? ????????????!"
    val boxTextColor = when {
        selectedDurations.isEmpty() -> Color.White.copy(alpha = 0.3f)
        else -> Color.White
    }
    var showDurationSelector by remember { mutableStateOf(true) }
    val mainGoalFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HagoMandalTheme.colors.background)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "?????? ???????????? ?????? ????????? \n" +
                "???????????? ????????? ???????",
            style = t24.copy(textAlign = TextAlign.Start)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(HagoMandalTheme.colors.surface, RoundedCornerShape(8.dp))
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
                modifier = Modifier
                    .focusRequester(mainGoalFocusRequester)
                    .padding(horizontal = 20.dp),
                text = mainGoal,
                onTextChanged = setMainGoal,
                trailingText = "?????? 15???",
                maxLength = 15,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onDone()
                    keyboardController?.hide()
                })
            )

            LaunchedEffect(key1 = Unit) {
                mainGoalFocusRequester.requestFocus()
            }
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
    val dividerColor = Color(0xff18202e).copy(alpha = 0.4f)

    LazyColumn(
        modifier = modifier
            .background(HagoMandalTheme.colors.surface, RoundedCornerShape(8.dp))
    ) {
        itemsIndexed(texts) { index, text ->
            HagoMandalText(
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
