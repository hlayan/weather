package com.hlayan.weather.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.hlayan.weather.core.model.UserPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferences> {

    override val defaultValue = UserPreferences(darkTheme = false)

    override suspend fun readFrom(input: InputStream): UserPreferences =
        try {
            Json.decodeFromString(
                UserPreferences.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserData", serialization)
        }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(UserPreferences.serializer(), t).encodeToByteArray()
        )
    }
}