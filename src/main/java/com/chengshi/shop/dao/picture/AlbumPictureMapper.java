package com.chengshi.shop.dao.picture;

import com.chengshi.shop.model.picture.AlbumPicture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumPictureMapper {
    int deleteByPrimaryKey(Integer picId);

    int insert(AlbumPicture record);

    int insertSelective(AlbumPicture record);

    AlbumPicture selectByPrimaryKey(Integer picId);

    int updateByPrimaryKeySelective(AlbumPicture record);

    int updateByPrimaryKey(AlbumPicture record);

    /**
     * 获取相册下的图片
     * @param albumId
     * @return
     */
    List<AlbumPicture> getPictureList(Integer albumId);
}