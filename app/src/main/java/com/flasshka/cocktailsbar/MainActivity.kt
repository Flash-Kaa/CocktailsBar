package com.flasshka.cocktailsbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailListDrawer.Draw()
        }
    }

    /*override fun onPause() {
        super.onPause()

        val sb = StringBuilder()
        for (i in CocktailsList.GetList()) {
            sb.append()
        }

    }*/

}


