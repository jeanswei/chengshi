package com.chengshi.shop.service.picture.impl;

import com.chengshi.shop.dao.picture.AlbumFolderMapper;
import com.chengshi.shop.dao.picture.AlbumPictureMapper;
import com.chengshi.shop.model.picture.AlbumFolder;
import com.chengshi.shop.model.picture.AlbumPicture;
import com.chengshi.shop.service.picture.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 图片接口
 *
 * @author xuxinlong
 * @version 2017年09月06日
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    private AlbumFolderMapper albumFolderMapper;
    @Resource
    private AlbumPictureMapper albumPictureMapper;

    /**
     * 获取相册文件夹
     *
     * @return
     */
    @Override
    public List<AlbumFolder> getFolderList() {
        return albumFolderMapper.getList();
    }

    /**
     * 保存文件夹
     *
     * @param albumFolder
     */
    @Override
    public void saveFolder(AlbumFolder albumFolder) {
        if (albumFolder.getAlbumId() != null) {
            albumFolderMapper.updateByPrimaryKeySelective(albumFolder);
        } else {
            albumFolder.setCreateTime(new Date());
            albumFolderMapper.insertSelective(albumFolder);
        }
    }

    /**
     * 保存图片列表
     *
     * @param pictureList
     */
    @Override
    @Transactional
    public void savePicture(List<AlbumPicture> pictureList) {
        for (AlbumPicture picture : pictureList){
            picture.setUploadTime(new Date());
            albumPictureMapper.insertSelective(picture);
            albumFolderMapper.updatePicNum(picture.getAlbumId(), 1);
        }
    }

    /**
     * 获取相册里的图片
     *
     * @param albumId
     * @return
     */
    @Override
    public List<AlbumPicture> getPictureList(Integer albumId) {
        return albumPictureMapper.getPictureList(albumId);
    }
}
