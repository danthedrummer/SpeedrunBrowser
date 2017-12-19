package com.ddowney.speedrunbrowser.models

/**
 * Created by Dan on 19/12/2017.
 */
data class UserModel(val id : String, val names : NamesModel, val weblink : String,
                     val twitch : RunModel.LinksModel?, val youtube : RunModel.LinksModel?,
                     val hitbox : RunModel.LinksModel?, val twitter : RunModel.LinksModel?,
                     val speedrunslive : RunModel.LinksModel?) {
    data class NamesModel(val international : String, val japanese : String?)
}