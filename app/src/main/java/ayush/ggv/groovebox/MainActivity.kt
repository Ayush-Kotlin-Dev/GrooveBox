package ayush.ggv.groovebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ayush.ggv.groovebox.ui.theme.GrooveBoxTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrooveBoxTheme {
                MusicPlayerHomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MusicPlayerHomeScreen() {
    val audioList = listOf("Song 1", "Song 2", "Song 3", "Song 4", "Song 5")
    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.SpaceBetween) {

        AudioItem(audio = "Sajni from Laapataa Ladies", isPlaying =  true )
        Player()
    }
}

@Composable
fun AudioItem(audio: String, isPlaying: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Album Cover",
            modifier = Modifier.size(56.dp)
        )
        Text(
            text = audio,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        IconButton(onClick = { /* Handle play/pause action here */ }) {
            Icon(
                painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play_buttton),
                contentDescription = if (isPlaying) "Pause" else "Play"

            )
        }
    }
}


@Composable
fun Player() {
    var progress by remember { mutableStateOf(0f) }
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Slider(
                value = progress,
                onValueChange = { progress = it },
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Red,
                    inactiveTrackColor = Color.Gray
                )
            )
            IconButton(onClick = { isPlaying = !isPlaying }) {
                Icon(
                    painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play_buttton),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}