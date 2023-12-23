package com.harison.core.app.api

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

@Singleton
class FileDownloader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
) {
    private val outputFolder = File(context.filesDir, "downloads")

    /**
     * Function to download file using retrofit.
     * Output folder is data/downloads
     * @return absolutePath of File. It will return file storage in dataApp if it has been downloaded
     * */
    suspend fun downloadFile(
        fileUrl: String,
        onStart: () -> Unit = {},
        onSuccess: (String) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        try {
            onStart()
            val fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1)
            val outputFile = File(outputFolder, fileName)
            if (File(outputFolder, fileName).exists()) {
                onSuccess(outputFile.absolutePath)
                Timber.tag("----Return file in storage:").d(outputFile.absolutePath)
                return
            }
            val response: Response<ResponseBody> = apiService.downloadFile(fileUrl)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Timber.tag("----").d("file name: %s", fileName)
                if (responseBody != null) {
                    saveFile(responseBody.byteStream(), fileName) {
                        onSuccess(it)
                        Timber.tag("----Save file success:").e(it)
                    }
                } else {
                    onError("Response body is empty.")
                }
            } else {
                onError("Download failed with code ${response.code()}")
            }
        } catch (e: Exception) {
            onError("Download failed: ${e.message}")
        }
    }

    private suspend fun saveFile(
        inputStream: InputStream,
        fileName: String,
        saveDone: (String) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            Timber.tag("----").d("output folder: %s", outputFolder.absolutePath)
            if (!outputFolder.exists()) {
                outputFolder.mkdirs()
            }
            val outputFile = File(outputFolder, fileName)
            try {
                val outputStream = FileOutputStream(outputFile)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                saveDone.invoke(outputFile.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getFileExtension(contentType: String?): String {
        return when (contentType) {
            "image/png" -> "png"
            "image/jpeg" -> "jpg"
            else -> "png"
        }
    }
}
