package ayush.ggv.groovebox

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import ayush.ggv.groovebox.ui.theme.GrooveBoxTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    val pagerState = rememberPagerState(pageCount = { musics.count()})
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
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        val configuration = LocalConfiguration.current

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HorizontalPager(
                                modifier = Modifier.fillMaxWidth(),
                                state = pagerState,
                                pageSize = PageSize.Fixed((configuration.screenWidthDp/(1.7)).dp),
                                contentPadding = PaddingValues(horizontal = 85.dp)
                            ) {page->
                                Card(
                                    modifier = Modifier
                                        .size((configuration.screenWidthDp /(1.7)).dp)
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
                                  shape  = CircleShape
                                ) {
                                    Image(
                                        painter = painterResource(id = musics[page].cover),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }


                            }
                        }


                    }
                }
            }
        }
    }
}

data class Music(
    val name: String,
    val music: Int,
    val cover: Int
)

