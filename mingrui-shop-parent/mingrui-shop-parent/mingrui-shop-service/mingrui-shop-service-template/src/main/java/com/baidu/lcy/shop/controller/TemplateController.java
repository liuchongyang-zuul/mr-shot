package com.baidu.lcy.shop.controller;

import com.baidu.lcy.shop.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName TemplateController
 * @Description: TODO
 * @Author liuchongyang
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Controller
@RequestMapping(value = "item")
public class TemplateController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private TemplateService templateService;

    @GetMapping(value = "{spuId}.html")
    public String test(@PathVariable(value = "spuId") Integer spuId, ModelMap modelMap,HttpServletRequest httpServletRequest){

        Map<String, Object> map = templateService.getGoodsInfo(spuId);

        modelMap.putAll(map);
        return "item";
    }
}
