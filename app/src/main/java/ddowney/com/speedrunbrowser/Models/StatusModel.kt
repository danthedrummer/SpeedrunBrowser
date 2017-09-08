package ddowney.com.speedrunbrowser.Models

import com.google.gson.annotations.SerializedName

data class StatusModel(val status : String,
                       val examiner : String,
                       @SerializedName("verify-date") val verifyDate : String)