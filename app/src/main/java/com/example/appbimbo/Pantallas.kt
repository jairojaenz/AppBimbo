package com.example.appbimbo


import BimboMongodbScreenClientes
import BimboMongodbScreenClientesByAge
import BimboMongodbScreenClientesByCity
import BimboMongodbScreenPedidos
import BimboMongodbScreenPedidosConfirmados
import BimboMongodbScreenPedidosOrderedByTotal
import BimboMongodbScreenPedidosPendientes
import BimboMongodbScreenSucursales
import BimboTabularScreen
import ClienteAgeViewModel
import ClienteCytyViewModel
import ClienteViewModel
import PedidoConfirmadosViewModel
import PedidoOrderedByTotalViewModel
import PedidoPendientesViewModel
import PedidoViewModel
import SucursalViewModel
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ventasproductofechaViewModel

// clase para las opciones de la pantalla de cada opcion del menu
data class opciones(val title: String, val description: String, val neonColor: Color, val flareColor: Color, val onClick: () -> Unit)

// Elementos de la pantalla de inicio
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
// Elementos de la pantalla de opciones de Tabular
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
                navController.navigate(Screen.TABULARCONSULTA1.route)
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

// Elementos de la pantalla de opciones de MongoDB
@Composable
fun MONGOOPCIONESScreen(navController: NavController) {
    val opcionesmongodb = listOf(
        opciones(
            title = "Información de los clientes",
            description = "Ver la información de los clientes de la empresa Bimbo",
            neonColor = Color(0xFFFA0155),
            flareColor = Color(0xFFFA0155),
            onClick = {
                navController.navigate(Screen.CONSULTA1.route)
            }
        ),
        opciones(
            title = "Sucursales que atienden de Lunes a Viernes",
            description = "Ver las sucursales que atienden de Lunes a Viernes de la empresa Bimbo",
            neonColor = Color(0xFFE8FF00),
            flareColor = Color(0xFFFFC107),
            onClick = {
                navController.navigate(Screen.CONSULTA2.route)
            }
        ),
        opciones(
            title = "Clientes menores de 36 años",
            description = "Ver los clientes mayores de 36 años de la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
                navController.navigate(Screen.CONSULTA3.route)
            }
        ),
        opciones(
            title = "Lista de Clientes por Ciudad",
            description = "Ver la lista de clientes por ciudad de la empresa Bimbo",
            neonColor = Color(0xFF00FFC0),
            flareColor = Color(0xFF00FFC0),
            onClick = {
                navController.navigate(Screen.CONSULTA4.route)
            }
        ),
        //buscar todos los pedidos de los clientes
        opciones(
            title = "Pedidos de los clientes",
            description = "Ver los pedidos de los clientes de la empresa Bimbo",
            neonColor = Color(0xFFC600D4),
            flareColor = Color(0xFFC200CF),
            onClick = {
                navController.navigate(Screen.CONSULTA5.route)
            }
        ),
        //pedidos ordenados por la cantidad de pedidos
        opciones(
            title = "Pedidos ordenados por cantidad",
            description = "Ver los pedidos ordenados por cantidad de la empresa Bimbo",
            neonColor = Color(0xFFC99103),
            flareColor = Color(0xFFFFC107),
            onClick = {
                navController.navigate(Screen.CONSULTA6.route)
            }
        ),
        //pedidos que estan "pendientes"
        opciones(
            title = "Pedidos pendientes",
            description = "Ver los pedidos pendientes de la empresa Bimbo",
            neonColor = Color(0xFF0710CC),
            flareColor = Color(0xFF0045CF),
            onClick = {
                navController.navigate(Screen.CONSULTA7.route)
            }
        ),
        //pedidos que estan "Confirmados"
        opciones(
            title = "Pedidos confirmados",
            description = "Ver los pedidos confirmados de la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
                navController.navigate(Screen.CONSULTA8.route)
            }
        ),
        //comentarios de los clientes ordenados por fecha
        opciones(
            title = "Comentarios de los clientes",
            description = "Ver los comentarios de los clientes de la empresa Bimbo",
            neonColor = Color(0xFFFF3300),
            flareColor = Color(0xFFFF1E00),
            onClick = {
                //navController.navigate(Screen.CONSULTA9.route)
            }
        ),
        //facturacion y pagos de los clientes
        opciones(
            title = "Facturación y pagos de los clientes",
            description = "Ver la facturación y pagos de los clientes de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                //navController.navigate(Screen.CONSULTA10.route)
            }
        ),
        //devoluciones co cantidad de productos mayores a 5
        opciones(
            title = "Devoluciones con cantidad de productos mayores a 5",
            description = "Ver las devoluciones con cantidad de productos mayores a 5 de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                //navController.navigate(Screen.CONSULTA11.route)
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

// aqui se crean las pantallas de las consultas de mongodb
@Composable
fun CONSULTA1Screen(navController: NavController) {
    val viewModel: ClienteViewModel = viewModel()
    BimboMongodbScreenClientes(viewModel)
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
fun CONSULTA2Screen(navController: NavController) {
    val viewModel: SucursalViewModel = viewModel()
    BimboMongodbScreenSucursales(viewModel)
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
fun CONSULTA3Screen(navController: NavController) {
    val clienteAgeViewModel: ClienteAgeViewModel = viewModel()
    BimboMongodbScreenClientesByAge(clienteAgeViewModel)
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
fun CONSULTA4Screen(navController: NavController) {
    val clienteCytyViewModel: ClienteCytyViewModel = viewModel()
    BimboMongodbScreenClientesByCity(clienteCytyViewModel)
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
fun CONSULTA5Screen(navController: NavController) {
   val pedidoViewModel: PedidoViewModel = viewModel()
    BimboMongodbScreenPedidos(pedidoViewModel)
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
fun CONSULTA6Screen(navController: NavController) {
    val pedidoOrderedByTotalViewModel: PedidoOrderedByTotalViewModel = viewModel()
    BimboMongodbScreenPedidosOrderedByTotal(pedidoOrderedByTotalViewModel)
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
fun CONSULTA7Screen(navController: NavController) {
   val pedidoPendientesViewModel: PedidoPendientesViewModel = viewModel()
    BimboMongodbScreenPedidosPendientes(pedidoPendientesViewModel)
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
fun CONSULTA8Screen(navController: NavController) {
    val pedidoConfirmadosViewModel: PedidoConfirmadosViewModel = viewModel()
    BimboMongodbScreenPedidosConfirmados(pedidoConfirmadosViewModel)
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


// Cosultas del modelo tabular
@Composable
fun TABULARCONSULTA1Screen(navController: NavController) {
    val ventasproductofechaViewModel: ventasproductofechaViewModel = viewModel()
    BimboTabularScreen(ventasproductofechaViewModel)
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

// boton de regreso
@Composable
fun backbutton(navController: NavController){
    Button(colors = ButtonDefaults.buttonColors(Color(0xFFFA0155)),
        border = BorderStroke(1.dp, Color(0xFFFFFFFF)),
        onClick = { navController.popBackStack() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        //Text(text = "Volver", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
    }
}