package ch.bbcag.gradely.adapters;

import android.content.Context;
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

import ch.bbcag.gradely.R;
import ch.bbcag.gradely.SubjectActivity;
import ch.bbcag.gradely.db.AppDatabase;
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.entity.Semester;

public class SemesterListAdapter extends RecyclerView.Adapter<SemesterListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private final TextView semesterNameTextView;
        private final TextView averageGradeTextView;

        public ViewHolder(View view) {
            super(view);

            semesterNameTextView = view.findViewById(R.id.semesterNameTextView);
            averageGradeTextView = view.findViewById(R.id.averageGradeTextView);
            this.view = view;
        }

        public void setSemester(Semester semester) {
            semesterNameTextView.setText(semester.name);
            averageGradeTextView.setText("6.0");

            view.setOnLongClickListener(v -> {
                AsyncTask.execute(() -> {
                    AppDatabase.getInstance(view.getContext()).semesterDao().delete(semester);
                });
                return true;
            });

            view.setOnClickListener(v -> {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
                intent.putExtra("semesterId", semester.getUid());
                intent.putExtra("semesterName", semester.getName());
                view.getContext().startActivity(intent);
            });
        }
    }

    private List<Semester> data = new ArrayList<>();

    @NonNull
    @Override
    public SemesterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.semester_list_item, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SemesterListAdapter.ViewHolder holder, int position) {
        holder.setSemester(data.get(position));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replaceData(List<Semester> semesters) {
        data = semesters;
        notifyDataSetChanged();
    }
}