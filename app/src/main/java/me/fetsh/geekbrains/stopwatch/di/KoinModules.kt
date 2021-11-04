package me.fetsh.geekbrains.stopwatch.di

import me.fetsh.geekbrains.stopwatch.Contract
import me.fetsh.geekbrains.stopwatch.TimestampFormatter
import me.fetsh.geekbrains.stopwatch.ui.main.MainViewModel
import org.koin.dsl.module
import kotlin.time.ExperimentalTime

@ExperimentalTime
val application = module {
    single<Contract.TimestampProvider> {
        object : Contract.TimestampProvider {
            override fun getMilliseconds(): Long {
                return System.currentTimeMillis()
            }
        }
    }
    single { TimestampFormatter() }
}
@ExperimentalTime
val mainScreen = module {
    factory {
        MainViewModel(timestampProvider = get())
    }
}