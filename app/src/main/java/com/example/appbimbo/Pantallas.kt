package com.example.appbimbo


import AlbumViewModel
import BimboAlbumScreen
import BimboCommentCard
import Comment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

data class opciones(val title: String, val description: String, val neonColor: Color, val flareColor: Color, val onClick: () -> Unit)
@Composable
fun HomeScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.bimbo),
            contentDescription = "Bimbo Logo",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "Bienvenido a la App Bimbo", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
    }

}
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TABULARScreen(navController: NavController) {
    val tabularopciones = listOf(
        opciones(
            title = "Ventas de Productos por fecha",
            description = "Ver las ventas de productos por fecha en la empresa Bimbo",
            neonColor = Color(0xFFFA0155),
            flareColor = Color(0xFFFA0155),
            onClick = {
                navController.navigate(Screen.CONSULTA1.route)
            }
        ),
        opciones(
            title = "Clientes con mayor cantidad de compras",
            description = "Ver los clientes con mayor cantidad de compras en la empresa Bimbo",
            neonColor = Color(0xFFE8FF00),
            flareColor = Color(0xFFFFC107),
            onClick = {
                //navController.navigate(Screen.CONSULTA2.route)
            }
        ),
        opciones(
            title = "Ventas de productos por categoría",
            description = "Ver las ventas de productos por categoría en la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
               // navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        opciones(
            title = "Promedio de ventas por diarias",
            description = "Ver el promedio de ventas diarias en la empresa Bimbo",
            neonColor = Color(0xFF00FFC0),
            flareColor = Color(0xFF00FFC0),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //ingresos totales mesuales de la empresa
        opciones(
            title = "Ingresos totales mensuales",
            description = "Ver los ingresos totales mensuales de la empresa Bimbo",
            neonColor = Color(0xFFC600D4),
            flareColor = Color(0xFFC200CF),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //ventas semanales de la empresa
        opciones(
            title = "Ventas semanales",
            description = "Ver las ventas semanales de la empresa Bimbo",
            neonColor = Color(0xFFC99103),
            flareColor = Color(0xFFFFC107),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //Productos menos vendidos
        opciones(
            title = "Productos menos vendidos",
            description = "Ver los productos menos vendidos de la empresa Bimbo",
            neonColor = Color(0xFF0710CC),
            flareColor = Color(0xFF0045CF),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //Ventas de producto en fin de semana
        opciones(
            title = "Ventas de producto en fin de semana",
            description = "Ver las ventas de producto en fin de semana de la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //Ventas de prodcuto por proveedor
        opciones(
            title = "Ventas de producto por proveedor",
            description = "Ver las ventas de producto por proveedor de la empresa Bimbo",
            neonColor = Color(0xFFFF3300),
            flareColor = Color(0xFFFF1E00),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        //Top ingresos por cliente
        opciones(
            title = "Top ingresos por cliente",
            description = "Ver el top de ingresos por cliente de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(tabularopciones) { opcion ->
                NeonEffectCard(title = opcion.title, description = opcion.description, neonColor = opcion.neonColor, flareColor = opcion.flareColor, onClick = opcion.onClick) {
                }
            }
        }


    }
}


@Composable
fun CONSULTA1Screen(navController: NavController) {
    val viewModel: AlbumViewModel = viewModel()
    BimboAlbumScreen(viewModel)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
    ) {
        backbutton(navController = navController)
    }
}
@Composable
fun CONSULTA2Screen() {
    CenteredText("Consulta 2")
}
@Composable
fun CONSULTA3Screen() {
    CenteredText("Consulta 3")
}

//@Composable
//fun EDAScreen() {
//
//    //BimboUserCard()
//    val viewModel: AlbumViewModel = viewModel()
//    BimboAlbumScreen(viewModel)
//
//   // CenteredText("EDA Screen")
//
//}

@Composable
fun CMIScreen() {

    BimboSucursalCard()

    //CenteredText("CMI Screen")
}
@Composable
fun MONGOOPCIONESScreen(navController: NavController) {
    val opcionesmongodb = listOf(
        opciones(
            title = "Consultas Mongo",
            description = "Esta consulta muestra los albumnes de una api alojada en un servidor remoto",
            neonColor = Color(0xFFFA0155),
            flareColor = Color(0xFFFA0155),
            onClick = {
                navController.navigate(Screen.MONGO.route)
            }
        ),
        opciones(
            title = "Top de los mejores clientes",
            description = "Esta consulta muestra el top de los mejores clientes de la empresa Bimbo",
            neonColor = Color(0xFFE8FF00),
            flareColor = Color(0xFFFFC107),
            onClick = {
                //navController.navigate(Screen.CONSULTA2.route)
            }
        ),
        opciones(
            title = "Ingresos por mes",
            description = "Esta consulta muestra los ingresos por mes de la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
                //navController.navigate(Screen.CONSULTA3.route)
            }
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(opcionesmongodb) { opcion ->
                NeonEffectCard(title = opcion.title, description = opcion.description, neonColor = opcion.neonColor, flareColor = opcion.flareColor, onClick = opcion.onClick) {
                }
            }
        }


    }
}
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
@Composable
fun MONGOScreen(navController: NavController) {
    val listComents = listOf<Comment>(
        Comment(
            postId = 1,
            id = 1,
            name = "John Doe",
            email = "john@example.com",
            body = "This is a sample comment body."
        ),
        Comment(
            postId = 1,
            id = 2,
            name = "Jane Doe",
            email = "john@example.com",
            body = "This is a sample comment body."
        ),
        Comment(
            postId = 1,
            id = 3,
            name = "John Smith",
            email = "john@example.com",
            body = "This is a sample comment body."
        ),
        Comment(
            postId = 1,
            id = 4,
            name = "Jane Smith",
            email = "john@example.com",
            body = "This is a sample comment body."
        ),)

    //lazy column
    LazyColumn {
        items(listComents) { comment ->
            BimboCommentCard(comment = comment)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
    ) {
        backbutton(navController = navController)
    }

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
            },
            function = {
            }
        )
    }
}

@Composable
fun backbutton(navController: NavController){
    Button(colors = ButtonDefaults.buttonColors(Color(0xFFFA0155)),
        border = BorderStroke(1.dp, Color(0xFFFFFFFF)),
        onClick = { navController.popBackStack() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        //Text(text = "Volver", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
    }
}

@Composable
fun CenteredText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text , color = Color.Red,
            style = TextStyle(fontSize = 24.sp), fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
    }
}