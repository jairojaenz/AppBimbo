import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbimbo.R
import kotlin.math.PI
import kotlin.math.sin

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

@Composable
fun BimboCommentCard(comment: Comment) {
    Card(
        modifier = Modifier
            .height(300.dp) // Adjust the height of the card
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
                        text = "Comentarios",
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
              //  Spacer(modifier = Modifier.height(16.dp))
                CommentInfo("postId", comment.postId.toString())
                CommentInfo("id", comment.id.toString())
                CommentInfo("name", comment.name)
                CommentInfo("email", comment.email)
                CommentInfo("body", comment.body)
            }
        }
    }
}

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

@Composable
fun CommentInfo(label: String, value: String) {
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
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun BimboCommentCardPreview() {
    val sampleComment = Comment(
        postId = 1,
        id = 1,
        name = "John Doe",
        email = "john@example.com",
        body = "This is a sample comment body."
    )
    BimboCommentCard(comment = sampleComment)
}