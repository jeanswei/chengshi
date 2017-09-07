package com.chengshi.shop.service.picture;

import com.chengshi.shop.model.picture.AlbumFolder;
import com.chengshi.shop.model.picture.AlbumPicture;

import java.util.List;

/**
 * 图片接口
 * @author xuxinlong
 * @version 2017年09月06日
 */
public interface PictureService {

    /**
     * 获取相册文件夹
     * @return
     */
    List<AlbumFolder> getFolderList();

    /**
     * 保存文件夹
     * @param albumFolder
     */
    void saveFolder(AlbumFolder albumFolder);

    /**
     * 保存图片列表
     * @param pictureList
     */
    void savePicture(List<AlbumPicture> pictureList);

    /**
     * 获取相册里的图片
     * @param albumId
     * @return
     */
    List<AlbumPicture> getPictureList(Integer albumId);
}
