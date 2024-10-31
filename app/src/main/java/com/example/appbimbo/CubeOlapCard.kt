package com.example.appbimbo

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NeonEffectCard(
    title: String,
    description: String,
    neonColor: Color = Color(0xFFE8FF00),
    flareColor: Color = Color(0xFFFFFFFF),
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var showFlare by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = if (isPressed) 0.8f else 0.5f,
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val flarePosition by animateFloatAsState(
        targetValue = if (showFlare) 1f else -0.2f,
        animationSpec = tween(500, easing = LinearEasing)
    )

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                // Al hacer clic, primero muestra el flare
                isPressed = true
                showFlare = true
                coroutineScope.launch {
                    delay(500) // Espera 500 ms para el efecto
                    showFlare = false
                    isPressed = false
                    onClick() // Luego llama a la función onClick
                }
            }
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.3f)
                        )
                    )
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp)
                .drawBehind {
                    drawRect(
                        color = neonColor.copy(alpha = glowAlpha),
                        topLeft = Offset(-40f, 0f),
                        size = size.copy(width = 120f)
                    )
                }
                .shadow(
                    elevation = if (isPressed) 50.dp else 30.dp,
                    shape = RoundedCornerShape(6.dp),
                    clip = false
                )
                .drawWithContent {
                    drawContent()
                    if (showFlare) {
                        val flareWidth = size.width * 0.4f
                        val flareHeight = size.height
                        val flareX = size.width * flarePosition - flareWidth / 2

                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(flareColor.copy(alpha = 0.7f), flareColor.copy(alpha = 0f)),
                                center = Offset(flareX + flareWidth / 2, flareHeight / 2),
                                radius = flareWidth / 2
                            ),
                            topLeft = Offset(flareX, 0f),
                            size = androidx.compose.ui.geometry.Size(flareWidth, flareHeight)
                        )
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.1f),
                                Color.White.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .blur(radius = 9.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 50.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun NeonEffectCardPreview() {
    NeonEffectCard(
        title = "Ejemplo",
        description = "Esta es una tarjeta de ejemplo con efecto neón y destello",
        neonColor = Color(0xFFE8FF00),
        flareColor = Color(0xFFFFFFFF),
        onClick = { /* Acción al hacer clic */ }
    )
}