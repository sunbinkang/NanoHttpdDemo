package com.kang.nanohttpddemo.webserver

/**
 * Date: 2022/4/21
 * Author: SunBinKang
 * Description:
 */
data class Responser<T>(val code: Int, val data: T, val msg: String) {
}