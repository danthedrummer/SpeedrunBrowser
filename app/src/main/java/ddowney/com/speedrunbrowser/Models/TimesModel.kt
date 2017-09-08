package ddowney.com.speedrunbrowser.Models

import com.google.gson.annotations.SerializedName

data class TimesModel(val primary : String,
                      @SerializedName("primary_t") val primarySeconds : Double,
                      @SerializedName("realtime") val realTime : String,
                      @SerializedName("realtime_t") val realTimeSeconds : Double,
                      @SerializedName("realtime_noloads") val realTimeNoLoads : String,
                      @SerializedName("realtime_noloads_t") val realTimeNoLoadsSeconds : Double,
                      @SerializedName("ingame") val inGame : String,
                      @SerializedName("ingame_t") val inGameSeconds : Double)