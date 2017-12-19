package com.ddowney.speedrunbrowser.models

import java.io.Serializable
import java.net.URI

/**
 * Created by Dan on 01/11/2017.
 */
data class RunModel(val id : String, val game : String, val players : List<PlayerModel>,
                    val times : TimeModel, val videos : VideosModel, val comment : String?) : Serializable {
    data class PlayerModel(val rel : String, val id : String, val name : String, val uri : String) : Serializable
    data class TimeModel(val primary : String, val primary_t : Float) : Serializable
    data class VideosModel(val text: String, val links : List<LinksModel>) : Serializable
    data class LinksModel(val uri : URI) : Serializable
}