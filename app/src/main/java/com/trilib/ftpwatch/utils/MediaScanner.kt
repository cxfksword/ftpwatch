package com.trilib.ftpwatch.utils;

import android.content.Context
import android.media.MediaScannerConnection
import android.webkit.MimeTypeMap
import java.io.File
import java.util.concurrent.atomic.AtomicInteger


class MediaScanner(private val context: Context) {
  val SCAN_SIZE = 500000

  fun scanFilesSimply(folder: File) {
    val toScanFiles = ArrayList<File>()
    val completed = AtomicInteger()

    getScanFiles(folder, toScanFiles)

    if (toScanFiles.isNotEmpty()) {
      MediaScannerConnection.scanFile(
          context,
          toScanFiles.map { it.absolutePath }.toTypedArray(),
          toScanFiles.map { "audio/*" }.toTypedArray()
      ) { path, uri ->
        println("onScanCompleted, path: $path uri: $uri completed: $completed")
      }
    }
  }


  private fun getScanFiles(file: File, toScanFiles: ArrayList<File>) {
    if (file.isFile && file.length() >= SCAN_SIZE) {
      if (isAudioFile(file))
        toScanFiles.add(file)
    } else {
      val files = file.listFiles() ?: return
      for (temp in files) {
        getScanFiles(temp, toScanFiles)
      }
    }
  }

  private fun isAudioFile(file: File): Boolean {
    val ext = getFileExtension(file.name)
    val mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
    return !mime.isNullOrEmpty() && mime.startsWith("audio") && !mime.contains("mpegurl")
  }

  private fun getFileExtension(fileName: String): String? {
    val i = fileName.lastIndexOf('.')
    return if (i > 0) {
      fileName.substring(i + 1)
    } else
      null
  }

  companion object {
    private const val TAG = "MediaScanner"
  }
}
