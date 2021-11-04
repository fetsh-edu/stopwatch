package me.fetsh.geekbrains.stopwatch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import me.fetsh.geekbrains.stopwatch.Contract
import me.fetsh.geekbrains.stopwatch.StopwatchState
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainViewModel(
    private val timestampProvider: Contract.TimestampProvider
) : ViewModel() {

    private var job: Job? = null
    private val scope : CoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob()
    )

    private val _currentState: MutableLiveData<StopwatchState> = MutableLiveData(StopwatchState.INIT)
    val currentState : LiveData<StopwatchState> = _currentState

    fun onStart() {
        _currentState.value = _currentState.value?.start(timestampProvider.getMilliseconds())
        if (job == null) startJob()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                _currentState.value = _currentState.value?.update(timestampProvider.getMilliseconds())
                delay(100)
            }
        }
    }

    fun onPause() {
        _currentState.value = _currentState.value?.pause()
        stopJob()
    }
    fun onStop() {
        _currentState.value = _currentState.value?.stop()
        stopJob()
    }
    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}