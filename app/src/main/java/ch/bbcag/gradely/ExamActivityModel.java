package ch.bbcag.gradely;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gradely.db.dao.ExamDao;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.dao.SubjectDao;
import ch.bbcag.gradely.db.entity.Exam;
import ch.bbcag.gradely.db.entity.Subject;

public class ExamActivityModel extends ViewModel {
    private MutableLiveData<String> subjectName;
    private MutableLiveData<List<Exam>> exams;


    public MutableLiveData<String> getSubjectName() {
        if (subjectName == null) {
            subjectName = new MutableLiveData<>("");
        }
        return subjectName;
    }

    public MutableLiveData<List<Exam>> getExams() {
        if (exams == null) {
            exams = new MutableLiveData<>(new ArrayList<>());
        }
        return exams;
    }
}
