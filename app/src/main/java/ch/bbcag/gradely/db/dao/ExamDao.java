package ch.bbcag.gradely.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.bbcag.gradely.db.entity.Exam;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exam")
    List<Exam> getAll();

    @Query("SELECT * FROM exam WHERE uid IN (:examIds)")
    List<Exam> loadAllByIds(int[] examIds);
    @Query("SELECT * FROM exam WHERE subjectId IN (:Id)")
    List<Exam> getExamsBySubject(int Id);

    @Insert
    void insertAll(Exam... exams);

    @Delete
    void delete(Exam exam);
}
