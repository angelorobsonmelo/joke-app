package br.com.angelorobson.jokeapp.joke

import br.com.angelorobson.jokeapp.model.api.JokeService
import io.reactivex.Single

class JokeRepository(private val jokeService: JokeService) {

    @Throws(Exception::class)
    fun getJoke(): Single<String> {
        return jokeService.getJoke()
            .map {
                it.joke
            }
    }
}