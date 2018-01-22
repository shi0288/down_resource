//package com.mcp.downpic.service
//
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
//
///**
// * Created by shiqm on 2018-01-15.
// */
//
//@RunWith(SpringJUnit4ClassRunner::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class Pic58ServiceTest{
//
//
//    @Autowired
//    private lateinit var pictureService: PictureService
//
//
//    @Autowired
//    private lateinit var pic58Service: Pic58Service
//
//
//    @Test
//    fun test(){
//        val picture = pictureService.getReadyPicture()
//        if (picture != null) {
//            pic58Service.fileDown(picture)
//        }
//    }
//
//
//
//
//
//
//
//
//}