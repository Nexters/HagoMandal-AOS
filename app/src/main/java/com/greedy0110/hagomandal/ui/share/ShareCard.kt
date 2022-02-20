package com.greedy0110.hagomandal.ui.share

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.ui.cardColorBrushes
import com.greedy0110.hagomandal.ui.theme.t12
import com.greedy0110.hagomandal.ui.theme.t20

@Composable
fun ShareCard(
    modifier: Modifier = Modifier,
    title: String,
    contents: List<String>,
    colorIndex: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = cardColorBrushes[colorIndex],
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            text = title,
            style = t20
        )
        contents.forEachIndexed { index, content ->
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                text = "ㆍ$content",
                style = t12
            )
            if (index != contents.lastIndex) Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewShareCard() {
    val details = listOf(
        DetailGoal(
            "주식 공부하기",
            listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", "연간 수익율 분석하고 공유하는 스터디 하기"),
            0
        ),
        DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
        DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
        DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
    )

    @Composable
    fun getShareCard(index: Int) {
        ShareCard(
            title = details[index].title,
            contents = details[index].details,
            colorIndex = details[index].colorIndex
        )
    }

    Row {
        Column(Modifier.weight(1f)) {
            getShareCard(index = 0)
            Spacer(modifier = Modifier.height(12.dp))
            getShareCard(index = 2)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            getShareCard(index = 1)
            Spacer(modifier = Modifier.height(12.dp))
            getShareCard(index = 3)
        }
    }
}
