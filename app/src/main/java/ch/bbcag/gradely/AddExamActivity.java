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
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.bbcag.gradely.databinding.ActivityAddExamBinding;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.entity.Exam;

public class AddExamActivity extends AppCompatActivity {

    private AddExamViewModel viewModel;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddExamViewModel.class);

        ActivityAddExamBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_exam);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        EditText examTitleTextField = findViewById(R.id.examTitleTextField);
        EditText examGradeTextField = findViewById(R.id.examGradeTextField);
        Button pickDateButton = findViewById(R.id.pick_date_button);
        MaterialSwitch gradeCountsSwitch = findViewById(R.id.gradeCountsSwitch);
        TextView selectedDateTextView = findViewById(R.id.show_selected_date);
        Button createExamButton = findViewById(R.id.createExamButton);

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Datum wählen")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        date = new Date(selection);
                        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN).format(date);
//                        selectedDateTextView.setText(MessageFormat.format("Datum:  {0}", date));
                        selectedDateTextView.setText(" " + dateString);
//                        date1 = MessageFormat.format("{0}", date);
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });


        createExamButton.setOnClickListener(view -> {
            Exam e = new Exam();
            e.name = examTitleTextField.getText().toString().trim();
            if (e.name.isEmpty()){
                examTitleTextField.setError("Titel kann nicht leer sein!");
                return;
            }

            String inputExamGradeText = examGradeTextField.getText().toString().trim();
            if (!inputExamGradeText.isEmpty()){
                e.grade = Double.parseDouble(inputExamGradeText);
            }
            else {
                examGradeTextField.setError("Note kann nicht leer sein!");
                return;
            }

            if (date == null){
                selectedDateTextView.setError("Datum kann nicht leer sein!");
                return;
            }
            else {
                e.date = date;
            }

            e.doesGradeCount = gradeCountsSwitch.isChecked();

            Intent intent1 = getIntent();
            int SubjectId = intent1.getIntExtra("subjectId", -1);

            e.subjectId = SubjectId;
            viewModel.getSubjectId().setValue(SubjectId);

            AsyncTask.execute(() -> {
                AppDatabase.getInstance(this).examDao().insertAll(e);
                finish();
            });
        });



        ImageButton upButton = findViewById(R.id.backButton);

        upButton.setOnClickListener(view -> {
            finish();
        });

    }

}