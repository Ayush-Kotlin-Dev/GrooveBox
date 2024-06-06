package ayush.ggv.groovebox

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.compose.rememberNavController
import ayush.ggv.groovebox.destinations.HomeScreenDestination
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.utils.currentDestinationAsState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MusicApp(
    player: ExoPlayer
) {

    val navHostController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()

    val isSystemInDark = isSystemInDarkTheme()
    val statusBarColor = if (isSystemInDark) {
        MaterialTheme.colors.surface
    } else {
        MaterialTheme.colors.surface.copy(alpha = 0.95f)
    }
    val context = LocalContext.current

    val navigationBarItems = remember { NavigationBarItems.entries.toTypedArray() }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val currentDestination by navHostController.currentDestinationAsState()

//    if (currentDestination?.route != null && currentDestination?.route != ProfileDestination.route) {
//        selectedIndex = getNavigationBarIndex(currentDestination?.route)
//    }


    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
//                if (currentDestination?.route in listOf(
//
//
//                    )
//                ) {
                    Box(modifier = Modifier.padding(top = 10.dp)) {
                        AnimatedNavigationBar(
                            modifier = Modifier.height(64.dp),
                            selectedIndex = selectedIndex,
                            cornerRadius = shapeCornerRadius(34.dp),
                            ballAnimation = Parabolic(tween(300)),
                            indentAnimation = Height(tween(300)),
                            ballColor = MaterialTheme.colors.primary,
                            barColor = MaterialTheme.colors.surface,
                        ) {
                            navigationBarItems.forEachIndexed { index, item ->
                                Box(
                                    modifier = Modifier
                                        .noRippleClickable {
                                            selectedIndex = item.ordinal
                                            when (item) {
                                                NavigationBarItems.HOME -> navHostController.navigate(HomeScreenDestination(
                                                    player
                                                ).route)

                                                NavigationBarItems.SEARCH -> Toast
                                                    .makeText(
                                                        context,
                                                        "Clicked Search",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()

                                                NavigationBarItems.PROFILE -> Toast
                                                    .makeText(
                                                        context,
                                                        "Clicked Profile",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                        }
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = item.icons,
                                        contentDescription = null,
                                        tint = if (selectedIndex == index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
                                            alpha = 0.6f
                                        )
                                    )
                                }
                            }
                        }

                    }
                //}
            }
        ){
            innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

            }
        }
    }
}



enum class NavigationBarItems(val icons: ImageVector) {
    HOME(Icons.Filled.Home),
    SEARCH(Icons.TwoTone.Search),
    PROFILE(Icons.Filled.Person)
}
