package ddowney.com.speedrunbrowser.Models

data class LeaderboardModel(val weblink : String,
                            val game : String,
                            val runs : List<LeaderboardRunModel>)

data class LeaderboardRunModel(val place : String,
                               val run : RunModel)