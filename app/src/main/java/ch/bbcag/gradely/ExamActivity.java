package ch.bbcag.gradely;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import ch.bbcag.gradely.adapters.ExamListAdapter;
import ch.bbcag.gradely.databinding.ActivityExamBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.dao.SubjectDao;
import ch.bbcag.gradely.db.entity.Exam;

public class ExamActivity extends AppCompatActivity {
    private ExamActivityModel viewModel;
    private SemesterDao semesterDao;
    private SubjectDao subjectDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ExamActivityModel.class);

        ActivityExamBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_exam);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        int subjectId = intent.getIntExtra("subjectId", -1);
        String SubjectName = intent.getStringExtra("subjectName");

        viewModel.getSubjectName().setValue(SubjectName);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "gradelyDB")
                .build();

        AsyncTask.execute(() -> {
            viewModel.getExams().postValue(
                    AppDatabase.getInstance(getApplicationContext()).examDao().getExamsBySubject(subjectId)
            );
        });
        addExamToClickableList();

        FloatingActionButton addExamFAB = findViewById(R.id.floatingAddButton);
        addExamFAB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getApplicationContext(), AddExamActivity.class);
                        intent1.putExtra("subjectId", subjectId);
                        startActivity(intent1);
                    }
                }
        );

        ImageButton upButton = findViewById(R.id.backButton);
        upButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        int subjectId = intent.getIntExtra("subjectId", -1);

        AsyncTask.execute(() -> {
            viewModel.getExams().postValue(
                    AppDatabase.getInstance(getApplicationContext()).examDao().getExamsBySubject(subjectId)
            );
        });
    }

    private void addExamToClickableList() {
        ExamListAdapter examAdapter = new ExamListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView examList = findViewById(R.id.examList);
        examList.setLayoutManager(layoutManager);
        examList.setAdapter(examAdapter);

        viewModel.getExams().observe(this, exams -> {
            examAdapter.replaceData(exams);
        });
    }

}
