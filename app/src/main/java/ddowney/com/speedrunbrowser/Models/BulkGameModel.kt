package ddowney.com.speedrunbrowser.Models

data class BulkGameModel(val id : String,
                         val names : GameNames,
                         val abbreviation : String,
                         val weblink : String)

data class GameNames(val international : String,
                     val japanese : String,
                     val twitch : String)