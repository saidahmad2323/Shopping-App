package com.example.signalapp.UIScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.signalapp.room.ViewModels
import java.net.URLEncoder


@Composable
fun FavoritesScreen(
    viewModels: ViewModels,
    navHostController: NavHostController,
) {
    val notes = viewModels.readNote().observeAsState(listOf()).value

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2), modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        items(notes) { book ->
            AnimatedVisibility(
                visible = true, enter = slideInVertically(
                    initialOffsetY = { 550 }, animationSpec = tween(durationMillis = 600)
                ), exit = slideOutVertically(
                    targetOffsetY = { 550 }, animationSpec = tween(durationMillis = 600)
                )
            ) {
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            val nameEncoded = URLEncoder.encode(book.name, "UTF-8")
                            val descriptionEncoded = URLEncoder.encode(book.description, "UTF-8")
                            val imageUriEncoded = URLEncoder.encode(book.imageUri, "UTF-8")
                            navHostController.navigate(Screens.DeteilScreen.route + "$nameEncoded/$descriptionEncoded/$imageUriEncoded")
                        }, shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = book.hits, color = Color.Red, fontWeight = FontWeight.Bold)
                            Text(
                                text = "-${book.acsi}",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        AsyncImage(model = book.imageUri, contentDescription = null)
                        Text(
                            text = book.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = {
                            viewModels.delete(
                                books = book
                            )
                        }) {

                        }
                    }
                }
            }
        }
    }
}
