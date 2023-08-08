package com.flasshka.cocktailsbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class CreatingCocktail : ComponentActivity() {

    private var description: MutableState<String> = mutableStateOf("")
    private var title: MutableState<String> = mutableStateOf("")
    private var recipe: MutableState<String> = mutableStateOf("")
    private var ingredients: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = CocktailListDrawer.bundle
        CocktailListDrawer.bundle = Bundle()

        title.value = bundle.getString(BundleKeys.Title.toString(), title.value)
        description.value = bundle.getString(BundleKeys.Description.toString(), description.value)
        recipe.value = bundle.getString(BundleKeys.Recipe.toString(), recipe.value)
        ingredients = bundle.getStringArray(BundleKeys.Ingredients.toString())?.toMutableList() ?: mutableListOf()
        setContent {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val context = LocalContext.current
                val fontSize = 30.sp

                Creating()

                DrawButton(
                    text = "Save",
                    fontSize = fontSize,
                    contentColor = Color.White,
                    containerColor = Color(65, 144, 255),
                    spaceSize = 5.dp,
                    enabled = title.value.length >= 4
                ) {
                    val cocktail = Cocktail(
                        title = title.value,
                        description = description.value,
                        recipe = recipe.value,
                        ingredients = ingredients.toTypedArray()
                    )
                    Save(cocktail, context)
                }

                DrawButton(
                    text = "Cancel",
                    fontSize = fontSize,
                    contentColor = Color(65, 144, 255),
                    containerColor = Color.White,
                    spaceSize = 20.dp
                ) {
                    Cancel(context)
                }
            }
        }
    }

    @Composable
    private fun DrawButton(
        text: String = "",
        fontSize: TextUnit,
        containerColor: Color,
        contentColor: Color,
        spaceSize: Dp = 0.dp,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            border = BorderStroke(2.dp, contentColor),
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(30),
            enabled = enabled,
            onClick = onClick
        ) {
            Text(text = text, fontSize = fontSize)
        }

        Spacer(modifier = Modifier.size(spaceSize))
    }

    @Composable
    private fun Creating() {

        Spacer(modifier = Modifier.size(200.dp))

        DrawTextField(
            value = title,
            label = "Title",
            placeholder = "Cocktail name",
            height = 100.dp,
            spacerSize = 20.dp,
            isError = title.value.length < 4
        )
        
        
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .horizontalScroll(ScrollState(0))
                .fillMaxWidth(0.9f)
        ) {
            val text = remember {
                mutableStateOf("")
            }

            DrawTextField(
                value = text,
                label = "Ingredients",
                height = 80.dp,
                spacerSize = 20.dp
            )
            
            Image(
                painter = painterResource(id = R.drawable.add_btn), 
                contentDescription = "add_ingredients",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        ingredients.add(text.value)
                        text.value = ""
                    }
            )
            
            for(i in ingredients) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.border(2.dp, Color.LightGray, RoundedCornerShape(30))
                ){
                    Text(text = i, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "delete",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(vertical = 15.dp)
                            .clickable {
                                ingredients.remove(i)
                            }
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        Spacer(modifier = Modifier.size(20.dp))


        DrawTextField(
            value = description,
            label = "Description",
            placeholder = "To make this homemade...",
            height = 200.dp,
            spacerSize = 20.dp
        )

        DrawTextField(
            value = recipe,
            label = "Recipe",
            placeholder = "Simply combine all the ingredients",
            height = 200.dp,
            spacerSize = 20.dp
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DrawTextField(
        value: MutableState<String>,
        label: String = "",
        placeholder: String = "",
        height: Dp = 50.dp,
        spacerSize: Dp = 0.dp,
        isError: Boolean = false
    ){
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            singleLine = false,
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            isError = isError,
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
        )

        Spacer(modifier = Modifier.size(spacerSize))
    }

    private fun Save(
        cocktail: Cocktail,
        context: Context
    ) {
        CocktailsList.PutCocktail(cocktail)
        context.startActivity(Intent(context, MainActivity:: class.java))
    }

    private fun Cancel(
        context: Context
    ) {
        context.startActivity(Intent(context, MainActivity:: class.java))
    }
}