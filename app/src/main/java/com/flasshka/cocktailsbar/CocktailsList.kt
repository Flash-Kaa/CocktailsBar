package com.flasshka.cocktailsbar

object CocktailsList {

    private val cocktails = mutableListOf<Cocktail>()

    fun Count() = cocktails.size

    fun GetList() = cocktails.map { it.copy() }

    fun PutCocktail(cocktail: Cocktail) {
        cocktails.add(cocktail)
    }
}