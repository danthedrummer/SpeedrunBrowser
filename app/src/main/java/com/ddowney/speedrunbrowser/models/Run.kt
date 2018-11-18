package com.ddowney.speedrunbrowser.models

import java.io.Serializable
import java.net.URI

/**
 * Created by Dan on 01/11/2017.
 */
data class Run(
        val id: String,
        val game: String,
        val players: List<Player>,
        val times: Time,
        val videos: Videos,
        val comment: String?
) : Serializable {

    data class Player(
            val rel: String,
            val id: String,
            val name: String,
            val uri: String
    ) : Serializable

    data class Time(
            val primary: String,
            val primary_t: Float
    ) : Serializable

    data class Videos(
            val text: String,
            val links: List<Links>
    ) : Serializable

    data class Links(val uri: URI) : Serializable

}