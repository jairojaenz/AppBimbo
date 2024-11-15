package com.example.appbimbo


import BimboMongodbListComentarios
import BimboMongodbScreenClientes
import BimboMongodbScreenClientesByAge
import BimboMongodbScreenClientesByCity
import BimboMongodbScreenComentarios
import BimboMongodbScreenDevoluciones
import BimboMongodbScreenFacturacionPago
import BimboMongodbScreenPedidos
import BimboMongodbScreenPedidosConfirmados
import BimboMongodbScreenPedidosOrderedByTotal
import BimboMongodbScreenPedidosPendientes
import BimboMongodbScreenSucursales
import BimboTabularScreen
import BimboTabularScreenClientes
import BimboTabularScreenIngresos
import BimboTabularScreenPMenos
import BimboTabularScreenProductos
import BimboTabularScreenProductosFinSemana
import BimboTabularScreenProductosPorProveedor
import BimboTabularScreenPromedio
import BimboTabularScreenSemanales
import BimboTabularScreenTopIngresosClientes
import ClienteAgeViewModel
import ClienteCytyViewModel
import ClienteViewModel
import ComentariosViewModel
import DevolucionViewModel
import FacturacionPagoViewModel
import PMenosVendidosViewModel
import PedidoConfirmadosViewModel
import PedidoOrderedByTotalViewModel
import PedidoPendientesViewModel
import PedidoViewModel
import ProductoPorProveedorViewModel
import ProductosfinSemanaViewModel
import PromedioVentas
import SucursalViewModel
import TopIngresosClientesViewModel
import TopingresosClientes
import android.text.TextUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.common.utils.DataUtils.getColorPaletteList
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import facturacionpago
import ingresosmensualesViewModel
import productoCategoriaViewModel
import promedioventasViewModel
import ventasSemanalesViewModel
import ventasclientesViewModel
import ventasproductofechaViewModel

// clase para las opciones de la pantalla de cada opcion del menu
data class opciones(val title: String, val description: String, val neonColor: Color, val flareColor: Color, val onClick: () -> Unit)

// Elementos de la pantalla de inicio

@Composable
fun HomeScreen(navController: NavController) {

    LazyColumn (
        modifier = Modifier.fillMaxSize()
            .padding(10.dp),
    ){
        item {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.bimbo),
                    contentDescription = "Bimbo Logo",
                    modifier = Modifier.size(100.dp)
                )
                Text(text = "Bienvenido a la App de gestión de ventas",
                    style = TextStyle(fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif),
                    textAlign = TextAlign.Center,)
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp)
                .size(100.dp),
                )
            Text(text = "Resumen de operaciones de empresa Bimbo",
                style = TextStyle(fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif),
                textAlign = TextAlign.Center,
                color = Color(0xFFFA0155))
            BarChartExample()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Ventas de productos por Mes", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
            LineXhart()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Ventas de productos por categoría", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
            PieChartExample()
        }
        item{
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Clientes con mayor cantidad de compras", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
            PieChartExample2()
        }
        item{
            Text(text = "Total de Ventas por Mes", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif))
            gradientbar()
        }
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
                navController.navigate(Screen.TABULARCONSULTA2.route)
            }
        ),
        opciones(
            title = "Ventas de productos por categoría",
            description = "Ver las ventas de productos por categoría en la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
               navController.navigate(Screen.TABULARCONSULTA3.route)
            }
        ),
        opciones(
            title = "Promedio de ventas diarias",
            description = "Ver el promedio de ventas diarias en la empresa Bimbo",
            neonColor = Color(0xFF00FFC0),
            flareColor = Color(0xFF00FFC0),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA4.route)
            }
        ),
        //ingresos totales mesuales de la empresa
        opciones(
            title = "Ingresos totales mensuales",
            description = "Ver los ingresos totales mensuales de la empresa Bimbo",
            neonColor = Color(0xFFC600D4),
            flareColor = Color(0xFFC200CF),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA5.route)
            }
        ),
        //ventas semanales de la empresa
        opciones(
            title = "Ventas semanales",
            description = "Ver las ventas semanales de la empresa Bimbo",
            neonColor = Color(0xFFC99103),
            flareColor = Color(0xFFFFC107),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA6.route)
            }
        ),
        //Productos menos vendidos
        opciones(
            title = "Productos menos vendidos",
            description = "Ver los productos menos vendidos de la empresa Bimbo",
            neonColor = Color(0xFF0710CC),
            flareColor = Color(0xFF0045CF),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA7.route)
            }
        ),
        //Ventas de producto en fin de semana
        opciones(
            title = "Ventas de producto en fin de semana",
            description = "Ver las ventas de producto en fin de semana de la empresa Bimbo",
            neonColor = Color(0xFF00FF00),
            flareColor = Color(0xFF00FF00),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA8.route)
            }
        ),
        //Ventas de prodcuto por proveedor
        opciones(
            title = "Ventas de producto por proveedor",
            description = "Ver las ventas de producto por proveedor de la empresa Bimbo",
            neonColor = Color(0xFFFF3300),
            flareColor = Color(0xFFFF1E00),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA9.route)
            }
        ),
        //Top ingresos por cliente
        opciones(
            title = "Top ingresos por cliente",
            description = "Ver el top de ingresos por cliente de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                navController.navigate(Screen.TABULARCONSULTA10.route)
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
            verticalArrangement = Arrangement.spacedBy(10.dp),
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
                navController.navigate(Screen.CONSULTA9.route)
            }
        ),
        //facturacion y pagos de los clientes
        opciones(
            title = "Facturación y pagos de los clientes",
            description = "Ver la facturación y pagos de los clientes de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                navController.navigate(Screen.CONSULTA10.route)
            }
        ),
        //devoluciones co cantidad de productos mayores a 5
        opciones(
            title = "Devoluciones con cantidad de productos mayores a 5",
            description = "Ver las devoluciones con cantidad de productos mayores a 5 de la empresa Bimbo",
            neonColor = Color(0xFF00FFD9),
            flareColor = Color(0xFF00FFB7),
            onClick = {
                navController.navigate(Screen.CONSULTA11.route)
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
            verticalArrangement = Arrangement.spacedBy(10.dp),
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
@Composable
fun CONSULTA9Screen(navController: NavController) {
    val viewModel: ComentariosViewModel = viewModel()
    BimboMongodbScreenComentarios(viewModel)
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
fun CONSULTA10Screen(navController: NavController) {
    val viewModel: FacturacionPagoViewModel = viewModel()

    BimboMongodbScreenFacturacionPago(viewModel)
    goBack(navController = navController)


}
@Composable
fun CONSULTA11Screen(navController: NavController) {
    val viewModel: DevolucionViewModel = viewModel()

    BimboMongodbScreenDevoluciones(viewModel)
    goBack(navController = navController)
}

// Cosultas del modelo tabular
@Composable
fun TABULARCONSULTA1Screen(navController: NavController) {
    val ventasproductofechaViewModel: ventasproductofechaViewModel = viewModel()
    BimboTabularScreen(ventasproductofechaViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA2Screen(navController: NavController) {
    val ventasClientesViewModel: ventasclientesViewModel = viewModel()

    BimboTabularScreenClientes(ventasClientesViewModel)
    goBack(navController = navController)

}
@Composable
fun TABULARCONSULTA3Screen(navController: NavController) {
    val productoCategoriaViewModel: productoCategoriaViewModel = viewModel()

    BimboTabularScreenProductos(productoCategoriaViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA4Screen(navController: NavController) {
    val promedioVentasViewModel: promedioventasViewModel = viewModel()

    BimboTabularScreenPromedio(promedioVentasViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA5Screen(navController: NavController) {
    val ingresosmensualesViewModel: ingresosmensualesViewModel = viewModel()

    BimboTabularScreenIngresos(ingresosmensualesViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA6Screen(navController: NavController) {
    val ventassemanalesViewModel: ventasSemanalesViewModel = viewModel()

    BimboTabularScreenSemanales(ventassemanalesViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA7Screen(navController: NavController) {
    val pmenosvendidosViewModel: PMenosVendidosViewModel = viewModel()

    BimboTabularScreenPMenos(pmenosvendidosViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA8Screen(navController: NavController) {
    val productosfinSemanaViewModel: ProductosfinSemanaViewModel = viewModel()

    BimboTabularScreenProductosFinSemana(productosfinSemanaViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA9Screen(navController: NavController) {
    val productoPorProveedorViewModel: ProductoPorProveedorViewModel = viewModel()

    BimboTabularScreenProductosPorProveedor(productoPorProveedorViewModel)
    goBack(navController = navController)
}
@Composable
fun TABULARCONSULTA10Screen(navController: NavController) {
    val topIngresosClientesViewModel:TopIngresosClientesViewModel = viewModel()

    BimboTabularScreenTopIngresosClientes(topIngresosClientesViewModel)
    goBack(navController = navController)
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

@Composable
fun goBack(navController: NavController){
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
fun PieChartExample() {
    PieChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(
                    value = 20f,
                    color = Color.hsv(180f, 0.5f, 1f),
                    label = "Pan",
                    sliceDescription = { "Slice 1: 20%" }
                ),
                PieChartData.Slice(
                    value = 30f,
                    color = Color.hsv(120f, 0.5f, 1f),
                    label = "Galletas",
                    sliceDescription = { "Slice 2: 30%" }
                ),
                PieChartData.Slice(
                    value = 50f,
                    color = Color.hsv(220f, 0.5f, 1f),
                    label = "Pastel",
                    sliceDescription = { "Slice 3: 50%" }
                ),
                PieChartData.Slice(
                    value = 100f,
                    color = Color.hsv(10f, 0.5f, 1f),
                    label = "Pan dulce",
                    sliceDescription = { "Slice 4: 100%" }
                )

            ),
            plotType = PlotType.Pie
        ),
        pieChartConfig = PieChartConfig(
            labelColor = Color.White,
            isAnimationEnable = true,
            sliceLabelTextColor = Color.White,
            labelFontSize = 30.sp,
            sliceLabelTextSize = 15.sp,
            animationDuration = 1200,
            strokeWidth = 9f,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            inActiveSliceAlpha = 0.8f,
            isClickOnSliceEnabled = true,
            sliceMinTextWidthToEllipsize = 50.dp,
            backgroundColor = Color.Transparent,
            activeSliceAlpha = 1f,
        ),
        onSliceClick = { slice ->
            slice.label?.let {
                // Handle slice click

            }
        }
    )
}
@Composable
fun BarChartExample() {
    val barSize = 3
    val barChartListSize = 12
    val maxRange = 10000
    val eachGroupBarSize = 3
    val groupBarPlotData = BarPlotData(
        groupBarList = DataUtils.getGroupBarChartData(
            barChartListSize,
            maxRange,
            eachGroupBarSize
        ),
        barColorPaletteList = getColorPaletteList(barSize)
    )
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(groupBarPlotData.groupBarList.size )
        .bottomPadding(10.dp)
        .labelData { i ->
            listOf("2012", "2014", "2015", "2016","2017","2018","2019","2020","2021","2022","2023","2024")[i]
        }
        .build()

    val yStepSize = 1
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(10.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()

    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )

    Column (
        modifier = Modifier.width(600.dp)
            .height(400.dp)
            .padding(top = 90.dp)
    ){
        Text(text = "Ventas de la empresa Bimbo por año",
            style = TextStyle(fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif),
            textAlign = TextAlign.Center,
            color = Color(0xFF000000)
        )
        GroupBarChart(modifier = Modifier.fillMaxSize(), groupBarChartData = groupBarChartData)
    }
}
@Composable
fun LineXhart() {
    val pointsData: List<Point> = listOf(
        Point(0f, 600f),
        Point(1f, 750f),
        Point(2f, 800f),
        Point(3f, 900f),
        Point(4f, 650f),
        Point(5f, 700f),
        Point(6f, 850f),
        Point(7f, 950f),
        Point(8f, 700f),
        Point(9f, 800f),
        Point(10f, 900f),
        Point(11f, 1000f)
    )
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")[i]
        }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisSteps = 5
    val yAxisData = AxisData.Builder()
        .steps(yAxisSteps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 500 + (i * 100)
            yScale.toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        color = Color.Blue,
                        colorFilter = ColorFilter.tint(Color.Cyan)
                    ),
                    intersectionPoint = IntersectionPoint(
                        color = Color.Blue,
                        blendMode = BlendMode.Hardlight
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = Color.Blue,
                        blendMode = BlendMode.Hardlight
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Blue, Color.Green) // Ensure at least two colors
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        labelColor = Color.Black,
                    )
                )
            ),
        ),
        backgroundColor = Color.hsv(0f, 0f, 0.95f),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            color = Color.Cyan,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(top = 20.dp),
        lineChartData = lineChartData
    )
}
@Composable
fun PieChartExample2() {
    PieChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(
                    value = 20f,
                    color = Color.hsv(180f, 0.5f, 1f),
                    label = "Juan Perez",
                    sliceDescription = { "Slice 1: 20%" }
                ),
                PieChartData.Slice(
                    value = 30f,
                    color = Color.hsv(10f, 0.5f, 1f),
                    label = "Maria Lopez",
                    sliceDescription = { "Slice 2: 30%" }
                ),
                PieChartData.Slice(
                    value = 50f,
                    color = Color.hsv(20f, 0.5f, 1f),
                    label = "Pedro Ramirez",
                    sliceDescription = { "Slice 3: 50%" }
                ),
                PieChartData.Slice(
                    value = 100f,
                    color = Color.hsv(160f, 0.5f, 1f),
                    label = "Carlos Hernandez",
                    sliceDescription = { "Slice 4: 100%" }
                )

            ),
            plotType = PlotType.Donut
        ),
        pieChartConfig = PieChartConfig(
            labelColor = Color.White,
            isAnimationEnable = true,
            sliceLabelTextColor = Color.Red,
            animationDuration = 1200,
            strokeWidth = 2f,
            sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
            inActiveSliceAlpha = 0.8f,
            backgroundColor = Color.Transparent,
            isClickOnSliceEnabled = true
        ),
        onSliceClick = { slice ->
            slice.label?.let {
                // Handle slice click

            }
        }
    )
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun gradientbar(){
    val barChartListSize = 12
    val maxRange = 500
    val barChartData2 = DataUtils.getBarChartData(
        barChartListSize, maxRange,
        barChartType = BarChartType.VERTICAL,
        dataCategoryOptions = DataCategoryOptions(
            isDataCategoryInYAxis = true,
            isDataCategoryStartFromBottom = true
        )
    )
    val xAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .steps(barChartData2.size - 1)
        .bottomPadding(20.dp)
        .axisLabelAngle(20f)
        .labelData { i ->
        listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")[i]
    }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(3)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / 3)).toString() }
        .build()
    val barChartData = BarChartData(
        chartData = barChartData2,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
    )
    BarChart(modifier = Modifier.height(500.dp).fillMaxWidth()
        , barChartData = barChartData)
}