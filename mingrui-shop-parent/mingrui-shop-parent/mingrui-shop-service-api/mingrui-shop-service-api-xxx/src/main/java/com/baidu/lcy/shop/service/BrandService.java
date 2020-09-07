package com.baidu.lcy.shop.service;

import com.baidu.lcy.shop.base.Result;
import com.baidu.lcy.shop.dto.BrandDTO;
import com.baidu.lcy.shop.entity.BrandEntity;
import com.baidu.lcy.shop.validate.group.MingRuiOperation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {
    @ApiOperation(value="查询")
    @GetMapping(value = "brend/list")
    public Result<PageInfo<BrandEntity>> getBrandList(BrandDTO brandDTO);

    @ApiOperation(value="增加")
    @PostMapping(value = "brend/saveBrand")
    public Result<PageInfo<BrandEntity>> saveBrand(@Validated ({MingRuiOperation.Add.class}) @RequestBody BrandDTO brandDTO);

    @ApiOperation(value="修改")
    @PutMapping(value = "brend/saveBrand")
    public Result<PageInfo<BrandEntity>> editBrand(@Validated ({MingRuiOperation.Update.class}) @RequestBody BrandDTO brandDTO);

    @ApiOperation(value = "刪除")
    @DeleteMapping(value = "brend/deleteBrand")
    public Result<PageInfo<BrandEntity>> deleteBrand(Integer id);
}
