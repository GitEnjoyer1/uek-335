package ch.bbcag.gradely;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddExamViewModel extends ViewModel {
    private MutableLiveData<Integer> subjectId;

    public MutableLiveData<Integer> getSubjectId(){
        if(subjectId == null) {
            subjectId = new MutableLiveData<>(-1);
        }
        return subjectId;
    }
}
