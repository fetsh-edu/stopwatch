package me.fetsh.geekbrains.stopwatch

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
sealed class StopwatchState {
    abstract fun start(milliseconds: Long): StopwatchState
    abstract fun pause(): StopwatchState
    abstract fun update(milliseconds: Long): StopwatchState
    fun stop(): StopwatchState = INIT
    abstract fun elapsedTime(): Duration

    data class Paused(
        val elapsedTime: Duration
    ) : StopwatchState() {
        override fun start(milliseconds: Long) =
            Running(
                startTime = milliseconds - elapsedTime.inWholeMilliseconds,
                currentTime = milliseconds,
            )
        override fun pause() = this
        override fun update(milliseconds: Long) = this

        override fun elapsedTime(): Duration = elapsedTime
    }

    data class Running(
        val startTime: Long,
        val currentTime: Long
    ) : StopwatchState() {

        override fun start(milliseconds: Long) = this
        override fun pause() = Paused(elapsedTime())
        override fun update(milliseconds: Long) = this.copy(currentTime = milliseconds)

        override fun elapsedTime() = Duration.milliseconds(currentTime - startTime)
    }
    companion object {
        val INIT : StopwatchState = Paused(Duration.ZERO)
    }
}
