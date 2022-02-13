package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t20
import com.greedy0110.hagomandal.ui.theme.t24

data class Career(
    val sector: String,
    val subSectors: List<String>
)

private val careers = listOf(
    // TODO: 개발자는 무엇인가...
    Career(
        sector = "개발자",
        subSectors = listOf(
            "안드로이드 개발자",
            "iOS 개발자",
            "서버 개발자"
        )
    ),
    Career(
        sector = "디자이너",
        subSectors = listOf(
            "UI/UX 디자이너",
            "편집/그래픽 디자이너",
            "영상/모션그래픽 디자이너",
            "일러스트레이터",
            "공간 디자이너",
            "패션 디자이너",
            "3D 그래픽 디자이너",
            "제품 디자이너"
        )
    )
)

@Composable
fun CareerScreen(
    onCareerSelected: () -> Unit,
    onClickOtherCareer: () -> Unit,
) {
    var selectedCareer by remember { mutableStateOf<Career?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xff202632), Color(0xff131b2b))
                    )
                )
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "직업을 선택하면\n" +
                    "더 적합한 목표를 추천할 수 있어!",
                style = t24.copy(textAlign = TextAlign.Start)
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionButton(text = "개발자", onClick = { selectedCareer = careers[0] })
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = "디자이너", onClick = { selectedCareer = careers[1] })
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = "다른 직업은 없어?", emphasize = false, onClick = onClickOtherCareer)
            Spacer(modifier = Modifier.height(74.dp))
        }

        selectedCareer?.let { career ->
            CareerBottomSheet(
                career = career,
                onCareerSelected = { subSector ->
                    // TODO: 선택을 데이터베이스에 저장하기
                    onCareerSelected()
                },
                onDismiss = {
                    selectedCareer = null
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewCareerScreen() {
    HagoMandalTheme {
        CareerScreen({}, {})
    }
}

@Composable
private fun CareerBottomSheet(
    career: Career,
    onCareerSelected: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val backgroundColor = Color.Black.copy(alpha = 0.4f)
    val sheetColor = Color(0xff2d3849)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable { onDismiss() }
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LazyColumn(
            modifier = Modifier
                .background(sheetColor, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = career.sector,
                        style = t20.copy(textAlign = TextAlign.Start)
                    )
                    // TODO: X 아이콘으로 대체
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() },
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            items(career.subSectors) { subSector ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCareerSelected(subSector) }
                        .padding(top = 15.dp, bottom = 14.dp),
                    text = subSector,
                    style = t16.copy(textAlign = TextAlign.Start)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCareerBottomSheet() {
    HagoMandalTheme {
        CareerBottomSheet(career = careers[1])
    }
}
