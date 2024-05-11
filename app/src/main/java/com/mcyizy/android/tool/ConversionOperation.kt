package com.mcyizy.android.tool

import kotlin.text.Regex
import java.nio.charset.Charset

//转换操作
public class ConversionOperation {

    //文本转十六进制
    public fun StringtoInt(Text : String) : String {
        val chars = "0123456789ABCDEF".toCharArray()
        val sb = StringBuilder("")
        val bs = Text.toByteArray()
        var bit: Int
        for (i in bs.indices) {
            bit = (bs[i].toInt() and 0x0f0) shr 4
            sb.append(chars[bit])
            bit = bs[i].toInt() and 0x0f
            sb.append(chars[bit])
        }
        var hexMsg = sb.toString().trim()
        return hexMsg
    }
    
    //十六进制转文本
    public fun InttoString(Text : String) : String {
        val str = "0123456789ABCDEF"
        val hexs = Text.toCharArray()
        val bytes = ByteArray(Text.length / 2)
        var n: Int
        for (i in bytes.indices) {
            n = str.indexOf(hexs[2 * i]) * 16
            n += str.indexOf(hexs[2 * i + 1])
            bytes[i] = (n and 0xff).toByte()
        }
        return String(bytes)
    }
    
    //中文转Unicode
    public fun ChinesetoUnicode(value : String) : String {
        val utfBytes = value.toCharArray()
        var unicodeBytes = ""
        for (i in utfBytes.indices) {
            var hexB = Integer.toHexString(utfBytes[i].toInt())
            if (hexB.length <= 2) {
                hexB = "00$hexB"
            }
            unicodeBytes += "\\u$hexB"
        }
        return unicodeBytes
    }
    
    //Unicode转中文
    public fun UnicodetoChinese(value : String) : String {
        val pattern = Regex("(\\\\u(\\p{XDigit}{4}))")
        var output = value
        pattern.findAll(value).forEach { matchResult ->
            val unicodeChar = Integer.parseInt(matchResult.groupValues[2], 16).toChar()
            output = output.replace(matchResult.groupValues[1], unicodeChar.toString())
        }
        return output
    }
    
    //文本转字节
    public fun textToByte(value: String, encoding: String = "utf-8"): ByteArray {
        try {
            return value.toByteArray(Charset.forName(encoding))
        } catch (ex: Exception) {
            throw RuntimeException("文本到字节( 解码错误")
        }
    }
    
    //字节转文本
    public fun byteToText(value: ByteArray, encoding: String = "utf-8"): String? {
        try {
            return String(value, Charset.forName(encoding))
        } catch (ex: Exception) {
        }
        return null
    }

}
