package com.chengshi.shop.controller.admin.index;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.index.IndexAd;
import com.chengshi.shop.model.index.IndexFloor;
import com.chengshi.shop.service.index.IndexService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 首页页面管理
 *
 * @author xuxinlong
 * @version 2017年09月04日
 */
@RestController
@RequestMapping(value = "admin/index")
public class IndexController extends BaseController {
    @Resource
    private IndexService indexService;

    /**
     * 获取广告列表
     *
     * @return
     */
    @GetMapping(value = "getAdList")
    public PageInfo<IndexAd> getGoodsList() {
        this.startPage();
        List<IndexAd> adList = indexService.getIndexAdList();
        return new PageInfo<>(adList);
    }

    /**
     * 广告列表页面
     *
     * @return
     */
    @GetMapping(value = "adList")
    public ModelAndView adList() {
        return new ModelAndView("admin/index/adList");
    }

    /**
     * 广告编辑页面
     *
     * @return
     */
    @GetMapping(value = "adForm")
    public ModelAndView adForm(@RequestParam(required = false) Integer adId) {
        ModelAndView mav = new ModelAndView("admin/index/adForm");
        IndexAd ad = new IndexAd();
        if (adId != null) {
            ad = indexService.getIndexAdByAdId(adId);
        }
        mav.addObject("ad", ad);
        return mav;
    }

    /**
     * 删除广告
     *
     * @param adId
     * @return
     */
    @PostMapping(value = "deleteAd")
    public HashMap<String, Object> deleteGoods(@RequestParam Integer adId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            indexService.deleteIndexAd(adId);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 保存广告信息
     *
     * @param indexAd
     * @return
     */
    @PostMapping(value = "saveAd")
    public HashMap<String, Object> saveAd(@ModelAttribute IndexAd indexAd) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            indexService.saveIndexAd(indexAd);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取楼层列表
     *
     * @return
     */
    @GetMapping(value = "getFloorList")
    public PageInfo<IndexFloor> getFloorList() {
        this.startPage();
        List<IndexFloor> floorList = indexService.getFloorList();
        return new PageInfo<>(floorList);
    }

    /**
     * 广告列表页面
     *
     * @return
     */
    @GetMapping(value = "floorList")
    public ModelAndView floorList() {
        return new ModelAndView("admin/index/floorList");
    }

    /**
     * 广告编辑页面
     *
     * @return
     */
    @GetMapping(value = "floorForm")
    public ModelAndView floorForm(@RequestParam(required = false) Integer floorId) {
        ModelAndView mav = new ModelAndView("admin/index/floorForm");
        IndexFloor floor = new IndexFloor();
        if (floorId != null) {
            floor = indexService.getIndexFloorByFloorId(floorId);
        }
        mav.addObject("floor", floor);
        return mav;
    }

    /**
     * 删除楼层
     *
     * @param floorId
     * @return
     */
    @PostMapping(value = "deleteFloor")
    public HashMap<String, Object> deleteFloor(@RequestParam Integer floorId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            indexService.deleteIndexFloor(floorId);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 保存楼层信息
     *
     * @param indexFloor
     * @return
     */
    @PostMapping(value = "saveFloor")
    public HashMap<String, Object> saveFloor(@ModelAttribute IndexFloor indexFloor) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            indexService.saveIndexFloor(indexFloor);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

}
