package ir.rkr.bh


import com.typesafe.config.ConfigFactory
import ir.rkr.bh.rest.JettyRestServer
import mu.KotlinLogging
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils

import java.net.InetSocketAddress
import java.net.ProxySelector




const val version = 0.1

/**
 * CacheService main entry point.
 */
fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}
    val config = ConfigFactory.defaultApplication()

    JettyRestServer(config)
    logger.info { "BH V$version is ready :D" }


    println(System.currentTimeMillis())
    val cm = PoolingHttpClientConnectionManager()
    cm.maxTotal = 200
    val httpclient = HttpClients.custom().setConnectionManager(cm).build()
    for (i in 0..100000) {
        val httpget = HttpGet("http://localhost:7070/ali")
//        httpget.entity = StringEntity("salam")
        val res = httpclient.execute(httpget)
        val r = EntityUtils.toString(res.entity)
//        println(r)

    }
    println(System.currentTimeMillis())

}
