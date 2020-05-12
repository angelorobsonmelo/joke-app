package br.com.angelorobson.jokeapp.joke

sealed class JokeChange {

    object Loading : JokeChange()
    data class JokeLoaded(val joke: String) : JokeChange()
    object Error : JokeChange()

}