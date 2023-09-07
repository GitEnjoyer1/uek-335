package ch.bbcag.gradely;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import ch.bbcag.gradely.databinding.ActivityAddSemesterBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.entity.Semester;

public class AddSemesterActivity extends AppCompatActivity {

    private AddSemesterViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddSemesterViewModel.class);

        ActivityAddSemesterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_semester);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        EditText outlinedTextField = findViewById(R.id.outlinedTextField);
        Button createSemesterButton = findViewById(R.id.createSemesterButton);

        createSemesterButton.setOnClickListener(view -> {
            Semester s = new Semester();
            s.name = outlinedTextField.getText().toString().trim();
            if (s.name.isEmpty()){
                outlinedTextField.setError("Titel kann nicht leer sein!");
                return;
            }
            AsyncTask.execute(() -> {
                AppDatabase.getInstance(this).semesterDao().insertAll(s);
                finish();
            });
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            finish();
        });
    }
}