package br.com.angelorobson.jokeapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import br.com.angelorobson.TestDependencyInjection
import br.com.angelorobson.jokeapp.joke.JokeAction
import br.com.angelorobson.jokeapp.joke.JokeActivity
import br.com.angelorobson.jokeapp.joke.JokeState
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class JokeActivityTest {

    private val viewModel = TestDependencyInjection.jokeViewModel

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(JokeActivity::class.java)

    val initialState = JokeState()

    @Test
    fun initialState() {
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.tv_joke)).check(matches(withText(R.string.joke_message)))
        assertEquals(viewModel.observableState.value, initialState)
    }

    @Test
    fun getJokeButtonClickedAction() {
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.btn_joke)).perform(click())

        viewModel.testAction.assertValues(JokeAction.GetJokeButtonClicked)
    }

    @Test
    fun jokeLoadedState() {
        val joke = "joke loaded"
        viewModel.testState.onNext(JokeState(joke = joke))

        onView(withId(R.id.progress_horizontal)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_joke)).check(matches(withText(joke)))
        onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_joke)).check(matches(isEnabled()))
    }

    @Test
    fun jokeLoadingState() {
        viewModel.testState.onNext(JokeState(loading = true))

        onView(withId(R.id.progress_horizontal)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_joke)).check(matches(withText(R.string.joke_message)))
        onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_joke)).check(matches(not(isEnabled())))
    }

    @Test
    fun errorState() {
        viewModel.testState.onNext(JokeState(displayError = true))

        onView(withId(R.id.progress_horizontal)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_joke)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_joke)).check(matches(isEnabled()))
    }


}