package ch.bbcag.gradely;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import ch.bbcag.gradely.databinding.ActivityAddSemesterBinding;
import ch.bbcag.gradely.databinding.ActivityAddSubjectBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.entity.Subject;

public class Add_Subject_Activity extends AppCompatActivity {

    private AddSubjectViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddSubjectViewModel.class);

        ActivityAddSubjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_subject);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        EditText outlinedTextField = findViewById(R.id.outlinedTextField);
        Button createSubjectButton = findViewById(R.id.createExamButton);

        createSubjectButton.setOnClickListener(view -> {
            Subject s = new Subject();
            s.name = outlinedTextField.getText().toString().trim();
            if (s.name.isEmpty()) {
                outlinedTextField.setError("Titel kann nicht leer sein!");
                return;
            }

            Intent intent = getIntent();
            int SemesterId = intent.getIntExtra("semesterId", -1);

            s.semesterId = SemesterId;
            viewModel.getSemesterId().setValue(SemesterId);

            AsyncTask.execute(() -> {
                AppDatabase.getInstance(this).subjectDao().insertAll(s);
                finish();
            });
        });

        ImageButton upButton = findViewById(R.id.backButton);
        upButton.setOnClickListener(view -> {
            finish();
        });
    }


}