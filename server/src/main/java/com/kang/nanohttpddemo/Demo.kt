//package com.kang.nanohttpddemo
//
//import com.kang.nanohttpddemo.webserver.HTTP_IP
//import com.kang.nanohttpddemo.webserver.HTTP_PORT
//import fi.iki.elonen.NanoHTTPD
//import java.io.IOException
//import java.io.InputStream
//import java.util.logging.Level
//import java.util.logging.Logger
//
//fun main(args: Array<String>) {
//    val TAG = "Server"
//    var logger: Logger = Logger.getLogger(TAG)
//    var mHttpServer: com.kang.nanohttpddemo.webserver.HttpServer? = null
//    try {
//        Demo.getFileAsIOStream("keystore.bks")?.let { keystoreStream ->
//            mHttpServer = com.kang.nanohttpddemo.webserver.HttpServer(
//                MainActivity@ keystoreStream,
//                HTTP_IP,
//                HTTP_PORT
//            )
//            //三种启动方式都行
////        mHttpServer.start()
////        mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT)
//            mHttpServer?.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
//        }
//    } catch (ioe: IOException) {
//        logger.log(
//            Level.WARNING, "Start HttpServer  Exception : $ioe"
//        )
//    }
//}
//
//object Demo {
//    fun getFileAsIOStream(fileName: String): InputStream? {
//        return javaClass
//            .classLoader
//            .getResourceAsStream(fileName)
//            ?: throw IllegalArgumentException("$fileName is not found")
//    }
//}