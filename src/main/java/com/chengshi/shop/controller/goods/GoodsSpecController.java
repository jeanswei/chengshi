package com.chengshi.shop.controller.goods;

import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.model.goods.GoodsSpecValue;
import com.chengshi.shop.service.goods.GoodsSpecService;
import com.chengshi.shop.util.MessageUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 商品规格
 * @author xuxinlong
 * @version 2017年09月22日
 */
@RestController
@RequestMapping(value = "admin")
public class GoodsSpecController {
    @Resource
    private GoodsSpecService goodsSpecService;

    /**
     * 获取规格列表
     * @return
     */
    @GetMapping(value = "getSpecList")
    public List<GoodsSpec> getSpecList(){
        return goodsSpecService.getSpecList();
    }

    /**
     * 获取规格下的规格值列表
     * @param specId
     * @return
     */
    @GetMapping(value = "getSpecValueList")
    public List<GoodsSpecValue> getSpecValueList(@RequestParam Integer specId){
        return goodsSpecService.getSpecValueList(specId);
    }

    /**
     * 新建规格
     * @param goodsSpec
     * @return
     */
    @PostMapping(value = "saveSpec")
    public HashMap<String, Object> saveSpec(@ModelAttribute GoodsSpec goodsSpec){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            if (goodsSpecService.checkSpecName(goodsSpec.getSpecName())){
                goodsSpecService.saveSpec(goodsSpec);
            } else {
                retMap = MessageUtils.error("该规格已存在");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 保存规格值
     * @param goodsSpecValue
     * @return
     */
    @PostMapping(value = "saveSpecValue")
    public HashMap<String, Object> saveSpecValue(@ModelAttribute GoodsSpecValue goodsSpecValue){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            if (goodsSpecService.checkSpecValue(goodsSpecValue.getSpecId(), goodsSpecValue.getSpecValue())){
                goodsSpecService.saveSpecValue(goodsSpecValue);
            } else {
                retMap = MessageUtils.error("该规格值已存在");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
