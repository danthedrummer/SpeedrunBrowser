package com.ddowney.speedrunbrowser.utils

/**
 * Created by Dan on 22/11/2017.
 */
class FormattingTools {

    /**
     * Takes a time in seconds and returns it in a readable format
     * e.g. 1:14:40.150 -> H:mm:ss.sss
     *
     * @param seconds The number of seconds
     * @return The readable time
     */
    fun getReadableTime(seconds : Float) : String {
        val str = StringBuilder()
        var tmpSeconds = Math.round(seconds)
        var amount : Int

        val millis : Float = seconds - tmpSeconds

        if (tmpSeconds / 3600 != 0) {
            amount = tmpSeconds / 3600
            str.append("$amount:")
            tmpSeconds %= 3600
        }

        if (tmpSeconds / 60 != 0) {
            amount = tmpSeconds / 60
            when (amount < 10) {
                true -> { str.append("0$amount:") }
                false -> { str.append("$amount:") }
            }
            tmpSeconds %= 60
        }

        when (tmpSeconds < 10) {
            true -> { str.append("0$tmpSeconds") }
            false -> { str.append("$tmpSeconds") }
        }

        if (millis > 0) {
            str.append(".${Math.round(millis * 1000)}")
        }

        return str.toString()
    }
}