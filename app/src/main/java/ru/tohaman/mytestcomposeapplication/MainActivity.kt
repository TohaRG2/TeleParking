package ru.tohaman.mytestcomposeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.tohaman.mytestcomposeapplication.presentation.navgraph.NavGraph
import ru.tohaman.mytestcomposeapplication.presentation.navgraph.Route
import ru.tohaman.mytestcomposeapplication.ui.theme.MyTestComposeApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTestComposeApplicationTheme {
                NavGraph(Route.HomeScreen.route)
            }
        }
    }
}



