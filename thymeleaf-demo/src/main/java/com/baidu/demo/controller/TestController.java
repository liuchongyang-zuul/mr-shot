package com.baidu.demo.controller;

import com.baidu.demo.entity.TestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

/**
 * @ClassName TestController
 * @Description: TODO
 * @Author liuchongyang
 * @Date 2020/9/15
 * @Version V1.0
 **/
@Controller
public class TestController {
//    @GetMapping("test")
//    public String test(ModelMap map){
//
//        map.put("name","tomcat");
//        map.put("name","tomcat");
//        return "test";
//    }
//
//    @GetMapping("stu")
//    public String stu(ModelMap map){
//        TestEntity entity = new TestEntity();
//        entity.setCode("007");
//        entity.setPass("9527");
//        entity.setAge(18);
//        entity.setLikeColor("<font color='red'>红色</font>");
//        map.put("stu",entity);
//        return "test";
//    }

    @GetMapping("list")
    public String list(ModelMap map){

        TestEntity s1=new TestEntity("001","111",18,"red");
        TestEntity s2=new TestEntity("002","222",19,"red");
        TestEntity s3=new TestEntity("003","333",16,"blue");
        TestEntity s4=new TestEntity("004","444",28,"blue");
        TestEntity s5=new TestEntity("005","555",68,"blue");
        TestEntity s6=new TestEntity("005","666",78,"blue");
        map.put("stuList", Arrays.asList(s1,s2,s3,s4,s5,s6));
        return "test";
    }
}
