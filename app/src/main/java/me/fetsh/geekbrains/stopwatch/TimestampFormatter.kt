package me.fetsh.geekbrains.stopwatch

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class TimestampFormatter {

    fun format(timestamp: Duration): String {
        timestamp.toComponents {
            hours, minutes, seconds, _ ->
            val millisecondsFormatted = (timestamp.inWholeMilliseconds % 1000).toInt().pad(3)
            return "${hours.pad(2)}:${minutes.pad(2)}:${seconds.pad(2)}.$millisecondsFormatted"
        }
    }
    private fun Int.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}