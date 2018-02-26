package com.mcp.downpic.service

import com.mcp.downpic.Constants
import com.mcp.downpic.dao.IdsDao
import com.mcp.downpic.dao.PictureDao
import com.mcp.downpic.dao.RecordDao
import com.mcp.downpic.entity.Picture
import com.mcp.downpic.entity.Record
import com.mcp.downpic.util.Source
import com.mcp.downpic.util.annotation.HttpClientWrapper
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

/**
 * Created by shiqm on 2017-12-19.
 */

@Service
class Pic58Service : DownService {

    @Autowired
    private lateinit var pictureDao: PictureDao

    val cookies = "qt_visitor_id=%22799399578c2a9861f6e6490e94777b97%22; qt_ur_type=2; user-browser=%22baidu%22; awake=0; Hm_lvt_644763986e48f2374d9118a9ae189e14=1519639425; loginBackUrl=%22http%3A%5C%2F%5C%2Fwww.58pic.com%5C%2F%22; referer=%22http%3A%5C%2F%5C%2Fwww.58pic.com%5C%2Findex.php%3Fm%3Duserinfo%26a%3DgetImgCaptch%26v%3D1519639442536%22; imgCodeKey=%224098606848ab4b78b6387a0e70208aae%22; loginCodeKey=%22loginCode77be6539ff58500815e35b1f3cc6444d%22; risk_forbid_login_uid=%227966495%22; qt_risk_visitor_id=%22cb04a0d608ae18f737c3a3cec5584a6c%22; auth_id=%227966495%7CSC+%5Cu2116+dream%7C1520244317%7C389307b366de0e51331872615b9f563f%22; success_target_path=%22http%3A%5C%2F%5C%2Fwww.58pic.com%5C%2Findex.php%3Fm%3Duserinfo%26a%3DgetImgCaptch%26v%3D1519639442536%22; ssid=%225a93dbddbb8557.30710286%22; _auth_dl_=Nzk2NjQ5NXwxNTIwMjQ0MzE0fGZlOTczMTQwYzJiNGU5NGYxOGFmYzk4N2EyZmIwZTQz; censor=%2220180226%22; 2018-02-26-userSign-7966495=1; message2=1; is_pay1519574400=%221%22; Hm_lpvt_644763986e48f2374d9118a9ae189e14=1519639603"

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
        println(result)
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
        picture.downUrl = downUrl
        picture.data_time = System.currentTimeMillis() / 1000
        picture.path = Constants.SAVE_PATH
        val pattern = Pattern.compile("(?<=/)([A-Za-z0-9.]+)(?=\\?)")
        val matcher = pattern.matcher(downUrl)
        if (matcher.find()) {
            picture.name = matcher.group()
        }
        pictureDao.save(picture)
        return true
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
