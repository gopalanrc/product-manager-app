package com.thales.productmanager.util

import android.content.ContentResolver
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

fun copyFile(uri: Uri, contentResolver: ContentResolver, destinationFile: File) {
    val outputStream = FileOutputStream(destinationFile)
    val inputStream = contentResolver.openInputStream(uri)

    inputStream?.let {
        copy(it, outputStream)
    }
    outputStream.flush()

    inputStream?.close()
    outputStream.close()
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}