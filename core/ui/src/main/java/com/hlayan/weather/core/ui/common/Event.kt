package com.hlayan.weather.core.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.atomic.AtomicBoolean

class Event<out T>(private val _value: T?) {

    private val used = AtomicBoolean(false)

    val value: T? get() = if (used.compareAndSet(false, true)) _value else null
}

val EmptyEvent get() = Event(null)

typealias FlowEvent<T> = StateFlow<Event<T>>
typealias MutableFlowEvent<T> = MutableStateFlow<Event<T>>

fun <T : Any> mutableFlowEvent(): MutableFlowEvent<T> = MutableStateFlow(EmptyEvent)

fun <T : Any> MutableFlowEvent<T>.asFlowEvent(): FlowEvent<T> = asStateFlow()

fun <T : Any> MutableFlowEvent<T>.sendEvent(value: T) {
    this.value = Event(value)
}

suspend fun <T : Any> FlowEvent<T>.collectEvent(action: suspend (value: T) -> Unit) {
    collectLatest { value.value?.let { action(it) } }
}

@Composable
fun <T> EventEffect(
    event: Event<T>,
    block: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(event) {
        event.value?.let { block(it) }
    }
}