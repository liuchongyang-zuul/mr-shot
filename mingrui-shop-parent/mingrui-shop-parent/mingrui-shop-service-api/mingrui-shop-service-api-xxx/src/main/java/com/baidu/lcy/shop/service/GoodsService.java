package com.baidu.lcy.shop.service;

import com.baidu.lcy.shop.base.Result;
import com.baidu.lcy.shop.dto.SpuDTO;
import com.baidu.lcy.shop.entity.SpuEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "商品接口")
public interface GoodsService {

    @GetMapping(value = "spu/list")
    @ApiOperation(value = "查询")
    Result<PageInfo<SpuEntity>> list(SpuDTO spuDTO);
}
