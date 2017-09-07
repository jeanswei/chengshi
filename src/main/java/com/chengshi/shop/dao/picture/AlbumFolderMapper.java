package com.chengshi.shop.dao.picture;

import com.chengshi.shop.model.picture.AlbumFolder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumFolderMapper {
    int deleteByPrimaryKey(Integer albumId);

    int insert(AlbumFolder record);

    int insertSelective(AlbumFolder record);

    AlbumFolder selectByPrimaryKey(Integer albumId);

    int updateByPrimaryKeySelective(AlbumFolder record);

    int updateByPrimaryKey(AlbumFolder record);

    /**
     * 获取相册文件夹列表
     * @return
     */
    List<AlbumFolder> getList();

    /**
     * 更新相册图片数量
     * @param albumId
     * @param picNum
     */
    void updatePicNum(@Param("albumId") Integer albumId,@Param("picNum") Integer picNum);
}