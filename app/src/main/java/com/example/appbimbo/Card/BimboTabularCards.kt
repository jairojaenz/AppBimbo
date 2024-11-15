import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbimbo.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.gson.annotations.SerializedName
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val URLTABULARAPI = "http://apitabularserver.westus2.cloudapp.azure.com/apitab/TABULAR/"

// aqui se crean las data class para el manejo de la informacion de las tarjetas
data class VentaProducto(
    @SerializedName("Nombre del mes") val mes: String,
    @SerializedName("Nombre Producto") val nombreDelProducto: String,
    @SerializedName("Ventas totales") val ventaTotal: Double,
    val iconRes: Int
)
data class VentasClientes(
    @SerializedName("Nombre Cliente") val nombreDelCliente: String,
    @SerializedName("Cantidad ventas") val ventaTotal: Double,
    val iconRes: Int
)
data class productoCategoria(
    @SerializedName("Nombre Producto") val nombreDelProducto: String,
    @SerializedName("Categoria Producto") val categoria: String,
    @SerializedName("Ventas totales") val ventaTotal: Double,
    val iconRes: Int
)
data class PromedioVentas(
    @SerializedName("Nombre del mes") val mes: String,
    @SerializedName("Promedio ventas diarias") val promedioVentas: Double,
    val iconRes: Int
)
data class  IngresosMensuales(
    @SerializedName("Nombre del mes") val mes: String,
    @SerializedName("Ventas totales") val ingresos: Double,
)
data class VentasSemanales(
    @SerializedName("Nombre del mes") val mes: String,
    @SerializedName("Anio") val anio: Int,
    @SerializedName("Semana del mes") val semanames: Int,
    @SerializedName("Venta totales") val ventaTotal: Double,
)
data class PMenosVendidos(
    @SerializedName("Nombre Producto") val nombreDelProducto: String,
    @SerializedName("Cantidad ventas") val ventaTotal: Double,
    val iconRes: Int
)
data class ProductosfinSemana(
    @SerializedName("Nombre Producto") val nombreDelProducto: String,
    @SerializedName("Cantidad ventas") val ventaTotal: Double,
)
data class ProductosPorProveedor(
    @SerializedName("Nombre Producto") val nombreDelProducto: String,
    @SerializedName("Nombre Proveedor") val nombreDelProveedor: String,
    @SerializedName("Cantidad ventas") val ventaTotal: Double
)
data class TopingresosClientes(
    @SerializedName("Nombre Cliente") val nombreDelCliente: String,
    @SerializedName("Ventas totales") val ingresos: Double,
)


// Data model
data class Album(
    val userId: Int,
    val id: Int,
    val title: String
)

// interface para el manejo de la informacion de las tarjetas
interface VentaProductoApiService {
    @GET("Venta de productos por fecha")
    suspend fun getVentas(): List<VentaProducto>
}
interface VentasClientesApiService {
    @GET("clientes con mayor cantidad de ventas")
    suspend fun getVentasClientes(): List<VentasClientes>
}
interface ProductoCategoriaApiService {
    @GET("Ventas de productos por categoria")
    suspend fun getProductosCategoria(): List<productoCategoria>
}
interface PromedioVentasApiService {
    @GET("Promedio de ventas diarias")
    suspend fun getPromedioVentas(): List<PromedioVentas>
}
interface IngresosMensualesApiService {
    @GET("Ingresos totales mensuales")
    suspend fun getIngresosMensuales(): List<IngresosMensuales>
}
interface VentasSemanalesApiService {
    @GET("Ventas semanales")
    suspend fun getVentasSemanales(): List<VentasSemanales>
}
interface PMenosVendidosApiService {
    @GET("productos menos vendidos")
    suspend fun getPMenosVendidos(): List<PMenosVendidos>
}
interface ProductosfinSemanaApiService {
    @GET("Ventas de productos en fin de semana")
    suspend fun getProductosfinSemana(): List<ProductosfinSemana>
}
interface ProductosPorProveedorApiService {
    @GET("Venta de productos por proveedor")
    suspend fun getProductosProveedor(): List<ProductosPorProveedor>
}
interface TopIngresosClientesApiService {
    @GET("Top ingresos por cliente")
    suspend fun getTopIngresosClientes(): List<TopingresosClientes>
}

// API Service
interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}

fun coneccionTabularApi(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(URLTABULARAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
// aqui se crean los viewmodels para el manejo de la informacion de las tarjetas
class ventasproductofechaViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<VentaProducto>>(emptyList())
    val ventas : StateFlow<List<VentaProducto>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(VentaProductoApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getVentas()
        }
    }


}
// ViewModel
class AlbumViewModel : ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val albumApiService = retrofit.create(AlbumApiService::class.java)

    private var currentPage = 0
    private val pageSize = 1
    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val fetchedAlbums = albumApiService.getAlbums()
                _albums.value = fetchedAlbums.take(pageSize)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadMoreAlbums() {
        viewModelScope.launch {
            try {
                val allAlbums = albumApiService.getAlbums()
                val nextPage = currentPage + 1
                val startIndex = nextPage * pageSize
                val endIndex = startIndex + pageSize
                if (startIndex < allAlbums.size) {
                    _albums.value = _albums.value + allAlbums.subList(startIndex, endIndex.coerceAtMost(allAlbums.size))
                    currentPage = nextPage
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
class ventasclientesViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<VentasClientes>>(emptyList())
    val ventas : StateFlow<List<VentasClientes>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(VentasClientesApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getVentasClientes()
        }
    }
}
class productoCategoriaViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<productoCategoria>>(emptyList())
    val ventas : StateFlow<List<productoCategoria>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(ProductoCategoriaApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getProductosCategoria()
        }
    }
}
class promedioventasViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<PromedioVentas>>(emptyList())
    val ventas : StateFlow<List<PromedioVentas>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(PromedioVentasApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getPromedioVentas()
        }
    }
}
class ingresosmensualesViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<IngresosMensuales>>(emptyList())
    val ventas : StateFlow<List<IngresosMensuales>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(IngresosMensualesApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getIngresosMensuales()
        }
    }
}
class ventasSemanalesViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<VentasSemanales>>(emptyList())
    val ventas : StateFlow<List<VentasSemanales>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(VentasSemanalesApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getVentasSemanales()
        }
    }
}
class PMenosVendidosViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<PMenosVendidos>>(emptyList())
    val ventas : StateFlow<List<PMenosVendidos>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(PMenosVendidosApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getPMenosVendidos()
        }
    }
}
class ProductosfinSemanaViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<ProductosfinSemana>>(emptyList())
    val ventas : StateFlow<List<ProductosfinSemana>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(ProductosfinSemanaApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getProductosfinSemana()
        }
    }
}
class ProductoPorProveedorViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<ProductosPorProveedor>>(emptyList())
    val ventas : StateFlow<List<ProductosPorProveedor>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(ProductosPorProveedorApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getProductosProveedor()
        }
    }
}
class TopIngresosClientesViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<TopingresosClientes>>(emptyList())
    val ventas : StateFlow<List<TopingresosClientes>> = _ventas

    private val retrofit = coneccionTabularApi()

    private val VentasApiService = retrofit.create(TopIngresosClientesApiService::class.java)

    init {
        viewModelScope.launch {
            _ventas.value = VentasApiService.getTopIngresosClientes()
        }
    }
}


@Composable
fun BimboTabularCard(data: List<Triple<String, String, Int>>, Titulo: String) {
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
            .clip(RoundedCornerShape(topEnd = 70.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(outerGradient)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Header
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
                        text = Titulo,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

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
                    LazyColumn {
                        items(data) { item ->
                            Filas(label = item.first, value = item.second, iconRes = item.third)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Filas(label: String, value: String, iconRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(19.dp))
        Text(
            text = "$label: $value",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
@Composable
fun TabularVentasList(ventasproductofechaViewModel: ventasproductofechaViewModel){
    val ventas by ventasproductofechaViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Mes:", venta.mes, R.drawable.mes),
                Triple("Nombre del producto:", venta.nombreDelProducto, R.drawable.pan),
                Triple("Venta total:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Ventas por producto por Mes")
        }
    }
}

@Composable
fun TabularVentasClientesList(ventasclientesViewModel: ventasclientesViewModel){
    val ventas by ventasclientesViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del cliente:", venta.nombreDelCliente, R.drawable.proveedor),
                Triple("Cantidad de ventas:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Clientes con mayor cantidad de compras")
        }
    }
}

@Composable
fun TabularVentasProductosList(productoCategoriaViewModel: productoCategoriaViewModel){
    val ventas by productoCategoriaViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del producto:", venta.nombreDelProducto, R.drawable.nombreproducto),
                Triple("Categoria:", venta.categoria, R.drawable.check),
                Triple("Cantidad de ventas:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Ventas de productos por categoría")
        }
    }
}

@Composable
fun TabularPromedioVentasList(promedioventasViewModel: promedioventasViewModel){
    val ventas by promedioventasViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del producto:", venta.mes, R.drawable.nombreproducto),
                Triple("Promedio de ventas:", venta.promedioVentas.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Promedio de ventass diarias")
        }
    }
}

@Composable
fun TabularIngresosMensualesList(ingresosmensualesViewModel: ingresosmensualesViewModel){
    val ventas by ingresosmensualesViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Mes:", venta.mes, R.drawable.mes),
                Triple("Ingresos totales:", venta.ingresos.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Ingresos totales mensuales")
        }
    }
}

@Composable
fun TabularVentasSemanalesList(ventasSemanalesViewModel: ventasSemanalesViewModel){
    val ventas by ventasSemanalesViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Mes:", venta.mes, R.drawable.mes),
                Triple("Año:", venta.anio.toString(), R.drawable.anio),
                Triple("Semana del mes:", venta.semanames.toString(), R.drawable.semana),
                Triple("Venta total:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Ventas semanales")
        }
    }
}

@Composable
fun TabularPMenosVendidosList(pmenosvendidosViewModel: PMenosVendidosViewModel){
    val ventas by pmenosvendidosViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del producto:", venta.nombreDelProducto, R.drawable.pan),
                Triple("Cantidad de ventas:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Productos menos vendidos")
        }
    }
}

@Composable
fun TabularProductosFinSemanaList(productosfinSemanaViewModel: ProductosfinSemanaViewModel){
    val ventas by productosfinSemanaViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del producto:", venta.nombreDelProducto, R.drawable.pan),
                Triple("Cantidad de ventas:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Productos vendidos los fines de semana")
        }
    }
}

@Composable
fun TabularProductosPorProveedorList(productosPorProveedorViewModel: ProductoPorProveedorViewModel){
    val ventas by productosPorProveedorViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del producto:", venta.nombreDelProducto, R.drawable.pan),
                Triple("Nombre del proveedor:", venta.nombreDelProveedor, R.drawable.proveedor),
                Triple("Cantidad de ventas:", venta.ventaTotal.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Venta de Productos por proveedor")
        }
    }
}

@Composable
fun TabularTopIngresosClientesList(topIngresosClientesViewModel: TopIngresosClientesViewModel){
    val ventas by topIngresosClientesViewModel.ventas.collectAsState()

    LazyColumn {
        items(ventas) { venta ->
            val data = listOf(
                Triple("Nombre del cliente:", venta.nombreDelCliente, R.drawable.proveedor),
                Triple("Ingresos:", venta.ingresos.toString(), R.drawable.cantproducto)
            )
            BimboTabularCard(data, "Top de ingresos por cliente")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BimboTabularScreen(ventasproductofechaViewModel: ventasproductofechaViewModel) {
    TabularVentasList(ventasproductofechaViewModel = ventasproductofechaViewModel)
}

@Composable
fun BimboTabularScreenClientes(ventasclientesViewModel: ventasclientesViewModel) {
    TabularVentasClientesList(ventasclientesViewModel = ventasclientesViewModel)
}

@Composable
fun BimboTabularScreenProductos(productoCategoriaViewModel: productoCategoriaViewModel) {
    TabularVentasProductosList(productoCategoriaViewModel = productoCategoriaViewModel)
}
@Composable
fun BimboTabularScreenPromedio(promedioventasViewModel: promedioventasViewModel) {
    TabularPromedioVentasList(promedioventasViewModel = promedioventasViewModel)
}
@Composable
fun BimboTabularScreenIngresos(ingresosmensualesViewModel: ingresosmensualesViewModel) {
    TabularIngresosMensualesList(ingresosmensualesViewModel = ingresosmensualesViewModel)
}
@Composable
fun BimboTabularScreenSemanales(ventasSemanalesViewModel: ventasSemanalesViewModel) {
    TabularVentasSemanalesList(ventasSemanalesViewModel = ventasSemanalesViewModel)
}
@Composable
fun BimboTabularScreenPMenos(pmenosvendidosViewModel: PMenosVendidosViewModel) {
    TabularPMenosVendidosList(pmenosvendidosViewModel = pmenosvendidosViewModel)
}
@Composable
fun BimboTabularScreenProductosFinSemana(productosfinSemanaViewModel: ProductosfinSemanaViewModel) {
    TabularProductosFinSemanaList(productosfinSemanaViewModel = productosfinSemanaViewModel)
}

@Composable
fun BimboTabularScreenProductosPorProveedor(productosPorProveedorViewModel: ProductoPorProveedorViewModel) {
    TabularProductosPorProveedorList(productosPorProveedorViewModel = productosPorProveedorViewModel)
}

@Composable
fun BimboTabularScreenTopIngresosClientes(topIngresosClientesViewModel: TopIngresosClientesViewModel) {
    TabularTopIngresosClientesList(topIngresosClientesViewModel = topIngresosClientesViewModel)
}