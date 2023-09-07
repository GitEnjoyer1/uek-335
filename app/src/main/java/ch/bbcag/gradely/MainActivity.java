package ch.bbcag.gradely;

import static android.graphics.Insets.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.time.LocalDate;

import ch.bbcag.gradely.adapters.SemesterListAdapter;
import ch.bbcag.gradely.databinding.ActivityMainBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.entity.Exam;
import ch.bbcag.gradely.db.entity.Semester;
import ch.bbcag.gradely.db.entity.Subject;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private SemesterDao semesterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "gradelyDB")
                .build();


        FloatingActionButton floatingAddButton = findViewById(R.id.floatingAddButton);
        floatingAddButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, AddSemesterActivity.class);
                        startActivity(i);
                    }
                }
        );

        addSemesterToClickableList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncTask.execute(() -> {
            viewModel.getSemesters().postValue(
                    AppDatabase.getInstance(getApplicationContext()).semesterDao().getAll()
            );
        });
    }

    private void addSemesterToClickableList() {
        SemesterListAdapter semesterAdapter = new SemesterListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        RecyclerView semesterList = findViewById(R.id.semesterList);
        semesterList.setLayoutManager(layoutManager);
        semesterList.setAdapter(semesterAdapter);
        

        viewModel.getSemesters().observe(this, semesters -> {
            semesterAdapter.replaceData(semesters);
        });

    }
}
