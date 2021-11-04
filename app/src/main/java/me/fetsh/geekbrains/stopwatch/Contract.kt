package me.fetsh.geekbrains.stopwatch

interface Contract {
    interface TimestampProvider {
        fun getMilliseconds(): Long
    }
}