package ch.bbcag.gradely.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.bbcag.gradely.db.entity.Subject;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM subject")
    List<Subject> getAll();

    @Query("SELECT * FROM subject WHERE uid IN (:subjectIds)")
    List<Subject> loadAllByIds(int[] subjectIds);

    @Query("SELECT * FROM subject WHERE semesterId IN (:Id)")
    List<Subject> getSubjectBySemester(int Id);

    @Insert
    void insertAll(Subject... subjects);

    @Delete
    void delete(Subject subject);
}
