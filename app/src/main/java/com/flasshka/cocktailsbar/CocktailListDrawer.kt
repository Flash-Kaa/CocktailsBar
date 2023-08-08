package com.flasshka.cocktailsbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

object CocktailListDrawer {
    private val drawDescriptionOfCocktail: MutableState<Cocktail?> = mutableStateOf(null)
    var bundle: Bundle = Bundle()

    @Composable
    fun Draw() {
        if(CocktailsList.Count() == 0) {
            DrawEmptyList(
                modifier = Modifier.fillMaxSize(),
                margin = 20.dp
            )
        }
        else if(drawDescriptionOfCocktail.value != null) {
            DrawCocktail(
                cocktail = drawDescriptionOfCocktail.value!!,
                modifier = Modifier.fillMaxSize(),
                margin = 20.dp
            )
        }
        else {
            DrawCocktailCells()
        }
    }

    @Composable
    private fun DrawCocktailCells(
        titleTextSize: TextUnit = 40.sp,
        cocktailsTitleTextSize: TextUnit = 25.sp
    ) {
        Column {
            WriteText(text = "My cocktails", fontSize = titleTextSize)

            val cocktails = CocktailsList.GetList()
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(items = cocktails) {
                    DrawCocktailCell(it, cocktailsTitleTextSize)
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            DrawAddButton(
                imageSize = 100.dp
            )
        }
    }

    @Composable
    private fun DrawCocktailCell(
        cocktail: Cocktail,
        cocktailsTitleTextSize: TextUnit,
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable {
                    drawDescriptionOfCocktail.value = cocktail
                }

        ) {
            Image(painter = painterResource(id = R.drawable.cocktail_image), contentDescription = "cocktail")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                WriteText(text = cocktail.Title, fontSize = cocktailsTitleTextSize)
            }
        }
    }

    @Composable
    private fun DrawCocktail(
        cocktail: Cocktail,
        titleTextSize: TextUnit = 40.sp,
        descriptionTextSize: TextUnit = 20.sp,
        fieldTextSize: TextUnit = 15.sp,
        margin: Dp = 0.dp,
        backSize: Dp = 50.dp,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current

        ConstraintLayout(
            modifier = modifier
        ) {
            val (column, back) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
                modifier = Modifier
                    .padding(margin)
                    .size(backSize)
                    .clickable {
                        drawDescriptionOfCocktail.value = null
                    }
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(column) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom, 15.dp)
                    }
                    .verticalScroll(ScrollState(0))
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.White)
            ) {
                WriteText(text = cocktail.Title, fontSize = titleTextSize, margin)
                WriteText(text = cocktail.Description, fontSize = descriptionTextSize, margin)

                cocktail.Ingredients.forEach {
                    WriteText(text = it, fontSize = descriptionTextSize, margin)
                }

                WriteText(text = "Recipe:", fontSize = fieldTextSize, 0.dp)
                WriteText(text = cocktail.Recipe, fontSize = descriptionTextSize, margin)

                Button(
                    onClick = {
                        SendCocktailsToEdit(cocktail, context)
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    WriteText(text = "Edit", fontSize = 30.sp)
                }
            }
        }
    }

    private fun SendCocktailsToEdit(
        cocktail: Cocktail,
        context: Context
    ) {
        val intent = Intent(context, CreatingCocktail::class.java)
        bundle.putString(BundleKeys.Title.toString(), cocktail.Title)
        bundle.putString(BundleKeys.Description.toString(), cocktail.Description)
        bundle.putString(BundleKeys.Recipe.toString(), cocktail.Recipe)
        bundle.putStringArray(BundleKeys.Ingredients.toString(), cocktail.Ingredients)

        intent.putExtras(bundle)

        CocktailsList.Remove(cocktail)
        context.startActivity(intent)
    }

    @Composable
    private fun WriteText(
        text: String,
        fontSize: TextUnit,
        spaceSize: Dp = 0.dp
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier.fillMaxWidth(0.9f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spaceSize))
    }

    @Composable
    private fun DrawEmptyList(
        modifier: Modifier = Modifier,
        margin: Dp = 0.dp,
        arrowSize: Dp = 70.dp
    ) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (image, text1, text2, arrow, addBtn) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.cocktail_image),
                contentDescription = "zero_cocktails",
                modifier = Modifier
                    .size(300.dp)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(text1.top)
                    }
            )

            Text(
                text = "My Cocktails",
                fontSize = 30.sp,
                modifier = Modifier.constrainAs(text1) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(text2.top, margin)
                }
            )

            Text(
                text = "Add your first cocktail here",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(text2) {
                    bottom.linkTo(arrow.top, margin)
                    centerHorizontallyTo(parent)
                }
            )
            
            Image(
                painter = painterResource(id = R.drawable.arrow_image),
                contentDescription = "arrow",
                modifier = Modifier
                    .size(arrowSize)
                    .constrainAs(arrow) {
                        bottom.linkTo(addBtn.top, margin)
                        centerHorizontallyTo(parent)
                    }
            )


            DrawAddButton(
                imageSize = 100.dp,
                modifier = Modifier.constrainAs(addBtn) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }
            )
        }
    }

    @Composable
    private fun DrawAddButton(
        imageSize: Dp,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.add_btn),
            contentDescription = "add_btn",
            modifier = modifier
                .size(imageSize)
                .clickable {
                    context.startActivity(Intent(context, CreatingCocktail::class.java))
                }
        )
    }
}