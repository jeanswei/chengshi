package com.chengshi.shop.controller.admin.picture;

import com.alibaba.fastjson.JSON;
import com.chengshi.shop.model.picture.AlbumFolder;
import com.chengshi.shop.model.picture.AlbumPicture;
import com.chengshi.shop.service.picture.PictureService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * 图片相关
 *
 * @author xuxinlong
 * @version 2017年09月06日
 */
@RestController
@RequestMapping(value = "admin")
public class PictureController {
    @Resource
    private PictureService pictureService;

    @Value("${img_url}")
    private String IMAGEURL;
    @Value("${SMALL}")
    private String SMALLIMG;

    /**
     * 图片空间
     *
     * @return
     */
    @GetMapping(value = "pictureSpace")
    public ModelAndView pictureSpace() {
        ModelAndView mav = new ModelAndView("admin/picture/pictureSpace");
        List<AlbumFolder> folderList = pictureService.getFolderList();
        mav.addObject("folderList", folderList);
        return mav;
    }

    /**
     * 新建文件夹页面
     *
     * @return
     */
    @GetMapping(value = "folderForm")
    public ModelAndView folderForm() {
        return new ModelAndView("admin/picture/folderForm");
    }

    /**
     * 保存文件夹
     *
     * @param albumFolder
     * @return
     */
    @PostMapping(value = "saveFolder")
    public HashMap<String, Object> saveFolder(@ModelAttribute AlbumFolder albumFolder) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            pictureService.saveFolder(albumFolder);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 保存图片
     *
     * @param request
     * @return
     */
    @PostMapping(value = "savePicture")
    public HashMap<String, Object> savePicture(HttpServletRequest request) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            String fileData = request.getParameter("fileData");
            List<AlbumPicture> pictureList = JSON.parseArray(fileData, AlbumPicture.class);
            if (pictureList.isEmpty()) {
                return MessageUtils.error("请选择图片上传");
            }
            pictureService.savePicture(pictureList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取图片列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "getPictureList")
    public PageInfo<AlbumPicture> getPictureList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam Integer albumId) {
        PageHelper.startPage(pageNumber, pageSize);
        List<AlbumPicture> pictureList = pictureService.getPictureList(albumId);
        for (AlbumPicture picture : pictureList) {
            picture.setThumbnail(IMAGEURL + picture.getPicUrl() + SMALLIMG);
        }
        return new PageInfo<>(pictureList);
    }
}
