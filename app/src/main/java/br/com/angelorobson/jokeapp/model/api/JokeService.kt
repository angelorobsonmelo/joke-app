package br.com.angelorobson.jokeapp.model.api

import br.com.angelorobson.jokeapp.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface JokeService {

    @GET("Any?type=single")
    fun getJoke(): Single<Response>
}