package com.ddowney.speedrunbrowser.utils

/**
 * Created by Dan on 22/11/2017.
 */
class FormattingTools {

    /**
     * Takes a time in seconds and returns it in a readable format
     * e.g. 1 hour 14 minutes 40 seconds
     *
     * @param seconds The number of seconds
     * @return The readable time
     */
    fun getReadableTime(seconds : Int) : String {
        val str = StringBuilder()
        var tmpSeconds = seconds
        var amount : Int

        if (tmpSeconds / 3600 != 0) {
            amount = tmpSeconds / 3600
            when (amount) {
                1 -> { str.append(" $amount hour") }
                else -> { str.append(" $amount hours") }
            }
            tmpSeconds %= 3600
        }

        if (tmpSeconds / 60 != 0) {
            amount = tmpSeconds / 60
            when (amount) {
                1 -> { str.append(" $amount minute") }
                else -> { str.append(" $amount minutes") }
            }
            tmpSeconds %= 60
        }

        when (tmpSeconds) {
            0 -> { }
            1 -> { str.append(" $tmpSeconds second") }
            else -> { str.append(" $tmpSeconds seconds") }
        }

        return str.toString()
    }
}