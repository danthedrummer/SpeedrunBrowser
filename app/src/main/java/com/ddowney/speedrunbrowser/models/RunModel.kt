package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 01/11/2017.
 */
data class RunModel(val id : String, val game : String, val players : List<PlayerModel>, val times : TimeModel) {
    data class PlayerModel(val rel : String, val id : String, val name : String)
    data class TimeModel(val primary : String)
}