package br.com.angelorobson.jokeapp.joke

import com.ww.roxie.BaseState

data class JokeState(
    val loading: Boolean = false,
    val joke: String = "",
    val displayError: Boolean = false
) : BaseState