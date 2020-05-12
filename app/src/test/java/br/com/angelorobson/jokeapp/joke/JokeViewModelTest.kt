package br.com.angelorobson.jokeapp.joke

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.angelorobson.jokeapp.RxTestSchedulerRule
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class JokeViewModelTest {

    @get:Rule
    val instantExecutableRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTestSchedulerRule = RxTestSchedulerRule()

    val jokeUseCase = mock<JokeUseCase>()
    val observer = mock<Observer<JokeState>>()

    lateinit var testSubject: JokeViewModel

    val loadingState = JokeState(loading = true)
    val initialState = JokeState()

    @Before
    fun setup() {
        testSubject = JokeViewModel(jokeUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given joke successfully loaded, when action getJoke is received, then State contains joke`() {
        // GIVEN
        val joke = "Joke loaded"
        val successfulState = JokeState(loading = false, joke = joke)

        whenever(jokeUseCase.getJoke()).thenReturn(Single.just(joke))

        // WHEN
        testSubject.dispatch(JokeAction.GetJokeButtonClicked)
        testRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState) // initial state
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successfulState)
        }

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given joke failed load, when action getJoke received, then State contains Error`() {
        // GIVEN
        val errorState = JokeState(displayError = true)
        whenever(jokeUseCase.getJoke()).thenReturn(Single.error(RuntimeException()))

        // WHEN
        testSubject.dispatch(JokeAction.GetJokeButtonClicked)
        testRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }
    }

}