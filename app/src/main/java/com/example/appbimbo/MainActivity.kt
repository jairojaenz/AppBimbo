package com.example.appbimbo

import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appbimbo.ui.theme.AppBimboTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBimboTheme {
                AppBimboApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AppBimboApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                navController = navController,
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Home.route) { HomeScreen(navController) } // Pasa navController
                composable(Screen.EDA.route) { EDAScreen() }
                composable(Screen.CMI.route) { CMIScreen() }
                composable(Screen.MONGO.route) { MONGOScreen() }
                composable(Screen.CUBEOLAP.route) { CUBEOLAPScreen() }
                composable(Screen.CONSULTA1.route) { CONSULTA1Screen() }
                composable(Screen.CONSULTA2.route) { CONSULTA2Screen() }
                composable(Screen.CONSULTA3.route) { CONSULTA3Screen() }// Asegúrate de que esta línea esté presente
            }

        }
    }
}