import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.google.gson.annotations.SerializedName
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val URLTABULARAPI = "http://192.168.0.19:5004/"

// aqui se crean las data class para el manejo de la informacion de las tarjetas
data class VentaProducto(
    @SerializedName("Mes") val mes: String,
    @SerializedName("Nombre del producto") val nombreDelProducto: String,
    @SerializedName("Venta total") val ventaTotal: Double,
    val iconRes: Int
)
// Data model
data class Album(
    val userId: Int,
    val id: Int,
    val title: String
)


interface TabularApi {
    @GET("ventas")
    suspend fun getVentas(): List<VentaProducto>
}

// API Service
interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}

// aqui se crean los viewmodels para el manejo de la informacion de las tarjetas
class ventasproductofechaViewModel: ViewModel() {
    private val _ventas = MutableStateFlow<List<VentaProducto>>(emptyList())
    val ventas : StateFlow<List<VentaProducto>> = _ventas

    private val retrofit = Retrofit.Builder()
        .baseUrl("$URLTABULARAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val VentasApiService = retrofit.create(TabularApi::class.java)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BimboTabularScreen(ventasproductofechaViewModel: ventasproductofechaViewModel) {
    TabularVentasList(ventasproductofechaViewModel = ventasproductofechaViewModel)
}