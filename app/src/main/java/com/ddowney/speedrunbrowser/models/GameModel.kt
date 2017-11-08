package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 31/10/2017.
 */
data class GameModel(val id : String,val names : GameNames,
                     val abbreviation : String, val weblink : String,
                     var platforms : List<String>?) {

    data class GameNames(val international : String, val japanese : String, val twitch : String)


}