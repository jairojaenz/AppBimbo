package com.example.appbimbo

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CMI : Screen("cmi")
    object MONGOOPCIONES : Screen("mongoopciones")
    object TABULAR : Screen("tabular")
    object CUBEOLAP : Screen("cubeolap")
    object CONSULTA1 : Screen("consulta1")
    object CONSULTA2 : Screen("consulta2")
    object CONSULTA3 : Screen("consulta3")
    object CONSULTA4 : Screen("consulta4")
    object CONSULTA5 : Screen("consulta5")
    object CONSULTA6 : Screen("consulta6")
    object CONSULTA7 : Screen("consulta7")
    object CONSULTA8 : Screen("consulta8")


    object TABULARCONSULTA1 : Screen("tabularconsulta1")
}