package ch.bbcag.gradely;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gradely.db.entity.Semester;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Semester>> semesters;

    public MutableLiveData<List<Semester>> getSemesters() {
        if (semesters == null) {
            semesters = new MutableLiveData<>(new ArrayList<>());
        }

        return semesters;
    }

}
