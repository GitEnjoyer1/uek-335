package ch.bbcag.gradely.adapters;

import android.content.Intent;
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
import ch.bbcag.gradely.db.dao.SemesterDao;
import ch.bbcag.gradely.db.entity.Semester;
import ch.bbcag.gradely.db.entity.Subject;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private final TextView subjectNameTextView;
        private final TextView averageGradeTextView;

        public ViewHolder(View view) {
            super(view);

            subjectNameTextView = view.findViewById(R.id.subjectNameTextView);
            averageGradeTextView = view.findViewById(R.id.averageGradeTextView);
            this.view = view;
        }

        public void setSubject(Subject subject) {
            subjectNameTextView.setText(subject.name);
            averageGradeTextView.setText("6.0");

            view.setOnClickListener(v -> {
                Intent intent = new Intent(view.getContext(), ExamActivity.class);
                intent.putExtra("subjectId", subject.getUid());
                intent.putExtra("subjectName", subject.getName());
                view.getContext().startActivity(intent);
            });
        }
    }

    private List<Subject> data = new ArrayList<>();

    @NonNull
    @Override
    public SubjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.subject_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.ViewHolder holder, int position) {
        holder.setSubject(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replaceData(List<Subject> subjects) {
        data = subjects;
        notifyDataSetChanged();
    }
}
