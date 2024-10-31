package com.example.appbimbo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BimboUserCard() {
    // Datos estáticos
    val userId = 1
    val title = "Usuario Ejemplo"
    val pais = "Nicaragua"

    val outerGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF4A0E4E), Color(0xFF000000))
    )
    val innerGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF800080), Color(0xFFFF1493), Color(0xFFFF0000))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(topEnd = 70.dp, bottomStart = 16.dp, bottomEnd = 16.dp))// FIGURA DE LA CARD
            .background(outerGradient)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Encabezado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bimbo),
                    contentDescription = "Bimbo Logo",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Bimbo",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Usuario",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Lista de usuarios en Bimbo",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Información del usuario con gradiente interior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 70.dp, bottomStart = 16.dp, bottomEnd = 10.dp))
                    .background(innerGradient)
                    .padding(30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(120.dp)
                ) {
                    UserInfoItemWithIcon("UserId", userId.toString(), R.drawable.gps)
                    UserInfoItemWithIcon("Title", title, R.drawable.hora)
                    UserInfoItemWithIcon("Pais", pais, R.drawable.postal)
                }
            }
        }
    }
}

@Composable
fun UserInfoItemWithIcon(label: String, value: String, iconRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(19.dp))// espacio entre icono y texto
        Text(
            text = "$label: $value",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}