import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.math.PI
import kotlin.math.sin

// aqui se asigna la url de la api
val URLAPI ="http://miapimongodbgrupo2.eastus.azurecontainer.io:5002/"
//val URLAPI ="http://192.168.0.19:5002/"

// aqui se crean las data class para cada consulta
data class Direccion(
    val calle: String,
    val ciudad: String,
    val pais: String
)
data class Cliente(
    val _id: Int,
    val direccion: Direccion,
    val edad: Int,
    val email: String,
    val fecha_registro: String,
    val nombre: String,
    val telefono: String
)
data class DireccionSucursal(
    val calle: String,
    val ciudad: String,
    val codigo_postal: String,
    val pais: String
)
data class HorarioAtencion(
    val dia_semana: String,
    val hora_apertura: String,
    val hora_cierre: String
)
data class Sucursal(
    val _id: Int,
    val direccion: DireccionSucursal,
    val horario_atencion: HorarioAtencion,
    val nombre: String,
    val telefono: String
)
data class Producto(
    val cantidad: Int,
    val nombre_producto: String,
    val producto_id: Int
)
data class Pedido(
    val _id: Int,
    val cliente_id: Int,
    val estado_pedido: String,
    val fecha_pedido: String,
    val nombre_cliente: String,
    val productos: List<Producto>,
    val total_pedido: Double
)
data class Comentarios(
    val nombre_cliente: String,
    val fecha_publicacion: String,
    val contenido: String,
    val tipo_comentario: String,
)
// aqui se crean los servicios para cada data class
// api service for cliente
interface ClienteApiService {
    @GET("clientes")
    suspend fun getClientes(): List<Cliente>

    @GET("clientes/ciudad/{ciudad}")
    suspend fun getClientsByCity(@retrofit2.http.Path("ciudad") ciudad: String): List<Cliente>

    @GET("clientes/edad")
    suspend fun getClientsByAge(): List<Cliente>
}
// api service for sucursal
interface SucursalApiService {
    @GET("sucursales")
    suspend fun getSucursales(): List<Sucursal>
}
// api service for pedido
interface PedidoApiService {
    @GET("pedidos")
    suspend fun getPedidos(): List<Pedido>

    @GET("pedidos/orderedbytotal")
    suspend fun getPedidosOrderedByTotal(): List<Pedido>

    @GET("pedidos/pendientes")
    suspend fun getPedidosPendientes(): List<Pedido>

    @GET("pedidos/confirmados")
    suspend fun getPedidosConfirmados(): List<Pedido>
}
// api service for comentarios
interface ComentariosApiService {
    @GET("comentarios")
    suspend fun getComentarios(): List<Comentarios>
}

// aqui se crean los viewmodels para cada data class
// ViewModel for cliente
class ClienteViewModel : ViewModel() {
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val clienteApiService = retrofit.create(ClienteApiService::class.java)

    init {
        viewModelScope.launch {
            _clientes.value = clienteApiService.getClientes()
        }
    }
}
// ViewModel for sucursal
class SucursalViewModel : ViewModel() {
    private val _sucursales = MutableStateFlow<List<Sucursal>>(emptyList())
    val sucursales: StateFlow<List<Sucursal>> = _sucursales

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val sucursalApiService = retrofit.create(SucursalApiService::class.java)

    init {
        viewModelScope.launch {
            _sucursales.value = sucursalApiService.getSucursales()
        }
    }
}
// ViewModel for cliente by city
class ClienteCytyViewModel : ViewModel() {
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes

    private val _searchResults = MutableStateFlow<List<Cliente>>(emptyList())
    val searchResults: StateFlow<List<Cliente>> = _searchResults

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val clienteApiService = retrofit.create(ClienteApiService::class.java)

    init {
        viewModelScope.launch {
            _loading.value = true
            _clientes.value = clienteApiService.getClientes()
            _loading.value = false
        }
    }

    fun searchClientsByCity(city: String) {
        viewModelScope.launch {
            _loading.value = true
            _searchResults.value = clienteApiService.getClientsByCity(city)
            _loading.value = false
        }
    }
}
// ViewModel for Cliente por edad
class ClienteAgeViewModel : ViewModel() {
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val clienteApiService = retrofit.create(ClienteApiService::class.java)

    init {
        viewModelScope.launch {
            _clientes.value = clienteApiService.getClientsByAge()
        }
    }
}
// ViewModel for Pedido
class PedidoViewModel : ViewModel() {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pedidoApiService = retrofit.create(PedidoApiService::class.java)

    init {
        viewModelScope.launch {
            _loading.value = true
            _pedidos.value = pedidoApiService.getPedidos()
            _loading.value = false
        }
    }
}
// ViewModel for Pedido ordenado por total
class PedidoOrderedByTotalViewModel: ViewModel() {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pedidoApiService = retrofit.create(PedidoApiService::class.java)

    init {
        viewModelScope.launch {
            _loading.value = true
            _pedidos.value = pedidoApiService.getPedidosOrderedByTotal()
            _loading.value = false
        }
    }
}
// ViewModel for Pedido pendientes
class PedidoPendientesViewModel: ViewModel() {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pedidoApiService = retrofit.create(PedidoApiService::class.java)

    init {
        viewModelScope.launch {
            _loading.value = true
            _pedidos.value = pedidoApiService.getPedidosPendientes()
            _loading.value = false
        }
    }
}
//ViewModel for Pedido confirmados
class PedidoConfirmadosViewModel: ViewModel() {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val pedidoApiService = retrofit.create(PedidoApiService::class.java)

    init {
        viewModelScope.launch {
            _loading.value = true
            _pedidos.value = pedidoApiService.getPedidosConfirmados()
            _loading.value = false
        }
    }
}
//ViewModel for comentarios
class ComentariosViewModel: ViewModel() {
    private val _comentarios = MutableStateFlow<List<Comentarios>>(emptyList())
    val comentarios: StateFlow<List<Comentarios>> = _comentarios

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val comentariosApiService = retrofit.create(ComentariosApiService::class.java)

    init {
        viewModelScope.launch {
            _comentarios.value = comentariosApiService.getComentarios()
        }
    }
}

//Elemento composable para realizar las cards de la aplicacion
@Composable
fun BimboMongoDbCard(data: List<Pair<String, String>>, Titulo: String) {
    val cardHeight = (data.size * 80).dp
    Card(
        modifier = Modifier
            .height(cardHeight) // Adjust the height of the card
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            WaveBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$Titulo",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.bimbo),
                        contentDescription = "Bimbo Logo",
                        modifier = Modifier.size(80.dp)
                    )
                }
                LazyColumn {
                    items(data) { item ->
                        Filas(label = item.first, value = item.second)
                    }
                }
            }
        }
    }
}

//Elemento composable para realizar el fondo de la pantalla de cada card
@Composable
fun WaveBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val path = Path()

        val gradient = Brush.horizontalGradient(
            colors = listOf(Color(0xFFFF69B4), Color(0xFF8A2BE2), Color(0xFF0000FF)),
            startX = 0f,
            endX = canvasWidth
        )

        drawRect(brush = gradient)

        for (i in 0..3) {
            val amplitude = canvasHeight / 8f
            val frequency = 2f

            path.reset()
            path.moveTo(0f, canvasHeight)

            for (x in 0..canvasWidth.toInt() step 10) {
                val y = sin(x * frequency / canvasWidth + phase + i * PI / 2) * amplitude + canvasHeight / 2
                path.lineTo(x.toFloat(), y.toFloat())
            }

            path.lineTo(canvasWidth, canvasHeight)
            path.close()

            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.1f)
            )
        }
    }
}
//Elemonto composable para realizar las filas de la lista
@Composable
fun Filas(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
// aqui se crean las listas de las cada consulta
@Composable
fun BimboMongodbClientList(clienteViewModel: ClienteViewModel) {
    val clientes by clienteViewModel.clientes.collectAsState()

    LazyColumn {
        items(clientes) { cliente ->
            val data = listOf(
                "Nombre" to cliente.nombre,
                "Edad" to cliente.edad.toString(),
                "Email" to cliente.email,
                "Telefono" to cliente.telefono,
                "Fecha de registro" to cliente.fecha_registro,
                "Calle" to cliente.direccion.calle,
                "Ciudad" to cliente.direccion.ciudad,
                "Pais" to cliente.direccion.pais
            )
            BimboMongoDbCard(data = data, Titulo = "Información del cliente")
        }
    }
}
@Composable
fun BimboMongodbSucursalesList(sucursalViewModel: SucursalViewModel){
    val sucursales by sucursalViewModel.sucursales.collectAsState()

    LazyColumn {
        items(sucursales) { sucursal ->
            val data = listOf(
                "Nombre" to sucursal.nombre,
                "Telefono" to sucursal.telefono,
                "Calle" to sucursal.direccion.calle,
                "Ciudad" to sucursal.direccion.ciudad,
                "Código Postal" to sucursal.direccion.codigo_postal,
                "Pais" to sucursal.direccion.pais,
                "Dia de la semana" to sucursal.horario_atencion.dia_semana,
                "Hora de apertura" to sucursal.horario_atencion.hora_apertura,
                "Hora de cierre" to sucursal.horario_atencion.hora_cierre
            )
            BimboMongoDbCard(data = data, Titulo = "Información de la sucursal")
        }
    }

}
@Composable
fun SearchClientByCityList(viewModel: ClienteCytyViewModel) {
    val loading by viewModel.loading.collectAsState()
    var city by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Buscar por ciudad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.searchClientsByCity(city) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Buscar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            if (loading) {
                CircularProgressIndicator(
                    color = Color.Green,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(searchResults) { cliente ->
                        val data = listOf(
                            "Nombre" to cliente.nombre,
                            "Edad" to cliente.edad.toString(),
                            "Email" to cliente.email,
                            "Telefono" to cliente.telefono,
                            "Fecha de registro" to cliente.fecha_registro,
                            "Calle" to cliente.direccion.calle,
                            "Ciudad" to cliente.direccion.ciudad,
                            "Pais" to cliente.direccion.pais
                        )
                        BimboMongoDbCard(data = data, Titulo = "Información del cliente")
                    }
                }
            }
        }
    }
}
@Composable
fun BimboMongodbListClientesByAge(clienteAgeViewModel: ClienteAgeViewModel) {
    val clientes by clienteAgeViewModel.clientes.collectAsState()

    LazyColumn {
        items(clientes) { cliente ->
            val data = listOf(
                "Nombre" to cliente.nombre,
                "Edad" to cliente.edad.toString(),
                "Email" to cliente.email,
                "Telefono" to cliente.telefono,
                "Fecha de registro" to cliente.fecha_registro,
                "Calle" to cliente.direccion.calle,
                "Ciudad" to cliente.direccion.ciudad,
                "Pais" to cliente.direccion.pais
            )
            BimboMongoDbCard(data = data, Titulo = "Información del cliente")
        }
    }
}
// Lista de pedidos
@Composable
fun BimboMongodbListPedidos(pedidoViewModel: PedidoViewModel) {
    val loading by pedidoViewModel.loading.collectAsState()
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator(color = Color.Red,
                strokeWidth = 10.dp,
                modifier = Modifier.align(Alignment.Center)
                    )
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    val data = listOf(
                        "Nombre del cliente" to pedido.nombre_cliente,
                        "Estado del pedido" to pedido.estado_pedido,
                        "Fecha del pedido" to pedido.fecha_pedido,
                        "Total del pedido" to pedido.total_pedido.toString(),
                        "Productos" to pedido.productos.joinToString { (it.nombre_producto to it.cantidad.toString()).toString() }
                    )
                    BimboMongoDbCard(data = data, Titulo = "Información del pedido")
                }
            }
        }
    }
}
//Lista de pedidos ordenados por total
@Composable
fun BimboMongodbListPedidosOrderedByTotal(pedidoOrderedByTotalViewModel: PedidoOrderedByTotalViewModel) {
    val loading by pedidoOrderedByTotalViewModel.loading.collectAsState()
    val pedidos by pedidoOrderedByTotalViewModel.pedidos.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator(color = Color.Red,
                strokeWidth = 10.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    val data = listOf(
                        "Nombre del cliente" to pedido.nombre_cliente,
                        "Estado del pedido" to pedido.estado_pedido,
                        "Fecha del pedido" to pedido.fecha_pedido,
                        "Total del pedido" to pedido.total_pedido.toString(),
                        "Productos" to pedido.productos.joinToString { (it.nombre_producto to it.cantidad.toString()).toString() }
                    )
                    BimboMongoDbCard(data = data, Titulo = "Información del pedido")
                }
            }
        }
    }
}
//Lista de pedidos pendientes
@Composable
fun BimboMongodbListPedidosPendientes(pedidoPendientesViewModel: PedidoPendientesViewModel) {
    val loading by pedidoPendientesViewModel.loading.collectAsState()
    val pedidos by pedidoPendientesViewModel.pedidos.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator(color = Color.Red,
                strokeWidth = 10.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    val data = listOf(
                        "Nombre del cliente" to pedido.nombre_cliente,
                        "Estado del pedido" to pedido.estado_pedido,
                        "Fecha del pedido" to pedido.fecha_pedido,
                        "Total del pedido" to pedido.total_pedido.toString(),
                        "Productos" to pedido.productos.joinToString { (it.nombre_producto to it.cantidad.toString()).toString() }
                    )
                    BimboMongoDbCard(data = data, Titulo = "Información del pedido")
                }
            }
        }
    }
}
//Lista de pedidos confirmados
@Composable
fun BimboMongodbListPedidosConfirmados(pedidoConfirmadosViewModel: PedidoConfirmadosViewModel) {
    val loading by pedidoConfirmadosViewModel.loading.collectAsState()
    val pedidos by pedidoConfirmadosViewModel.pedidos.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator(color = Color.Red,
                strokeWidth = 10.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    val data = listOf(
                        "Nombre del cliente" to pedido.nombre_cliente,
                        "Estado del pedido" to pedido.estado_pedido,
                        "Fecha del pedido" to pedido.fecha_pedido,
                        "Total del pedido" to pedido.total_pedido.toString(),
                        "Productos" to pedido.productos.joinToString { (it.nombre_producto to it.cantidad.toString()).toString() }
                    )
                    BimboMongoDbCard(data = data, Titulo = "Información del pedido")
                }
            }
        }
    }
}
//Lista de comentarios
@Composable
fun BimboMongodbListComentarios(comentariosViewModel: ComentariosViewModel) {
    val comentarios by comentariosViewModel.comentarios.collectAsState()

    LazyColumn {
        items(comentarios) { comentario ->
            val data = listOf(
                "Nombre del cliente" to comentario.nombre_cliente,
                "Fecha de publicación" to comentario.fecha_publicacion,
                "Contenido" to comentario.contenido,
                "Tipo de comentario" to comentario.tipo_comentario
            )
            BimboMongoDbCard(data = data, Titulo = "Información del comentario")
        }
    }
}

//aqui se crean las pantallas de la aplicacion
@Composable
fun BimboMongodbScreenClientes(clienteViewModel: ClienteViewModel) {
    BimboMongodbClientList(clienteViewModel = clienteViewModel)
}
@Composable
fun BimboMongodbScreenSucursales(sucursalViewModel: SucursalViewModel) {
    BimboMongodbSucursalesList(sucursalViewModel = sucursalViewModel)
}
@Composable
fun BimboMongodbScreenClientesByCity(clienteCytyViewModel: ClienteCytyViewModel) {
    SearchClientByCityList(viewModel = clienteCytyViewModel)
}
@Composable
fun BimboMongodbScreenClientesByAge(clienteAgeViewModel: ClienteAgeViewModel) {
    BimboMongodbListClientesByAge(clienteAgeViewModel = clienteAgeViewModel)
}
@Composable
fun BimboMongodbScreenPedidos(pedidoViewModel: PedidoViewModel) {
    BimboMongodbListPedidos(pedidoViewModel = pedidoViewModel)
}
@Composable
fun BimboMongodbScreenPedidosOrderedByTotal(pedidoOrderedByTotalViewModel: PedidoOrderedByTotalViewModel) {
    BimboMongodbListPedidosOrderedByTotal(pedidoOrderedByTotalViewModel = pedidoOrderedByTotalViewModel)
}
@Composable
fun BimboMongodbScreenPedidosPendientes(pedidoPendientesViewModel: PedidoPendientesViewModel) {
    BimboMongodbListPedidosPendientes(pedidoPendientesViewModel = pedidoPendientesViewModel)
}
@Composable
fun BimboMongodbScreenPedidosConfirmados(pedidoConfirmadosViewModel: PedidoConfirmadosViewModel) {
    BimboMongodbListPedidosConfirmados(pedidoConfirmadosViewModel = pedidoConfirmadosViewModel)
}
@Composable
fun BimboMongodbScreenComentarios(comentariosViewModel: ComentariosViewModel) {
    BimboMongodbListComentarios(comentariosViewModel = comentariosViewModel)
}


