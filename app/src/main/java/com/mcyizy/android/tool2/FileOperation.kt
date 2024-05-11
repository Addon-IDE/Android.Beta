package com.mcyizy.android.tool2

import android.content.Context

import java.io.File
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.FileOutputStream
import java.util.ArrayList
import java.io.InputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date

//文件操作
public class FileOperation {

   //获取文件名称
   public fun getFileNmae(path : String) : String {
       val file = File(path)
        return file.name
   }
   
   //获取文件前缀名
   public fun getFilePrefixName(path : String) : String {
       val file = File(path)
        val name = file.nameWithoutExtension
        return name
   }
   
   //获取文件后缀名
   public fun getFileSuffixName(path : String) : String {
       val file = File(path)
        val extension = file.extension
        return extension
   }
   
   //判断文件是否存在
   public fun Exists(path : String) : Boolean {
        val file = File(path)
        val fileExists = file.exists()
        return fileExists
   }
   
   //获取文件修改时间
   public fun GetModifiedTime(path : String) : String {
        val file = File(path)
        val lastModified = file.lastModified()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date(lastModified))
   }
   
   //获取文件父目录
   public fun getParentDirectoryPath(filePath: String): String {
        val file = File(filePath)
        return file.parent
    }

   //创建目录
   public fun CreateDirectory(path : String) : Boolean {
       val directory = File(path)
        return if (!directory.exists()) {
            directory.mkdirs()
        } else {
            false
        }
   }

   //取子文件集合
   public fun FetchSubfileCollection(path : String) : Array<String> {
        val list = ArrayList<String>()
        val ff = File(path).listFiles()
        if (ff != null) {
            for (file in ff) {
                list.add(file.absolutePath)
            }
        }
        return list.toTypedArray()
    }
    
   //读取文件 
   public fun ReadFile(filePath: String,Encoding: String = "UTF-8"): String {
        try {
            val br = BufferedReader(InputStreamReader(FileInputStream(filePath), Encoding))
            var first = true
            val content = StringBuilder()
            var line: String?
            while (br.readLine().also { line = it } != null) {
                if (first) {
                    first = false
                    content.append(line)
                } else {
                    content.append('\n').append(line)
                }
            }
            br.close()
            return content.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
    
   //写入文件
    public fun WriteFile(filePath: String, content: String, encoding: Charset = Charsets.UTF_8) {
        try {
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file),encoding))
            writer.write(content)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            return
        }
    }
    
    //重命名 <旧  新>
    public fun renameFile(originalPath: String, newPath: String): Boolean {
        if (newPath == originalPath) {
            return true
        }
        val oldFile = File(originalPath)
        if (!oldFile.exists()) {
            return false
        }
        val newFile = File(newPath)
        if (newFile.exists()) {
            return false
        }
        if (oldFile.renameTo(newFile)) {
            return true
        }
        return false
    }
    
    //删除文件
    public fun deleteFile(filePath: String): Boolean {
       val file = File(filePath)
        return deleteFile(file)
    }
    private fun deleteFile(file: File): Boolean {
        var success = true
        if (file.exists()) {
            if (file.isDirectory) {
                file.listFiles()?.forEach { subFile ->
                    if (!success) {
                        return false
                    }
                    success = success && deleteFile(subFile)
                }
            }
            if (success) {
                success = success && file.delete()
            }
        }
        return success
    }
    
    //是否为目录
    public fun isDirectory(path : String) : Boolean {
        val file = File(path)
        if (file.isDirectory) {
            return true
        } else {
            return false
        }
        return false
    }
    
    //复制文件
    public fun CopyFile(filePath: String, destinationPath: String): Boolean {
        try {
            val file = File(filePath)
            val destinationFile = File(destinationPath)
            file.copyTo(destinationFile, true)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }
    
    //读入资源文件
    public fun ReadResourceFile(context: Context, name: String, encoding: String = "UTF-8"): String {
        try {
            val inputStream: InputStream = context.assets.open(name)
            val length: Int = inputStream.available()
            val buffer = ByteArray(length)
            inputStream.read(buffer)
            val res = String(buffer, Charset.forName(encoding))
            inputStream.close()
            return res
        } catch (e: IOException) {
            e.printStackTrace()
            return "null"
            throw RuntimeException("读入资源文件失败 (未找到文件: $name)")
        }
    }
    
    //写出资源文件
    public fun writeAssetToFile(context: Context, fileName: String, outputPath: String): Boolean {
        try {
            val inputStream = context.assets.open(fileName)
            val file = File(outputPath)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            if (inputStream != null && writeStreamToFile(inputStream, file)) {
                return true
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("写出资源文件($fileName 或 $outputPath)")
        }
    }
    private fun writeStreamToFile(inputStream: InputStream, file: File): Boolean {
        try {
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    
}