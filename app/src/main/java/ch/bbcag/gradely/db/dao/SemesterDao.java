package ch.bbcag.gradely.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.bbcag.gradely.db.entity.Semester;
import ch.bbcag.gradely.db.entity.Subject;

@Dao
public interface SemesterDao {
    @Query("SELECT * FROM semester")
    List<Semester> getAll();

    @Query("SELECT * FROM semester WHERE uid IN (:semesterIds)")
    List<Semester> loadAllByIds(int[] semesterIds);

    @Query("SELECT * FROM semester WHERE uid IN (:semesterId)")
    Semester getSemesterById(int semesterId);

    @Insert
    void insertAll(Semester... semesters);

    @Delete
    void delete(Semester semester);
}
