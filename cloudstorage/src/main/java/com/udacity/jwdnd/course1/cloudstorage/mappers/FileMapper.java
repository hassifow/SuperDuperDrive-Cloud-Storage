package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES " +
            "(#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.userId}, #{file.fileData})")
    Integer save(@Param("file") Files file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<Files> findFilesByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void deleteById(@Param("fileid") Integer fileid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files findById(@Param("fileId") Integer fileId);

}