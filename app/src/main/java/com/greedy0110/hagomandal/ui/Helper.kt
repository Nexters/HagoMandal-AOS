package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t12

@Composable
fun Helper(
    modifier: Modifier = Modifier,
    message: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.End
    ) {
        MessageBubble(message = message)
        Spacer(modifier = Modifier.height(3.dp))
        Image(
            modifier = Modifier.size(40.dp, 35.dp),
            painter = painterResource(id = R.drawable.gp_happy_mandal),
            contentDescription = "작고 귀여운 헬퍼"
        )
    }
}

@Preview
@Composable
fun PreviewHelper() {
    HagoMandalTheme {
        Helper(message = "여기야 여기! 나를 눌러봐!")
    }
}

@Composable
private fun MessageBubble(message: String) {
    ConstraintLayout {
        val (box, triangle) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(box) {
                }
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(text = message, style = t12.copy(color = Color(0xff202632)))
        }
        Canvas(
            modifier = Modifier
                .constrainAs(triangle) {
                    top.linkTo(box.bottom)
                    end.linkTo(box.end, margin = 12.dp)
                }
                .size(width = 8.dp, height = 6.dp)
        ) {
            val halfWidth = size.width / 2

            val trianglePath = Path().apply {
                moveTo(0f, 0f)
                lineTo(halfWidth, size.height)
                lineTo(size.width, 0f)
                close()
            }

            // https://stackoverflow.com/questions/69748987/how-to-draw-rounded-corner-polygons-in-jetpack-compose-canvas
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        color = Color.White
                        pathEffect = PathEffect.cornerPathEffect(1f)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMessageBubble() {
    HagoMandalTheme {
        Column {
            MessageBubble(message = "여기야 여기! 나를 눌러봐!")
        }
    }
}
