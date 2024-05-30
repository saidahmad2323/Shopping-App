package com.example.signalapp.UIScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.signalapp.room.Book
import com.example.signalapp.Components.ShimmerEffect
import com.example.signalapp.Components.TabbedPager
import com.example.signalapp.R
import com.example.signalapp.room.Books
import com.example.signalapp.room.ViewModels
import com.example.signalapp.ui.theme.Cyan
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting(
    navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    viewModels: ViewModels,
) {
    var searchstate by remember {
        mutableStateOf(false)
    }
    var search by remember { mutableStateOf("") }
    val fs = Firebase.firestore
    val focusRequester = remember { FocusRequester() }
    val list = remember { mutableStateOf(emptyList<Book>()) }
    val context = LocalContext.current
    val selectTabIndex = remember { mutableStateOf(0) }
    val image = listOf(
        R.drawable.kross,
        R.drawable.kross,
        R.drawable.kross,
    )
    val lazyListState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }
    LaunchedEffect(searchstate) {
        if (searchstate) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentIndex = (currentIndex + 1) % image.size
            coroutineScope.launch {
                lazyListState.animateScrollToItem(currentIndex)
            }
        }
    }
    LaunchedEffect(Unit) {
        fs.collection("books").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            val books = snapshot?.toObjects(Book::class.java)?.sortedByDescending { it.id }
            list.value = books ?: emptyList()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), shape = RoundedCornerShape(30.dp)
        ) {
            Box(
                modifier = Modifier.padding(start = 20.dp), contentAlignment = Alignment.CenterStart
            ) {
                if (search.isEmpty()) {
                    Text(text = "Найдено ${list.value.count()} объявление")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = search, onValueChange = { newText ->
                            search = newText.capitalize()
                        }, modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester)
                    )
                    IconButton(onClick = {
                        searchstate = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "search"
                        )
                    }
                }
            }
        }
        TabbedPager(titles = listOf("Все", "Бокс", "Тренажер"),
            selectedTabIndex = selectTabIndex.value,
            onTabSelected = { selectTabIndex.value = it }) { tabIndex ->
            val filteredList = when (tabIndex) {
                0 -> list.value
                1 -> list.value.filter { it.category == "Бокс" }
                2 -> list.value.filter { it.category == "Тренажер" }
                else -> emptyList()
            }
            val sortedFiltered = filteredList.sortedByDescending { it.id }
            val filtred = sortedFiltered.filter {
                it.name.contains(search, ignoreCase = true)
            }
            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Категория Пустая")
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(filtred) { book ->
                        val isFavorite = remember { mutableStateOf(false) }
                        LaunchedEffect(book.id) {
                            coroutineScope.launch {
                                isFavorite.value = viewModels.isBookFavorite(book.id)
                            }
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    val nameEncoded = URLEncoder.encode(book.name, "UTF-8")
                                    val descriptionEncoded =
                                        URLEncoder.encode(book.description, "UTF-8")
                                    val imageUriEncoded = URLEncoder.encode(book.imageUri, "UTF-8")
                                    navHostController.navigate(Screens.DeteilScreen.route + "$nameEncoded/$descriptionEncoded/$imageUriEncoded")
                                },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {
                            Column {
                                Box {
                                    SubcomposeAsyncImage(
                                        model = book.imageUri,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                        loading = { ShimmerEffect() },
                                    )
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                if (isFavorite.value) {
                                                    viewModels.delete(
                                                        Books(
                                                            id = book.id,
                                                            name = book.name,
                                                            price = book.price,
                                                            hits = book.hits,
                                                            description = book.description,
                                                            imageUri = book.imageUri,
                                                            acsi = book.acsi
                                                        )
                                                    )
                                                } else {
                                                    viewModels.addNote(
                                                        Books(
                                                            id = book.id,
                                                            name = book.name,
                                                            price = book.price,
                                                            hits = book.hits,
                                                            description = book.description,
                                                            imageUri = book.imageUri,
                                                            acsi = book.acsi
                                                        )
                                                    )
                                                }
                                                isFavorite.value = !isFavorite.value
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .background(color = Cyan, shape = CircleShape)
                                            .align(Alignment.TopEnd)
                                            .size(35.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = if (isFavorite.value) "Удалить из избранного" else "Добавить в избранное",
                                            tint = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = book.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${book.price} смн",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = book.hits,
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}