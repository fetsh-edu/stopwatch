package me.fetsh.geekbrains.stopwatch.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.fetsh.geekbrains.stopwatch.StopwatchState
import me.fetsh.geekbrains.stopwatch.TimestampFormatter
import me.fetsh.geekbrains.stopwatch.ui.theme.StopwatchTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModel()
    private val timestampFormatter : TimestampFormatter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchTheme {
                Surface(color = MaterialTheme.colors.background) {
                    StopwatchScreen(mainViewModel, timestampFormatter)
                }
            }
        }
    }
}

@ExperimentalTime
@Composable
fun StopwatchScreen(
    viewModel: MainViewModel,
    timestampFormatter: TimestampFormatter
) {
    val currentState : StopwatchState by viewModel.currentState.collectAsState()
    Stopwatch(
        timer = currentState,
        timestampFormatter = timestampFormatter,
        onStart = viewModel::onStart,
        onPause = viewModel::onPause,
        onStop = viewModel::onStop
    )
}

@ExperimentalTime
@Preview(showBackground = true)
@Composable
fun Stopwatch(
    timer: StopwatchState = StopwatchState.INIT,
    timestampFormatter: TimestampFormatter = TimestampFormatter(),
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Timer(
            timestampFormatter.format(timer.elapsedTime())
        )

        Row {
            Button(
                onClick = onStart,
                modifier = Modifier.padding(horizontal = 10.dp),
                enabled = timer is StopwatchState.Paused
            ) {
                Text(text = "Start")
            }
            Button(
                onClick = onPause,
                modifier = Modifier.padding(horizontal = 10.dp),
                enabled = timer is StopwatchState.Running
            ) {
                Text(text = "Pause")
            }
            Button(
                onClick = onStop,
                modifier = Modifier.padding(horizontal = 10.dp),
                enabled = timer.elapsedTime().toLong(DurationUnit.MILLISECONDS) != 0L
            ) {
                Text(text = "Stop")
            }
        }
    }
}

@Composable
private fun Timer(timer: String) {
    Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
        Text(text = timer)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchTheme {
        Greeting("Android")
    }
}