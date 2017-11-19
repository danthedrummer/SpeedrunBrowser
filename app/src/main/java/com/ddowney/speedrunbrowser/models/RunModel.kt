package com.ddowney.speedrunbrowser.models

import java.io.Serializable

/**
 * Created by Dan on 01/11/2017.
 */
data class RunModel(val id : String, val game : String, val players : List<PlayerModel>, val times : TimeModel) : Serializable {
    data class PlayerModel(val rel : String, val id : String, val name : String, val uri : String) : Serializable
    data class TimeModel(val primary : String, val primary_t : Int) : Serializable
}