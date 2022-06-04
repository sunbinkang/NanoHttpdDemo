package com.kang.nanohttpddemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kang.nanohttpddemo.webserver.HttpServer;
import fi.iki.elonen.NanoHTTPD;
import static com.kang.nanohttpddemo.webserver.ConfigKt.HTTP_IP;
import static com.kang.nanohttpddemo.webserver.ConfigKt.HTTP_PORT;

public class Demo {
    private final static String TAG = " Demo";
    private static HttpServer mHttpServer;
    private final static Logger logger = Logger.getLogger(TAG);

    public static void main(String[] args) {
        //FIXME get Short read of DER length !!
        final String fileName = "keystore.bks";
        InputStream keystoreStream = Demo.class.getClassLoader().getResourceAsStream(fileName);
        if (keystoreStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        } else {
            try {
                mHttpServer = new HttpServer(keystoreStream, HTTP_IP, HTTP_PORT);
                //            //三种启动方式都行
//                mHttpServer.start();
//                mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT);
                mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            } catch (IOException ioe) {
                logger.log(Level.WARNING, "Start HttpServer  Exception :  " + ioe);
            }
        }
    }

    private File getResourceFile(final String fileName) {
        URL url = this.getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException(fileName + " is not found ");
        }
        File file = new File(url.getFile());
        return file;
    }

    private InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}
