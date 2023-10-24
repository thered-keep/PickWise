package com.example.unipath1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static Intent intent ;
    public static int student_id_static ;
    TextView completed_subjects , in_progress_subjects ;
    TextView nameView;
    RecyclerView subjectRecyclerView;
    DataBaseHelper dataBaseHelper;
    ChipGroup chipGroup;
    Chip chip1 ,chip2,chip3,chip4,chip5;


    int current_semester;
    String student_id, student_name;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment
     *
     * .
     */


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<Subject> mySubjects = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent =getActivity().getIntent();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // database
        dataBaseHelper = new DataBaseHelper(this.getContext());
        chipGroup =view.findViewById(R.id.chipGroup);
        subjectRecyclerView = view.findViewById(R.id.subjectsRv);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        // getting the subjectlist
        Intent intent = getActivity().getIntent();
        current_semester = intent.getIntExtra("semester_id", 1);
        student_id = intent.getStringExtra("student_id");
        student_name = dataBaseHelper.getStudentName(student_id);

        nameView = view.findViewById(R.id.name_view);
        nameView.setText("Hello " + student_name);

        completed_subjects =view.findViewById(R.id.completed_subjects_view);
        in_progress_subjects=view.findViewById(R.id.in_progress_subjects);

        mySubjects = dataBaseHelper.getSubjects(current_semester);
        subjectAdapter adapter = new subjectAdapter(mySubjects);
        adapter.setStudent_id(Integer.parseInt(student_id));
        subjectRecyclerView.setAdapter(adapter);

        chip1=view.findViewById(R.id.chip1);
        chip2=view.findViewById(R.id.chip2);
        chip3=view.findViewById(R.id.chip3);
        chip4=view.findViewById(R.id.chip4);
        chip5=view.findViewById(R.id.chip5);

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    int sem_id = Integer.parseInt(buttonView.getText().toString());
                    mySubjects = dataBaseHelper.getSubjects(sem_id);
                    subjectAdapter adapter = new subjectAdapter(mySubjects);
                    adapter.setStudent_id(Integer.parseInt(student_id));
                    subjectRecyclerView.setAdapter(adapter);
                }
            }
        };
        chip1.setOnCheckedChangeListener(checkedChangeListener);
        chip2.setOnCheckedChangeListener(checkedChangeListener);
        chip3.setOnCheckedChangeListener(checkedChangeListener);
        chip4.setOnCheckedChangeListener(checkedChangeListener);
        chip5.setOnCheckedChangeListener(checkedChangeListener);




        int number_of_completed_subjects =dataBaseHelper.getNumberOfCompletedSubjects(current_semester, Integer.parseInt(student_id));
        int number_in_progress_subjects =dataBaseHelper.getNumberInProgressSubjects(current_semester, Integer.parseInt(student_id));
        completed_subjects.setText(String.valueOf(number_of_completed_subjects));
        in_progress_subjects.setText(String.valueOf(number_in_progress_subjects));

    }



}

