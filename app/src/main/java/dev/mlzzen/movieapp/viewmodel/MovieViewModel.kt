package dev.mlzzen.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mlzzen.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import kotlinx.coroutines.delay

class MovieViewModel : ViewModel() {
    private val _state = MutableStateFlow(MovieState())
    val state: StateFlow<MovieState> = _state.asStateFlow()

    fun handleIntent(intent: MovieIntent) {
        when (intent) {
            is MovieIntent.LoadMovies -> loadMovies()
            is MovieIntent.SelectMovie -> selectMovie(intent.movie)
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val movies = fetchMoviesFromApi()
                _state.update { it.copy(movies = movies, isLoading = false) }
            } catch (e: Exception) {
                // 处理错误情况
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private suspend fun fetchMoviesFromApi(): List<Movie> {
        // 模拟网络延迟
        delay(2000)
        // 模拟从API获取数据
        return listOf(
            Movie(1, "电影1", "导演1"),
            Movie(2, "电影2", "导演2"),
            Movie(3, "电影3", "导演3")
        )
    }

    private fun selectMovie(movie: Movie) {
        _state.update { it.copy(selectedMovie = movie) }
    }
}

data class MovieState(
    val movies: List<Movie> = emptyList(),
    val selectedMovie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class MovieIntent {
    object LoadMovies : MovieIntent()
    data class SelectMovie(val movie: Movie) : MovieIntent()
}