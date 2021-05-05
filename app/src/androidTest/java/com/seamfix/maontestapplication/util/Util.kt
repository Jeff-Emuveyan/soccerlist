package com.seamfix.maontestapplication.util

import android.content.Context
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun asset(context: Context, assetPath: String): String {
    try {
        val inputStream = context.assets.open(assetPath)
        return inputStreamToString(inputStream, "UTF-8")
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}

private fun inputStreamToString(inputStream: InputStream, charsetName: String): String {
    val builder = StringBuilder()
    val reader = InputStreamReader(inputStream, charsetName)
    reader.readLines().forEach {
        builder.append(it)
    }
    return builder.toString()
}


fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
                MockResponse()
                        .setResponseCode(code)
                        .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}