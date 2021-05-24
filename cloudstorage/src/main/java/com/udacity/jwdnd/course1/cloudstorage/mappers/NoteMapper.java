package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{note.noteTitle}, #{note.noteDescription}, #{note.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer save(@Param("note") Notes note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Notes> findNotesByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteById(@Param("noteId") Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription}, userId = #{note.userId} WHERE noteid = #{note.noteId}")
    void update(@Param("note") Notes note);
}