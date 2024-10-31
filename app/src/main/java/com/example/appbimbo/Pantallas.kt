package com.example.appbimbo


import Album
import AlbumViewModel
import BimboAlbumCard
import BimboAlbumList
import BimboAlbumScreen
import BimboCommentCard
import Comment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NeonEffectCard(
                title = "Albumnes",
                description = "Esta consulta muestra los albumnes de una api alojada en un servidor remoto",
                neonColor = Color(0xFFFA0155),
                flareColor = Color(0xFFFA0155), // Pass flareColor
                onClick = {
                    navController.navigate(Screen.CONSULTA1.route)
                }
            )

            NeonEffectCard(
                title = "Top de los mejores clientes",
                description = "Esta consulta muestra el top de los mejores clientes de la empresa Bimbo",
                neonColor = Color(0xFFE8FF00),
                flareColor = Color(0xFFFFC107), // Pass flareColor
                onClick = {
                    //navController.navigate(Screen.CONSULTA2.route)
                }
            )

            NeonEffectCard(
                title = "Ingresos por mes",
                description = "Esta consulta muestra los ingresos por mes de la empresa Bimbo",
                neonColor = Color(0xFF00FF00),
                flareColor = Color(0xFF00FF00), // Pass flareColor
                onClick = {
                   // navController.navigate(Screen.CONSULTA3.route)
                }
            )

        }
    }
}



@Composable
fun CONSULTA1Screen() {
    val viewModel: AlbumViewModel = viewModel()
    BimboAlbumScreen(viewModel)
}
@Composable
fun CONSULTA2Screen() {
    CenteredText("Consulta 2")
}
@Composable
fun CONSULTA3Screen() {
    CenteredText("Consulta 3")
}

@Composable
fun EDAScreen() {

    //BimboUserCard()
    val viewModel: AlbumViewModel = viewModel()
    BimboAlbumScreen(viewModel)

   // CenteredText("EDA Screen")

}

@Composable
fun CMIScreen() {

    BimboSucursalCard()

    //CenteredText("CMI Screen")
}

@Composable
fun MONGOScreen() {
    val sampleComment = Comment(
        postId = 1,
        id = 1,
        name = "John Doe",
        email = "john@example.com",
        body = "This is a sample comment body."
    )
    BimboCommentCard(comment = sampleComment)

//CenteredText("Consultas Mongo")
}

@Composable
fun CUBEOLAPScreen() {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        NeonEffectCard(
            title = "Home",
            description = "Bienvenido a la App Bimbo",
            neonColor = Color(0xFF8400FF),
            flareColor = Color(0xFF8400FF),
            onClick = {
            }
        )
    }
}



@Composable
fun CenteredText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}