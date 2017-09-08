package ddowney.com.speedrunbrowser.Models

/**
 * Model class for a Run. A run is a finished attempt to a play a game
 * following that games ruleset.
 *
 * I haven't included everything that gets returned from the API.
 */
data class RunModel (val id : String,
                     val weblink : String,
                     val game : String,
                     val category : String,
                     val videos : VideosModel,
                     val comment : String,
                     val status : StatusModel,
                     val players : List<PlayerModel>,
                     val date : String,
                     val submitted : String,
                     val times : TimesModel,
                     val system : SystemModel)