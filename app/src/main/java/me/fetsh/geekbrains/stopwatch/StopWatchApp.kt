package me.fetsh.geekbrains.stopwatch

import android.app.Application
import me.fetsh.geekbrains.stopwatch.di.application
import me.fetsh.geekbrains.stopwatch.di.mainScreen
import org.koin.core.context.startKoin
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StopWatchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}