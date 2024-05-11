package com.mcyizy.android.tool

import java.net.URLEncoder
import java.net.URLDecoder
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

//编码操作
public class CodingOperation {

    //URL编码
    public fun URLEncoding(value : String,encoding : String = "UTF-8") : String {
        try {
			return URLEncoder.encode(value, encoding)
		} catch (e: UnsupportedEncodingException) {
			return value
		}
    }
    
    //URL解码
    public fun URLDecoding(value : String,encoding : String = "UTF-8") : String {
        try {
			return URLDecoder.decode(value, encoding)
		} catch (e: UnsupportedEncodingException) {
			return value
		}
    }
    
    //转换编码
    public fun Transcoding(Text: String?, OriginalCode: String, NewCode: String): String {
        if (Text == null) {
            return ""
        }
        try {
            return String(Text.toByteArray(Charset.forName(OriginalCode)), Charset.forName(NewCode))
        } catch (e: UnsupportedEncodingException) {
            return Text
        }
    }

}
