package ch.bbcag.gradely.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.sql.Date;

import ch.bbcag.gradely.db.dao.ExamDao;
import ch.bbcag.gradely.db.converters.Converters;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.dao.SubjectDao;
import ch.bbcag.gradely.db.entity.Exam;
import ch.bbcag.gradely.db.entity.Semester;
import ch.bbcag.gradely.db.entity.Subject;

@Database(entities = {Subject.class, Exam.class, Semester.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract SubjectDao subjectDao();

    public abstract ExamDao examDao();

    public abstract SemesterDao semesterDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "gradelyDB")
                    .build();
        }

        return instance;
    }

    public void seedTestdata() {
        AsyncTask.execute(() -> {
            Exam e = new Exam();
            e.name = "Test";
            e.date = new Date(2022,1,1);

            Subject s = new Subject();
            s.name = "Test";

            Semester se = new Semester();
            se.name = "Test";

            this.examDao().insertAll(e);
            this.subjectDao().insertAll(s);
            this.semesterDao().insertAll(se);
        });
    }
}
