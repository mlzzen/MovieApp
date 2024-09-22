package dev.mlzzen.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.mlzzen.movieapp.ui.MovieDetailScreen
import dev.mlzzen.movieapp.ui.MovieListScreen
import dev.mlzzen.movieapp.ui.theme.MovieAppTheme
import dev.mlzzen.movieapp.viewmodel.MovieIntent
import dev.mlzzen.movieapp.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                val state by viewModel.state.collectAsState()

                if (state.selectedMovie == null) {
                    MovieListScreen(
                        state = state,
                        onMovieClick = { movie ->
                            viewModel.handleIntent(MovieIntent.SelectMovie(movie))
                        },
                        onLoadMovies = {
                            viewModel.handleIntent(MovieIntent.LoadMovies)
                        }
                    )
                } else {
                    MovieDetailScreen(movie = state.selectedMovie!!)
                }
            }
        }
    }
}