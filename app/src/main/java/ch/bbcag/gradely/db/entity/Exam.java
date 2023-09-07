package ch.bbcag.gradely.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Exam {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDoesGradeCount() {
        return doesGradeCount;
    }

    public void setDoesGradeCount(boolean doesGradeCount) {
        this.doesGradeCount = doesGradeCount;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @ColumnInfo(name = "grade")
    public double grade;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "doesGradeCount")
    public boolean doesGradeCount;

    @ColumnInfo(name = "subjectId")
    public int subjectId;
}
