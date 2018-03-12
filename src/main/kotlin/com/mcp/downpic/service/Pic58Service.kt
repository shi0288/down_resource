package com.mcp.downpic.service

import com.mcp.downpic.Constants
import com.mcp.downpic.dao.PictureDao
import com.mcp.downpic.entity.Picture
import com.mcp.downpic.util.CookiesUtil
import com.mcp.downpic.util.annotation.HttpClientWrapper
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.util.regex.Pattern
import javax.servlet.http.HttpServletResponse

/**
 * Created by shiqm on 2017-12-19.
 */

@Service
class Pic58Service : DownService {

    @Autowired
    private lateinit var pictureDao: PictureDao

    val cookies = "qt_visitor_id=%22799399578c2a9861f6e6490e94777b97%22; qt_ur_type=2; qt_risk_visitor_id=%22cb04a0d608ae18f737c3a3cec5584a6c%22; message2=1; awake=0; Hm_lvt_644763986e48f2374d9118a9ae189e14=1519639425,1519697482,1519714231,1520834785; referer=%22http%3A%5C%2F%5C%2Fwww.58pic.com%5C%2Findex.php%3Fm%3Duserinfo%26a%3DgetImgCaptch%26v%3D1520834792690%22; imgCodeKey=%222a41c67e50040ff96cfee27a25c15c32%22; loginCodeKey=%22loginCode451b888662ee9d315da1ef691ccb2cd2%22; risk_forbid_login_uid=%227966495%22; auth_id=%227966495%7CSC+%5Cu2116+dream%7C1521439641%7C4ab00036fd766cf0c83d279faa86d80c%22; success_target_path=%22http%3A%5C%2F%5C%2Fwww.58pic.com%5C%2Findex.php%3Fm%3Duserinfo%26a%3DgetImgCaptch%26v%3D1520834792690%22; ssid=%225aa61919914f18.52322861%22; _auth_dl_=Nzk2NjQ5NXwxNTIxNDM5NjM4fDExMmU1NjM0ODEwNzVkNmFmMzA4ZTRhYjc3MmRjMzIz; censor=%2220180312%22; 2018-03-12-userSign-7966495=1; 1490c6811c510539f99068d1b8b4e2ba=%22111.205.32.2%22; is_pay1520784000=%221%22; Hm_lpvt_644763986e48f2374d9118a9ae189e14=1520848274"

    override fun fileAnalysis(picture: Picture): Boolean {
        val url = "https://dl.58pic.com/${picture.outerId}.html"
        val result = HttpClientWrapper.get(url, block = { httpRequest ->
            httpRequest.addHeader("Cookie", cookies)
            httpRequest.addHeader("Host", "dl.58pic.com")
            httpRequest.addHeader("Cache-Control", "max-age=0")
            httpRequest.addHeader("Connection", "keep-alive")
            httpRequest.addHeader("Upgrade-Insecure-Requests", "1")
            httpRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            httpRequest.addHeader("Referer", url)
            httpRequest.addHeader("Accept-Encoding", "gzip, deflate, br")
            httpRequest.addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            httpRequest
        })
        val document = Jsoup.parse(result)
        val elements = document.getElementsByAttribute("attr-type")
        var downUrl = ""
        run {
            elements.forEach {
                val type = it.attr("attr-type")
                if (type == "a1") {
                    downUrl = it.attr("href").toString()
                    return@run
                }
            }
        }
        val titleElements = document.getElementsByAttribute("thunderrestitle")
        var title = ""
        run {
            titleElements.forEach {
                val temp = it.attr("thunderrestitle")
                if (temp != "") {
                    title = temp.toString()
                    return@run
                }
            }
        }
        picture.title = title
//        picture.downUrl = downUrl
        picture.data_time = System.currentTimeMillis() / 1000
        picture.path = Constants.SAVE_PATH
        val pattern = Pattern.compile("(?<=/)([A-Za-z0-9.]+)(?=\\?)")
        val matcher = pattern.matcher(downUrl)
        if (matcher.find()) {
            picture.name = matcher.group()
        }
        if (picture.name.isNullOrEmpty()) {
            return false
        }
        pictureDao.save(picture)
        return true
    }

    override fun startDown(picture: Picture, httpResponse: HttpServletResponse) {
        val url = "https://dl.58pic.com/${picture.outerId}.html"
        val result = HttpClientWrapper.get(url, block = { httpRequest ->
            httpRequest.addHeader("Cookie", cookies)
            httpRequest.addHeader("Host", "dl.58pic.com")
            httpRequest.addHeader("Cache-Control", "max-age=0")
            httpRequest.addHeader("Connection", "keep-alive")
            httpRequest.addHeader("Upgrade-Insecure-Requests", "1")
            httpRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            httpRequest.addHeader("Referer", url)
            httpRequest.addHeader("Accept-Encoding", "gzip, deflate, br")
            httpRequest.addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            httpRequest
        })
        val document = Jsoup.parse(result)
        val elements = document.getElementsByAttribute("attr-type")
        var downUrl = ""
        run {
            elements.forEach {
                val type = it.attr("attr-type")
                if (type == "a1") {
                    downUrl = it.attr("href").toString()
                    return@run
                }
            }
        }
        if (downUrl.isNotEmpty()) {
            CookiesUtil.saveCookies(cookies, "58pic.com", httpResponse)
            val url = downUrl.split("n=")[0]
            httpResponse.sendRedirect("${url}n=${URLEncoder.encode(picture.title, "UTF-8")}.${picture.name!!.split(".")[1]}")
        }
    }


    override fun fileDown(picture: Picture): Boolean {
        val result = HttpClientWrapper.downFile(picture.downUrl!!, Constants.SAVE_PATH + "/" + picture.name, { request ->
            request.setHeader("Host", "proxy-vip.58pic.com")
            request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            request.setHeader("Upgrade-Insecure-Requests", "1")
            request.setHeader("Cookie", cookies)
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
            request.setHeader("Accept-Language", "zh-CN,zh;q=0.9")
            request.setHeader("Accept-Encoding", "gzip, deflate")
            request
        })
        if (result) {
            picture.status = 2
            pictureDao.updateById(picture)
            return true
        }
        picture.status = 3
        pictureDao.updateById(picture)
        return false
    }


}
