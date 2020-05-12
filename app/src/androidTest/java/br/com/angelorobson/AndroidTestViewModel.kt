package br.com.angelorobson

import br.com.angelorobson.jokeapp.joke.JokeAction
import br.com.angelorobson.jokeapp.joke.JokeState
import com.ww.roxie.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class AndroidTestViewModel : BaseViewModel<JokeAction, JokeState>() {
    override val initialState: JokeState = JokeState()
    val testAction = TestObserver<JokeAction>()
    val testState = PublishSubject.create<JokeState>()

    init {
        actions.subscribe(testAction)
        disposables += testState.observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }

}