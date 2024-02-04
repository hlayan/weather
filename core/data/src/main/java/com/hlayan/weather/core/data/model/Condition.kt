package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Condition
import com.hlayan.weather.core.network.model.NetworkCondition

fun NetworkCondition.asCondition() = Condition(
    text = text,
    icon = icon,
    code = code
)