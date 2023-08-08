package com.flasshka.cocktailsbar

import androidx.compose.runtime.Composable

object CocktailsList {

    private lateinit var cocktails: List<String>

    fun Count() = cocktails.size
}