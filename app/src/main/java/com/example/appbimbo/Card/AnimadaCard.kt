import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbimbo.R
import kotlin.math.sin

@Composable
fun WaterEffectCard() {
    Card(
        modifier = Modifier
            .width(360.dp)
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
          //  Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.bimbo),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                   // .padding(start = .dp) // Agrega padding al inicio de la imagen
            )
            //Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "App de Gestión de Ventas PanPlus",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
         //   Spacer(modifier = Modifier.height(16.dp))
            WaterEffect()
        }
    }
}

@Composable
fun WaterEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
           // .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val waterPath = Path()
            val waterColor = Color(0xFF7A0101)

            waterPath.moveTo(0f, canvasHeight * 0.1f) // Raise the start of the water to 10% of the canvas height
            for (x in 0..canvasWidth.toInt() step 20) {
                val y = sin((x / canvasWidth + offsetY) * 2 * Math.PI) * 20 + canvasHeight * 0.1f // Adjust the amplitude
                waterPath.lineTo(x.toFloat(), y.toFloat())
            }
            waterPath.lineTo(canvasWidth, canvasHeight)
            waterPath.lineTo(0f, canvasHeight)
            waterPath.close()

            drawPath(waterPath, waterColor)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SGV PanPlus",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Grupo #2",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}