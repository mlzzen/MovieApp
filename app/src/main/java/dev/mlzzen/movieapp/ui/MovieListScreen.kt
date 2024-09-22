package dev.mlzzen.movieapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mlzzen.movieapp.model.Movie
import dev.mlzzen.movieapp.viewmodel.MovieState

@Composable
fun MovieListScreen(
    state: MovieState,
    onMovieClick: (Movie) -> Unit,
    onLoadMovies: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onLoadMovies()
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(state.movies) { movie ->
                MovieItem(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "导演: ${movie.director}", style = MaterialTheme.typography.bodyMedium)
    }
}