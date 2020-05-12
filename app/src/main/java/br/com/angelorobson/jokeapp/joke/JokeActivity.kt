package br.com.angelorobson.jokeapp.joke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import br.com.angelorobson.jokeapp.R
import br.com.angelorobson.jokeapp.di
import kotlinx.android.synthetic.main.activity_main.*

class JokeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokeViewModel = di.jokeViewModel
        jokeViewModel.observableState.observe(this, Observer {
            renderState(it)
        })

        btn_joke.setOnClickListener {
            jokeViewModel.dispatch(JokeAction.GetJokeButtonClicked)
        }
    }

    private fun renderState(state: JokeState) {
        with(state) {
            if (joke.isNotEmpty()) {
                tv_joke.text = joke
            }

            progress_horizontal.isVisible = loading
            btn_joke.isEnabled = loading.not()
            tv_error.isVisible = displayError
        }
    }
}
