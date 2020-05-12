package br.com.angelorobson

import br.com.angelorobson.jokeapp.App
import br.com.angelorobson.jokeapp.di.DependencyInjection

class AndroidTestApplication : App() {

    override val di = TestDependencyInjection
}

object TestDependencyInjection : DependencyInjection {
    override val jokeViewModel = AndroidTestViewModel()

}