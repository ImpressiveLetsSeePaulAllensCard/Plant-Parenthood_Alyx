package com.example.plantparenthood

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import org.apache.commons.io.IOUtils
import java.io.File
import java.util.concurrent.Executor

class ProviderFileManager(
    private val context: Context,
    val fileHelper: FileHelper,
    private val contentResolver: ContentResolver,
    private val executor: Executor,
    private val mediaContentHelper: MediaContentHelper
) {
    fun generatePhotoUri(time: Long): FileInfo {
        val name = "img_$time.jpg"
        val file = File(
            context.getExternalFilesDir(fileHelper.getPicturesFolder()),
            name
        )

        val uri = FileProvider.getUriForFile(
            context,
            "com.example.plantparenthood.fileprovider",
            file
        )

        return FileInfo(
            uri,
            file,
            name,
            fileHelper.getPicturesFolder(),
            "image/jpeg"
        )
    }

    fun generateVideoUri(time: Long): FileInfo {
        val name = "video_$time.mp4"
        val file = File(
            context.getExternalFilesDir(fileHelper.getVideosFolder()),
            name
        )

        val uri = FileProvider.getUriForFile(
            context,
            "com.example.plantparenthood.fileprovider",
            file
        )

        return FileInfo(
            uri,
            file,
            name,
            fileHelper.getVideosFolder(),
            "video/mp4"
        )
    }

    fun insertImageToStore(fileInfo: FileInfo?) {
        fileInfo?.let {
            insertToStore(
                fileInfo,
                mediaContentHelper.getImageContentUri(),
                mediaContentHelper.generateImageContentValues(it)
            )
        }
    }

    fun insertVideoToStore(fileInfo: FileInfo?) {
        fileInfo?.let {
            insertToStore(
                fileInfo,
                mediaContentHelper.getVideoContentUri(),
                mediaContentHelper.generateVideoContentValues(it)
            )
        }
    }

    private fun insertToStore(fileInfo: FileInfo, contentUri: Uri, contentValues: ContentValues) {
        executor.execute {
            val insertedUri = contentResolver.insert(contentUri, contentValues)
            insertedUri?.let {
                val inputStream = contentResolver.openInputStream(fileInfo.uri)
                val outputStream = contentResolver.openOutputStream(insertedUri)
                IOUtils.copy(inputStream, outputStream)
            }
        }
    }
}