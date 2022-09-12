@file:Suppress("HttpUrlsUsage")

package com.willfp.libreforge

import com.willfp.eco.core.config.ConfigType
import com.willfp.eco.core.config.TransientConfig
import com.willfp.eco.core.config.interfaces.Config
import java.io.BufferedReader
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

internal fun LibReforgePlugin.shareConfigs(directory: String) {
    val configs = this.getUsermadeConfigs(directory)

    if (configs.isEmpty()) {
        return
    }

    val key = getKey()

    for ((id, config) in configs) {
        shareConfig(config, id, this, key)
    }
}

private fun getKey(): String {
    val url = URL("http://configshare.auxilor.io/key")
    val connection = url.openConnection()

    return try {
        val stream = connection.getInputStream()
        val reader = stream.reader()
        BufferedReader(reader).readLine()
    } catch (_: Exception) {
        "" // Handle rate limit the bad way!
    }
}

private fun shareConfig(config: Config, id: String, plugin: LibReforgePlugin, key: String) {
    val body = TransientConfig(
        mapOf(
            "id" to id,
            "plugin" to plugin.name,
            "config" to config.toPlaintext(),
            "key" to key
        ), ConfigType.JSON
    ).toPlaintext()

    val client = HttpClient.newBuilder().build()

    val request = HttpRequest.newBuilder()
        .uri(URI.create("http://configshare.auxilor.io/"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build()

    try {
        client.send(request, HttpResponse.BodyHandlers.ofString())
    } catch (_: Exception) {
        // Do nothing, just let it fail.
    }
}