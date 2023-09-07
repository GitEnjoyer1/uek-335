package ch.bbcag.gradely.adapters;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gradely.ExamActivity;
import ch.bbcag.gradely.R;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.entity.Exam;
import ch.bbcag.gradely.db.entity.Subject;

public class ExamListAdapter  extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private final TextView examNameTextView;
        private final TextView averageGradeTextView;
        private List<Exam> data = new ArrayList<>();

        public ViewHolder(View view) {
            super(view);

            examNameTextView = view.findViewById(R.id.examNameTextView);
            averageGradeTextView = view.findViewById(R.id.averageGradeTextView);
            this.view = view;
        }

        public void setExam(Exam exam) {
            examNameTextView.setText(exam.name);
            averageGradeTextView.setText(exam.grade+"");

            view.setOnLongClickListener(v -> {
                AsyncTask.execute(() -> {
                    AppDatabase.getInstance(view.getContext()).examDao().delete(exam);
                });
                return true;
            });
        }

//        has leider nid häreübercho (ke zyt me gha)
//        public void updateAverage(TextView textView){
//            double totalGrade = 0;
//            for (Exam exam : data) {
//                totalGrade += exam.getGrade();
//            }
//            double averageGrade = totalGrade / data.size();
//            averageGradeTextView.setText((int) averageGrade);
//        }
    }

    private List<Exam> data = new ArrayList<>();

    @NonNull
    @Override
    public ExamListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exam_list_item, viewGroup, false);

        return new ExamListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamListAdapter.ViewHolder holder, int position) {
        holder.setExam(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replaceData(List<Exam> exams) {
        data = exams;
        notifyDataSetChanged();
    }


}
