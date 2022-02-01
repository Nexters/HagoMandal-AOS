package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun NicknameScreen() {
    val (name, setName) = rememberSaveable { mutableStateOf("") }

    // TODO: 시스템 키보드와 어떻게 상호작용 하는가?
    Column(
        modifier = Modifier
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(top = 60.dp, start = 60.dp, end = 60.dp)
                .size(240.dp),
//                .fillMaxWidth()
//                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.bg_nickname),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

        NameTextField(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
            name = name,
            onNameChange = setName,
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun PreviewNicknameScreen() {
    HagoMandalTheme {
        NicknameScreen()
    }
}

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = Color(0xfff7f7f7),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // TODO: 글자 제한은 어떻게 주어야 하는가? onNameChange 내부 로직 변경 필요?
            // TODO: cursor 어떻게 커스텀할 수 있는가?
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = name,
                onValueChange = onNameChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* TODO: not implemented */ })
            )

            Text(text = "최대 10자")
        }
    }
}

@Preview
@Composable
fun PreviewNameTextField() {
    HagoMandalTheme {
        NameTextField(name = "박만달", onNameChange = {})
    }
}
