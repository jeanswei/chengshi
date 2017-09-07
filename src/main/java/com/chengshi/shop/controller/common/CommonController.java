package com.chengshi.shop.controller.common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.chengshi.shop.util.MessageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;

/**
 * 公共方法
 * @author xuxinlong
 * @version 2017年09月06日
 */
@RestController
public class CommonController {
    @Value("${endpoint}")
    private String endpoint;
    @Value("${accessKeyId}")
    private String accessKeyId;
    @Value("${accessKeySecret}")
    private String accessKeySecret;
    @Value("${bucketName}")
    private String bucketName;

    @GetMapping(value = "getOssPolicy")
    public HashMap<String, Object> getOssPolicy() {
        HashMap<String, Object> retMap = MessageUtils.success();

        String host = "http://" + bucketName+ "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            long expireTime = 60 * 5;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "images/");

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            retMap.put("accessid", accessKeyId);
            retMap.put("policy", encodedPolicy);
            retMap.put("signature", postSignature);
            retMap.put("dir", "images/");
            retMap.put("host", host);
            retMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
