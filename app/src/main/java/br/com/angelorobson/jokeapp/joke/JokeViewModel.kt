package br.com.angelorobson.jokeapp.joke

import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class JokeViewModel(private val jokeUseCase: JokeUseCase) : BaseViewModel<JokeAction, JokeState>() {


    override val initialState: JokeState = JokeState()

    private val reducer: Reducer<JokeState, JokeChange> = { state, change ->
        when (change) {
            is JokeChange.Loading -> state.copy(loading = true, displayError = false)
            is JokeChange.JokeLoaded -> state.copy(
                loading = false,
                joke = change.joke,
                displayError = false
            )
            is JokeChange.Error -> state.copy(loading = false, displayError = true)
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val getJokeChange = actions.ofType(JokeAction.GetJokeButtonClicked::class.java)
            .switchMap {
                jokeUseCase.getJoke()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<JokeChange> {
                        JokeChange.JokeLoaded(it)
                    }
                    .onErrorReturn {
                        JokeChange.Error
                    }
                    .startWith(JokeChange.Loading)
            }

        disposables += getJokeChange.scan(initialState, reducer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}
