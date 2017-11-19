package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 18/11/2017.
 */
data class CategoriesModel(val id : String, val name : String, val weblink : String,
                           val type : String, val rules : String, val players : CategoryPlayers) {
    data class CategoryPlayers(val type : String, val value : Int)
}