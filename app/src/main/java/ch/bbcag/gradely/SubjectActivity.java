package ch.bbcag.gradely;

import static java.lang.Math.log;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.gradely.adapters.SubjectListAdapter;
import ch.bbcag.gradely.databinding.ActivityMainBinding;
import ch.bbcag.gradely.databinding.ActivitySubjectBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.dao.SubjectDao;
import ch.bbcag.gradely.db.entity.Subject;

public class SubjectActivity extends AppCompatActivity {
    private SubjectActivityModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = new ViewModelProvider(this).get(SubjectActivityModel.class);

        ActivitySubjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_subject);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);


        Intent intent = getIntent();
        int semesterId = intent.getIntExtra("semesterId", -1);
        String SemesterName = intent.getStringExtra("semesterName");

        viewModel.getSemesterName().setValue(SemesterName);

        AsyncTask.execute(() -> {
            viewModel.getSubjects().postValue(
                    AppDatabase.getInstance(getApplicationContext()).subjectDao().getSubjectBySemester(semesterId)
            );
        });


        FloatingActionButton addSubjectFAB = findViewById(R.id.floatingAddButton);
        addSubjectFAB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), Add_Subject_Activity.class);
                        i.putExtra("semesterId", semesterId);
                        startActivity(i);
                    }
                }
        );

        addSubjectToClickableList();

        ImageButton upButton = findViewById(R.id.backButton);
        upButton.setOnClickListener(view -> {
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        int semesterId = intent.getIntExtra("semesterId", -1);

        AsyncTask.execute(() -> {
            viewModel.getSubjects().postValue(
                    AppDatabase.getInstance(getApplicationContext()).subjectDao().getSubjectBySemester(semesterId)
            );
        });
    }

    private void addSubjectToClickableList() {
        SubjectListAdapter subjectAdapter = new SubjectListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView subjectList = findViewById(R.id.subjectList);
        subjectList.setLayoutManager(layoutManager);
        subjectList.setAdapter(subjectAdapter);

        viewModel.getSubjects().observe(this, subjects -> {
            subjectAdapter.replaceData(subjects);
        });

    }
}
