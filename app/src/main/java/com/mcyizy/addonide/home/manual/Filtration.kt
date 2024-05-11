package com.mcyizy.addonide.home.manual

import java.io.File

public class Filtration {

public var getTotalQuantity : Int = 0

// 递归遍历目录下的所有文件和文件夹
private fun getAllFilesInDirectory(directoryPath: String): Array<String> {
    val fileList = ArrayList<String>()
    val directory = File(directoryPath)
    val files = directory.listFiles()

    if (files != null) {
        for (file in files) {
            if (file.isDirectory) {
                fileList.addAll(getAllFilesInDirectory(file.absolutePath)) // 递归调用，遍历子目录
            } else {
                fileList.add(file.absolutePath) // 添加文件路径到结果列表
            }
        }
    }
    return fileList.toTypedArray()
}

// 根据文件名搜索文件
public fun searchFilesByName(directoryPath: String, fileName: String): Array<String> {
    val allFiles = getAllFilesInDirectory(directoryPath) // 获取指定目录下的所有文件
    getTotalQuantity = allFiles.size
    val matchingFiles = mutableListOf<String>()
    for (file in allFiles) {
        val fileObj = File(file)
        if (fileObj.name.contains(fileName)) { // 判断文件名是否满足条件
            matchingFiles.add(file) // 如果文件名符合条件，则添加到结果列表
        }
    }
    return matchingFiles.toTypedArray()
}

}
