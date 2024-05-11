package com.mcyizy.android.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import androidx.core.database.getStringOrNull
import androidx.core.net.toFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

//文件选择器
object FileHelper {

    fun getFileAbsolutePath(context: Context, imageUri: Uri?): String? {
        if (context == null || imageUri == null) {
            return null
        }

        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT -> getRealFilePath(context, imageUri)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && DocumentsContract.isDocumentUri(
                context,
                imageUri
            ) -> {
                when {
                    isExternalStorageDocument(imageUri) -> {
                        val docId = DocumentsContract.getDocumentId(imageUri)
                        val split = docId.split(":")
                        val type = split[0]
                        if ("primary".equals(type, ignoreCase = true)) {
                            return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                        }
                        null
                    }
                    isDownloadsDocument(imageUri) -> {
                        val id = DocumentsContract.getDocumentId(imageUri)
                        val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), id.toLong())
                        return getDataColumn(context, contentUri, null, null)
                    }
                    isMediaDocument(imageUri) -> {
                        val docId = DocumentsContract.getDocumentId(imageUri)
                        val split = docId.split(":")
                        val type = split[0]
                        val contentUri = when {
                            "image" == type -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" == type -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" == type -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        val selection = "${MediaStore.Images.Media._ID}=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }
                    else -> null
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> uriToFileApiQ(context, imageUri)
            "content".equals(imageUri.scheme, ignoreCase = true) -> {
                if (isGooglePhotosUri(imageUri)) {
                    return imageUri.lastPathSegment
                }
                getDataColumn(context, imageUri, null, null)
            }
            "file".equals(imageUri.scheme, ignoreCase = true) -> imageUri.path
            else -> null
        }
    }

    private fun getRealFilePath(context: Context, uri: Uri): String? {
        val scheme = uri.scheme
        val data: String = when {
            ContentResolver.SCHEME_FILE == scheme -> uri.path ?: ""
            ContentResolver.SCHEME_CONTENT == scheme -> {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        cursor.getStringOrNull(index) ?: ""
                    } else {
                        ""
                    }
                } ?: ""
            }
            else -> ""
        }
        return data
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        if (uri == null) {
            return ""
        }
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(column)
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getStringOrNull(index)
            }
        }
        return null
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun getFileFromContentUri(context: Context, uri: Uri?): String {
        if (uri == null) {
            return ""
        }
        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
        val contentResolver = context.contentResolver
        contentResolver.query(uri, filePathColumn, null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            try {
                val filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                return filePath
            } catch (e: Exception) {
                return ""
            }
        }
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uriToFileApiQ(context: Context, uri: Uri): String {
        var file: File? = null
        if (uri.scheme != null) {            
            file = if (uri.scheme == ContentResolver.SCHEME_FILE) {
                uri.toFile()
            } else {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        try {
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val cache = File(context.externalCacheDir?.absolutePath, "${Math.round(Math.random() * 1000)}$displayName")
                            if (inputStream != null) {
                                FileOutputStream(cache).use { fos ->
                                    inputStream.copyTo(fos)
                                }
                                file = cache
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                file
            }
        }
        return file?.absolutePath ?: ""
    }
}