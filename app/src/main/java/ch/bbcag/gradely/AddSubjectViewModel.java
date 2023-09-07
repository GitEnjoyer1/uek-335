package ch.bbcag.gradely;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class AddSubjectViewModel extends ViewModel {
    private MutableLiveData<Integer> semesterId;

    public MutableLiveData<Integer> getSemesterId() {
        if(semesterId == null) {
            semesterId = new MutableLiveData<>(-1);
        }
        return semesterId;
    }

}
