package com.example.appbimbo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(onMenuClick: () -> Unit) {
    // Caja que contiene el fondo degradado y el TopAppBar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFAF0000), Color(0xFF330000)) // Colores del degradado
                )
            )
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "My App de Gestión de Ventas PanPlus",
                    color = Color.White, // Cambia el color del texto aquí
                    fontSize = 20.sp // Cambia el tamaño del texto aquí
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.size(48.dp) // Cambia el tamaño del botón aquí
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White) // Cambia el color del icono aquí
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent // Hace que el TopAppBar sea transparente para que se vea el degradado detrás
            ),
        )
    }
}
