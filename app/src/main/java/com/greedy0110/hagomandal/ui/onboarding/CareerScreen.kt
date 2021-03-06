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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.HagoMandalText
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
    onBoardingViewModel: OnBoardingViewModel = viewModel(),
) {
    var selectedCareer by remember { mutableStateOf<Career?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(HagoMandalTheme.colors.background)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "직업을 선택하면\n더 적합한 목표를 추천할 수 있어!",
                style = t24,
                textAlign = TextAlign.Start
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
                    onBoardingViewModel.setJob(career.sector, subSector)
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
    val sheetColor = HagoMandalTheme.colors.surface

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
                    HagoMandalText(
                        modifier = Modifier.weight(1f),
                        text = career.sector,
                        style = t20,
                        textAlign = TextAlign.Start,
                        color = HagoMandalTheme.colors.onSurface
                    )
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() },
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = HagoMandalTheme.colors.onSurface
                    )
                }
            }
            items(career.subSectors) { subSector ->
                HagoMandalText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCareerSelected(subSector) }
                        .padding(top = 15.dp, bottom = 14.dp),
                    text = subSector,
                    style = t16,
                    textAlign = TextAlign.Start,
                    color = HagoMandalTheme.colors.onSurface.copy(alpha = 0.8f)
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
