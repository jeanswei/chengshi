package com.chengshi.shop.controller.mobile.index;

import com.chengshi.shop.model.index.IndexAd;
import com.chengshi.shop.service.index.IndexService;
import com.chengshi.shop.util.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 商城首页相关
 * @author xuxinlong
 * @version 2017年10月11日
 */
@Api(value = "商城首页相关接口")
@RestController
@RequestMapping("/mobile/index")
public class MobileIndexController {
    @Resource
    private IndexService indexService;

    /**
     * 获取首页广告brand图
     * @return
     */
    @ApiOperation(value = "获取首页广告图列表")
    @GetMapping("/getAdList")
    public HashMap<String, Object> getAdList(){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            List<IndexAd> adList = indexService.getValidAdList();
            retMap.put("adList", adList);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }

//    /**
//     * 获取首页楼层列表
//     * @return
//     */
//    @ApiOperation(value = "获取首页楼层列表")
//    @GetMapping("/getFloorList")
//    public HashMap<String, Object> getFloorList(){
//        HashMap<String, Object> retMap = MessageUtils.success();
//        try {
//            List<IndexFloor> floorList = indexService.getValidFloorList();
//            retMap.put("floorList", floorList);
//        } catch (Exception e){
//            retMap = MessageUtils.error();
//        }
//        return retMap;
//    }
}
