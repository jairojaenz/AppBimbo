package com.example.appbimbo

import WaterEffectCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appbimbo.R

@Composable
fun AppDrawerContent(
    navController: NavController,
    onDestinationClicked: (route: String) -> Unit
) {
    var selectedButton by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(360.dp)
            .border(3.dp, Color.White)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFAF0000), Color(0xFF000000))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFAF0000), Color(0xFF9C0101))
                        )
                    )
                    .border(1.dp, Color.Black).shadow(1.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WaterEffectCard()
                }
            }

            DrawerButton(
                text = "Home",
                icon = painterResource(id = R.drawable.hogar_2),
                isSelected = selectedButton == Screen.Home.route,
                onClick = {
                    selectedButton = Screen.Home.route
                    onDestinationClicked(Screen.Home.route)
                }
            )

            DrawerButton(
                text = "Modelo Tabular",
                icon = painterResource(id = R.drawable.modeltabular),
                isSelected = selectedButton == Screen.EDA.route,
                onClick = {
                    selectedButton = Screen.EDA.route
                    onDestinationClicked(Screen.EDA.route)
                }
            )

            DrawerButton(
                text = "MongoDB",
                icon = painterResource(id = R.drawable.mongo),
                isSelected = selectedButton == Screen.MONGO.route,
                onClick = {
                    selectedButton = Screen.MONGO.route
                    onDestinationClicked(Screen.MONGO.route)
                }
            )

        }
    }
}
@Composable
fun DrawerButton(
    text: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) Color.White else Color.Transparent
    val contentColor = if (isSelected) Color.Black else Color.White

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = text,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
        }
    }
}