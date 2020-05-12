package br.com.angelorobson.jokeapp

import android.app.Application
import android.content.Context
import br.com.angelorobson.jokeapp.di.DependencyInjection
import br.com.angelorobson.jokeapp.di.DependencyInjectionImplementation

open class App : Application() {

    open val di: DependencyInjection by lazy {
        DependencyInjectionImplementation()
    }
}

val Context.di: DependencyInjection get() = (this.applicationContext as App).di