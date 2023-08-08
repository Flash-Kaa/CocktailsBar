package com.flasshka.cocktailsbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

class CocktailListDrawer {


    @Composable
    fun Draw() {
        DrawEmptyList(
            margin = 40.dp
        )
    }

    @Composable
    private fun DrawEmptyList(
        margin: Dp = 0.dp,
        arrowSize: Dp = 70.dp,
        modifier: Modifier = Modifier
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxSize()
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

        Image(
            painter = painterResource(id = R.drawable.add_btn),
            contentDescription = "add_btn",
            modifier = modifier
                .size(imageSize)
                .clickable {
                    TODO()
                }
        )
    }
}