package com.mcp.downpic.util.annotation

import org.apache.http.Consts
import org.apache.http.HttpEntity
import org.apache.http.HttpEntityEnclosingRequest
import org.apache.http.NameValuePair
import org.apache.http.client.HttpRequestRetryHandler
import org.apache.http.client.config.AuthSchemes
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.conn.ConnectTimeoutException
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InterruptedIOException
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.util.*
import javax.net.ssl.SSLException

/**
 * Created by shiqm on 2017-11-21.
 */
class HttpClientWrapper {


    companion object {
        /* 请求超时时间 */
        private val Request_TimeOut = 20000
        /* Http连接池只需要创建一个*/
        private val httpPoolManager: PoolingHttpClientConnectionManager = PoolingHttpClientConnectionManager()
        /* 连接池最大生成连接数200 */
        private val Pool_MaxTotal = 200
        /* 连接池默认路由最大连接数,默认为20 */
        private val Pool_MaxRoute = 20
        /* 当前连接池中来年接诶 */
        private var httpClientCur: CloseableHttpClient? = null

        init {
            // 连接池总的最大生成连接数
            httpPoolManager.maxTotal = Pool_MaxTotal
            //设置每个route最大连接数
            httpPoolManager.defaultMaxPerRoute = Pool_MaxRoute
        }

        //请求重试机制
        private var myRetryHandler: HttpRequestRetryHandler = HttpRequestRetryHandler { exception, executionCount, context ->
            if (executionCount >= 3) {
                // 超过三次则不再重试请求
                return@HttpRequestRetryHandler false
            }
            if (exception is InterruptedIOException) {
                // Timeout
                return@HttpRequestRetryHandler false
            }
            if (exception is UnknownHostException) {
                // Unknown host
                return@HttpRequestRetryHandler false
            }
            if (exception is ConnectTimeoutException) {
                // Connection refused
                return@HttpRequestRetryHandler false
            }
            if (exception is SSLException) {
                // SSL handshake exception
                return@HttpRequestRetryHandler false
            }
            val clientContext = HttpClientContext.adapt(context)
            val request = clientContext.request
            val idempotent = request !is HttpEntityEnclosingRequest
            if (idempotent) {
                // Retry if the request is considered idempotent
                return@HttpRequestRetryHandler true
            }
            false
        }

        private fun getHttpClient(): CloseableHttpClient {
            if (httpClientCur == null) {
                val defaultRequestConfig = RequestConfig
                        .custom()
                        .setConnectTimeout(Request_TimeOut)
                        .setConnectionRequestTimeout(Request_TimeOut)
                        .setSocketTimeout(Request_TimeOut)
                        .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                        .setExpectContinueEnabled(true)
                        .setTargetPreferredAuthSchemes(
                                Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                        .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                        .build()
                // 创建httpClient
                httpClientCur = HttpClients.custom()
                        .setConnectionManager(httpPoolManager)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .setRetryHandler(myRetryHandler)
                        .build()
            }
            return httpClientCur!!
        }

        fun post(url: String, params: Map<String, Any?>, encoding: Charset = Consts.UTF_8, block: (HttpPost) -> HttpPost = { it }): String? {
            val request = block(HttpPost(url))
            try {
                val reqParams = ArrayList<NameValuePair>()
                for ((k, v) in params) {
                    if (v != null) {
                        reqParams.add(BasicNameValuePair(k, v.toString()))
                    }
                }
                request.entity = UrlEncodedFormEntity(reqParams, encoding)
                val context = HttpClientContext.create()
                val response = getHttpClient().execute(request, context)
                response.use {
                    return EntityUtils.toString(response!!.entity, encoding)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun get(url: String, encoding: Charset = Consts.UTF_8, block: (HttpGet) -> HttpGet = { it }): String? {
            val request = block(HttpGet(url))
            try {
                val response = getHttpClient().execute(request)
                response.use {
                    return EntityUtils.toString(response!!.entity, encoding)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun downFile(url: String, pathname: String, block: (HttpGet) -> HttpGet = { it }): Boolean {
            val request = block(HttpGet(url))
            try {
                val response = getHttpClient().execute(request)
                response.use {
                    val httpEntity = response.entity
                    val inputContentStream = httpEntity!!.content
                    val saveFile = File(pathname)
                    val fos = FileOutputStream(saveFile)
                    val bufferSave = ByteArray(4096)
                    while (true) {
                        //当有内容时读取一行数据，否则退出循环
                        var len = inputContentStream.read(bufferSave)
                        if (len != -1) {
                            fos.write(bufferSave, 0, len)
                        } else {
                            break
                        }
                    }
                    fos.close()
                    inputContentStream.close()
                    return true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
    }
}