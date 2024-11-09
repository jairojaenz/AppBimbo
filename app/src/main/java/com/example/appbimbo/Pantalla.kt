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
    object CONSULTA9 : Screen("consulta9")


    object TABULARCONSULTA1 : Screen("tabularconsulta1")
    object TABULARCONSULTA2 : Screen("tabularconsulta2")
    object TABULARCONSULTA3 : Screen("tabularconsulta3")
    object TABULARCONSULTA4 : Screen("tabularconsulta4")
    object TABULARCONSULTA5 : Screen("tabularconsulta5")
    object TABULARCONSULTA6 : Screen("tabularconsulta6")
    object TABULARCONSULTA7 : Screen("tabularconsulta7")
    object TABULARCONSULTA8 : Screen("tabularconsulta8")
}