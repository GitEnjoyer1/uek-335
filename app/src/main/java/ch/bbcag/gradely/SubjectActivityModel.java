package ch.bbcag.gradely;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import ch.bbcag.gradely.db.dao.ExamDao;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.dao.SubjectDao;
import ch.bbcag.gradely.db.entity.Semester;
import ch.bbcag.gradely.db.entity.Subject;

public class SubjectActivityModel extends ViewModel{
    private MutableLiveData<String> semesterName;
    private MutableLiveData<List<Subject>> subjects;

    public MutableLiveData<String> getSemesterName() {
        if (semesterName == null) {
            semesterName = new MutableLiveData<>("");
        }
        return semesterName;
    }

    public MutableLiveData<List<Subject>> getSubjects() {
        if (subjects == null) {
            subjects = new MutableLiveData<>(new ArrayList<>());
        }
        return subjects;
    }
}
