package com.kang.nanohttpddemo.webserver

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.kang.nanohttpddemo.R
import fi.iki.elonen.NanoHTTPD
import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory

/**
 * Date: 2022/4/21
 * Author: SunBinKang
 * Description:
 */
class HttpServer(hostname: String?, port: Int) : NanoHTTPD(hostname, port) {//继承NanoHTTPD

    private val TAG = "binkang"
    private var count = 0 //用于记录请求为第几次
    private var mGson: Gson = Gson() //用于记录请求为第几次

    //注释一下这段代码就是http server,加上就是https server,但是是自签名的证书
    //服务器信任的客户端证书
    constructor(context: Context, hostname: String?, port: Int) : this(hostname, port) {
        //从文件中拿到流对象
        val keystoreStream: InputStream =
            context.resources.openRawResource(R.raw.keystore)
        //拿到keystore对象
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        //keystore加载流对象，并把storepass参数传入
        keyStore.load(keystoreStream, "myKeyStorePass".toCharArray())
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        //这里的第二个参数就是密钥密码，keypass
        keyManagerFactory.init(keyStore, "*******".toCharArray())
        //调用 NanoHttpd的makeSecure()方法
        makeSecure(makeSSLSocketFactory(keyStore, keyManagerFactory), null)
    }

    override fun serve(session: IHTTPSession?): Response {
        return dealWith(session)
    }

    private fun dealWith(session: IHTTPSession?): Response {
        //日志输出外部请求相关的日志信息
        Log.i(
            TAG,
            "dealWith: session.uri = ${session?.uri}, method = ${session?.method}, header = ${session?.headers}, " +
                    "params = ${session?.parameters}"
        )
        //响应get请求
        if (Method.GET == session?.method) {

            if (session.uri == HTTP_URI_SAYSOMETHING) {
                count++
                val param = session.parameters
                return responseJsonString(
                    200,
                    "${param["name"]?.get(0)}, say somthing to me $count, ok?",
                    "请求成功！"
                )
            }

        } else if (Method.POST == session?.method) {//响应post请求
            //获取请求头数据
            val header = session.headers
            //获取传参参数
            val param = session.parameters

            return when (session.uri) {

                HTTP_URI_SUM -> {
                    var b: Int = param["number"]?.get(0).toString().toInt()
                    for (i in 0..100) {
                        b += 1
                    }
                    responseJsonString(200, b, "Success！")
                }

                HTTP_URI_HELLO -> responseJsonString(
                    200,
                    "Hello ${param["name"]?.get(0)} !",
                    "Success！"
                )

                else -> responseJsonString(404, "It's nothing!", "Success！")

            }
        }
        return responseJsonString(404, "", "Request not support!")
    }

    private fun <T : Any> responseJsonString(code: Int, data: T, msg: String): Response {
        val response = Responser<T>(code, data, msg)
        Log.i(TAG, "responseJsonString: $response")
        return newFixedLengthResponse(mGson.toJson(response))//返回对应的响应体Response
    }
}