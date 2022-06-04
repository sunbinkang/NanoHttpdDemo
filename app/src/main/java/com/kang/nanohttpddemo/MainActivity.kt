package com.kang.nanohttpddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kang.nanohttpddemo.webserver.HTTP_IP
import com.kang.nanohttpddemo.webserver.HTTP_PORT
import com.kang.nanohttpddemo.webserver.HttpServer
import fi.iki.elonen.NanoHTTPD
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    var mHttpServer: HttpServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        //从文件中拿到流对象
        val keystoreStream: InputStream =  MainActivity@ this.resources.openRawResource(R.raw.keystore)
        mHttpServer = HttpServer(MainActivity@ keystoreStream, HTTP_IP, HTTP_PORT)
        //三种启动方式都行
//        mHttpServer.start()
//        mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT)
        mHttpServer?.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        mHttpServer?.stop()//停止
    }
}