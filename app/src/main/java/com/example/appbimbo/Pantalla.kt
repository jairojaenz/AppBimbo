package com.example.appbimbo

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object EDA : Screen("eda")
    object CMI : Screen("cmi")
    object MONGO : Screen("mongo")
    object CUBEOLAP : Screen("cubeolap")
    object CONSULTA1 : Screen("consulta1")
    object CONSULTA2 : Screen("consulta2")
    object CONSULTA3 : Screen("consulta3")





}