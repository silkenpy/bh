package ir.rkr.bh


import com.typesafe.config.ConfigFactory
import ir.rkr.bh.rest.JettyRestServer
import mu.KotlinLogging
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread


const val version = 0.1

/**
 * CacheService main entry point.
 */
fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}
    val config = ConfigFactory.defaultApplication()

    JettyRestServer(config)
    logger.info { "BH V$version is ready :D" }


    val num = AtomicLong(0)

    val cm = PoolingHttpClientConnectionManager()
    cm.maxTotal = 200
    cm.defaultMaxPerRoute = 200
    val httpclient = HttpClients.custom().setConnectionManager(cm).build()

    val t1 = System.currentTimeMillis()
    for (i in 1..100)
        thread {
            for (j in 0..10000) {
                val httpget = HttpGet("http://localhost:7070/ali")
//        httpget.entity = StringEntity("salam")
                val res = httpclient.execute(httpget)
                val r = EntityUtils.toString(res.entity)
                res.close()

//        println(r)

            }
            num.incrementAndGet()
        }

        while (num.get()<100L){

        Thread.sleep(10)
    }
    println(System.currentTimeMillis()-t1)

//    val num = AtomicLong(0)
//
//    val url = URL("http://localhost:7070/ali")
//    println(System.currentTimeMillis())
//    for (i in 1..100)
//        thread {
//
//
//            for ( j in 1..10000) {
//
//
//
//
//                val con = url.openConnection() as HttpURLConnection
//                con.requestMethod = "GET"
//                con.connectTimeout = 200
////        con.setRequestProperty("Authorization", "Bearer");
////        con.setRequestProperty("Sabet-Bucket-Public-Key", "aa");
////        con.setRequestProperty("Content-Type", "application/octet-stream");
//
////    con.setRequestProperty("User-Agent", USER_AGENT);
////    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//                con.doOutput = true
////        val os = con.getOutputStream()
////        val osw = OutputStreamWriter(os, "UTF-8")
////        osw.write("Just Some Text")
////        osw.flush()
//                val responseCode = con.responseCode
//
//                val ins = BufferedReader(
//                        InputStreamReader(con.inputStream))
//                val response = StringBuffer()
//
//                response.append(ins.readLine())
//
//
////            println("$responseCode ${con.responseMessage} ${response} ")
////                thread { con.disconnect() }
//            }
//            num.getAndIncrement()
//        }
//
//    while (num.get()<100L){
//
//        Thread.sleep(10)
//    }
//
//    println(System.currentTimeMillis())

}
