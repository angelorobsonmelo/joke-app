package br.com.angelorobson.jokeapp.joke

import io.reactivex.Single

interface JokeUseCase {

    fun getJoke(): Single<String>

}

class JokeUseCaseImplementation(private val jokeRepository: JokeRepository) : JokeUseCase {

    override fun getJoke(): Single<String> = jokeRepository.getJoke()
}