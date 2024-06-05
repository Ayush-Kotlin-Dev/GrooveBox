package ayush.ggv.groovebox

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import ayush.ggv.groovebox.notification.MusicService
import ayush.ggv.groovebox.notification.MusicService.Companion.CHANNEL_ID
import ayush.ggv.groovebox.ui.theme.GrooveBoxTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


class MainActivity : ComponentActivity() {
    lateinit var player: ExoPlayer

    @SuppressLint("RestrictedApi", "UnusedContentLambdaTargetStateParameter")
    @OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, MusicService::class.java))
        player = ExoPlayer.Builder(this).build()

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Music Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)


        enableEdgeToEdge()
        setContent {
            GrooveBoxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val uiController = rememberSystemUiController()
                    val colors = listOf(
                        Color(0xFF6200EE),
                        Color(0xFF03DAC6),
                        Color(0xFF018786),
                        Color(0xFF3700B3),
                        Color(0xFF795548),
                        Color(0xFFD32F2F),
                        Color(0xFFC2185B),
                        Color(0xFF512DA8),
                        Color(0xFF303F9F),
                        Color(0xFF1976D2),
                        Color(0xFF0288D1),
                        Color(0xFF0097A7),
                        Color(0xFF00796B),
                        Color(0xFF388E3C),
                        Color(0xFF689F38),
                        Color(0xFFAFB42B),
                        Color(0xFFFFA000),
                        Color(0xFFE64A19),
                        Color(0xFF5D4037),
                        Color(0xFF616161),
                        Color(0xFF455A64),
                        Color(0xFF263238)
                    )
                    val darkColors = listOf(
                        Color(0xFF3700B3),
                        Color(0xFF6200EE),
                        Color(0xFF03DAC6),
                        Color(0xFF018786),
                        Color(0xFF795548),
                        Color(0xFFD32F2F),
                        Color(0xFFC2185B),
                        Color(0xFF512DA8),
                        Color(0xFF303F9F),
                        Color(0xFF1976D2),
                        Color(0xFF0288D1),
                        Color(0xFF0097A7),
                        Color(0xFF00796B),
                        Color(0xFF388E3C),
                        Color(0xFF689F38),
                        Color(0xFFAFB42B),
                        Color(0xFFFFA000),
                        Color(0xFFE64A19),
                        Color(0xFF5D4037),
                        Color(0xFF616161),
                        Color(0xFF455A64),
                        Color(0xFF263238)
                    )

                    val colorIndex = remember { mutableIntStateOf(0) }

                    LaunchedEffect(Unit) {
                        colorIndex.intValue += 1
                    }
                    LaunchedEffect(key1 = colorIndex.value) {
                        delay(2000)
                        if (colorIndex.value < darkColors.lastIndex) {
                            colorIndex.intValue += 1
                        } else {
                            colorIndex.intValue = 0
                        }
                    }
                    val animatedColor by animateColorAsState(
                        targetValue = colors[colorIndex.value],
                        animationSpec = tween(2000)
                    )
                    val animatedDarkColor by animateColorAsState(
                        targetValue = darkColors[colorIndex.value],
                        animationSpec = tween(2000)
                    )

                    uiController.setStatusBarColor(
                        color = animatedColor,
                        darkIcons = false
                    )
                    uiController.setNavigationBarColor(
                        color = animatedDarkColor
                    )

                    val musics = listOf(
                        Music("Music 1", R.raw.music1, R.drawable.music1),
                        Music("Music 2", R.raw.music2, R.drawable.music2),
                        Music("Music 3", R.raw.music33, R.drawable.music3)
                    )
                    val pagerState = rememberPagerState(pageCount = { musics.count() })
                    val playingIndex = remember { mutableIntStateOf(0) }


                    LaunchedEffect(pagerState.currentPage) {
                        playingIndex.value = pagerState.currentPage
                        player.seekTo(pagerState.currentPage , 0)

                    }


                    LaunchedEffect(Unit) {
                        musics.forEach {
                            val path = "android.resource://$packageName/${it.music}"
                            val mediaItem = MediaItem.fromUri(Uri.parse(path))
                            player.addMediaItem(mediaItem)
                        }
                    }
                    player.prepare()

                    val playing = remember {
                        mutableStateOf(false)
                    }
                    val currentPosition = remember {
                        mutableLongStateOf(0)
                    }
                    val totalDuration = remember {
                        mutableLongStateOf(0)
                    }
                    val progressSize = remember {
                        mutableStateOf(
                            IntSize(
                                width = 0,
                                height = 0
                            )
                        )
                    }


                    LaunchedEffect(player.isPlaying) {
                        playing.value = player.isPlaying
                    }

                    LaunchedEffect(
                        player.currentPosition
                    ) {
                        currentPosition.value = player.currentPosition
                    }
                    LaunchedEffect(
                        player.duration
                    ) {
                        if (
                            player.duration > 0
                        ) {
                            totalDuration.value = player.duration
                        }
                    }
                    LaunchedEffect(player.currentMediaItemIndex) {
                        playingIndex.value = player.currentMediaItemIndex
                        pagerState.animateScrollToPage(playingIndex.value , animationSpec = tween(2000))

                    }
                    var percentReached = currentPosition.value.toFloat() / (if(totalDuration.value > 0) totalDuration.value else 0).toFloat()

                    if(percentReached.isNaN()){
                       percentReached = 0f
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        animatedColor,
                                        animatedDarkColor
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val configuration = LocalConfiguration.current

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val textColor by animateColorAsState(
                                targetValue = if (animatedColor.luminance() > .5f) Color(
                                    0xff414141
                                ) else Color.White,
                                animationSpec = tween(2000)
                            )
                            AnimatedContent(targetState = playingIndex.value, transitionSpec = {
                                (scaleIn() + fadeIn()) togetherWith (scaleOut() + fadeOut())
                            }) {
                                Text(text = musics[it].name, fontSize = 52.sp, color = textColor)
                            }
                            Spacer(modifier = Modifier.height(30.dp))

                            HorizontalPager(
                                modifier = Modifier.fillMaxWidth(),
                                state = pagerState,
                                pageSize = PageSize.Fixed((configuration.screenWidthDp / (1.7)).dp),
                                contentPadding = PaddingValues(horizontal = 85.dp)
                            ) { page ->
                                Card(
                                    modifier = Modifier
                                        .size((configuration.screenWidthDp / (1.7)).dp)
                                        .graphicsLayer {
                                            val pageOffset = (
                                                    (pagerState.currentPage - page) +
                                                            pagerState.currentPageOffsetFraction
                                                    ).absoluteValue
                                            val alphaLerp = lerp(
                                                0.4f,
                                                1f,
                                                amount = 1f - pageOffset.coerceIn(0f, 1f)
                                            )
                                            val scaleLerp = lerp(
                                                0.5f,
                                                1f,
                                                amount = 1f - pageOffset.coerceIn(0f, 0f)
                                            )
                                            alpha = alphaLerp
                                            scaleX = scaleLerp
                                            scaleY = scaleLerp

                                        }
                                        .border(
                                            3.dp,
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                        .padding(6.dp),
                                    shape = CircleShape
                                ) {
                                    Image(
                                        painter = painterResource(id = musics[page].cover),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Row(
                                modifier = Modifier.height(50.dp),
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Text(
                                    text =  convertLongToText(currentPosition.value),
                                    modifier = Modifier.width(55.dp),
                                    color = textColor,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                //progress Box
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .height(8.dp)
                                        .padding(horizontal = 10.dp)
                                        .background(Color.White.copy(alpha = 0.5f), CircleShape)
                                        .clip(CircleShape)
                                        .onGloballyPositioned {
                                            progressSize.value = IntSize(it.size.width, it.size.height)
                                        }
                                        .pointerInput(Unit) {
                                            detectHorizontalDragGestures { change, dragAmount ->
                                                change.consume()
                                                val totalWidth = progressSize.value.width.toFloat()
                                                val percent = dragAmount / totalWidth
                                                val seekTo =
                                                    (currentPosition.value + (percent * totalDuration.value)).coerceIn(
                                                        0f,
                                                        totalDuration.value.toFloat()
                                                    ).toLong()
                                                player.seekTo(seekTo)
                                            }
                                        }

                                )
                                {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = if(playing.value) percentReached else 0f)
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF414141)),
                                    )

                                }

                                Text(
                                    text = convertLongToText(totalDuration.value),
                                    modifier = Modifier.width(55.dp),
                                    color = textColor,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp)
                            ) {
                                Control(icon = R.drawable.ic_fast_rewind, size = 60.dp) {
                                    player.seekToPreviousMediaItem()
                                }
                                Control(icon =if( playing.value ) R.drawable.ic_pause else R.drawable.ic_play , size = 80.dp) {
                                    //play music
                                    if (playing.value) {
                                        player.pause()
                                    } else {
                                        player.play()
                                    }
                                }
                                Control(icon = R.drawable.ic_fast_forward, size = 60.dp) {
                                    player.seekToNextMediaItem()
                                }

                            }

                        }
                    }
                }
            }
        }
    }
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // Define your custom back button behavior here
        if (player.isPlaying) {
            player.pause()
        }
        super.onBackPressed()
    }
}

@Composable
fun Control(icon: Int, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size / 2),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xff414141)
        )
    }

}
fun convertLongToText(time: Long): String {
    val seconds = time / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "${if (minutes < 10) "0" else ""}$minutes:${if (remainingSeconds < 10) "0" else ""}$remainingSeconds"
}


data class Music(
    val name: String,
    val music: Int,
    val cover: Int
)