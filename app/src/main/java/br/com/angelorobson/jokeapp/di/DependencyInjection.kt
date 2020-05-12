package br.com.angelorobson.jokeapp.di

import br.com.angelorobson.jokeapp.joke.*
import br.com.angelorobson.jokeapp.model.api.JokeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ww.roxie.BaseViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {

    val jokeViewModel: BaseViewModel<JokeAction, JokeState>

}

class DependencyInjectionImplementation() : DependencyInjection {

    override val jokeViewModel: BaseViewModel<JokeAction, JokeState>

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://sv443.net/jokeapi/v2/joke/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val catService = retrofit.create(JokeService::class.java)

        val jokeRepository = JokeRepository(catService)
        val JokeUseCase = JokeUseCaseImplementation(jokeRepository)
        jokeViewModel = JokeViewModel(JokeUseCase)
    }

}