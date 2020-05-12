package br.com.angelorobson.jokeapp.joke

import com.ww.roxie.BaseAction

sealed class JokeAction: BaseAction {

    object GetJokeButtonClicked: JokeAction()

}