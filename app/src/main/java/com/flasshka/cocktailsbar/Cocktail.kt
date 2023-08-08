package com.flasshka.cocktailsbar

import android.media.Image

data class Cocktail(
    private var title: String,
    private var description: String,
    private var recipe: String,
    private var ingredients: Array<String>,
    private var image: Image? = null
) {
    var Title: String
        get() = title
        private set(value) {
            title = value
        }

    var Description: String
        get() = description
        private set(value) {
            description = value
        }

    var Recipe: String
        get() = recipe
        private set(value) {
            recipe = value
        }

    var Ingredients: Array<String>
        get() = ingredients
        private set(value) {
            ingredients = value
        }

    var Image: Image?
        get() = image
        private set(value) {
            image = value
        }

    fun Edit(
        title: String,
        description: String,
        recipe: String,
        ingredients: Array<String>,
        image: Image? = null
    ) {
        this.title = title
        this.description = description
        this.recipe = recipe
        this.ingredients = ingredients
        this.image = image
    }

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        return title == (other as Cocktail).title
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}