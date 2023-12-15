package com.tilikki.training.unimager.demo.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log

fun Context.downloadFile(uri: Uri, filename: String): Long {
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadRequest = DownloadManager.Request(uri)
        .setTitle("$filename.jpg")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$filename.jpg")
    val downloadId = downloadManager.enqueue(downloadRequest)
    Log.d("downloadManager", "Start downloading $filename.jpg from $uri...")
    return downloadId
}
