package com.example.unipath1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CircleImageView img;
    TextView name_view , uni_view , age_view , address_view , nationality_view ,deg_view;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        String s= getArguments().getString("student_id");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getContext());
        Student student = dataBaseHelper.getStudent(Integer.parseInt(s));

        name_view = view.findViewById(R.id.profileName);
        img=view.findViewById(R.id.prof_img);
        uni_view=view.findViewById(R.id.universityinput);
        age_view=view.findViewById(R.id.name_view);
        address_view=view.findViewById(R.id.adressinput);
        nationality_view=view.findViewById(R.id.nationalityInput);
        deg_view=view.findViewById(R.id.degreeinput);


        if (student.getImg() == null)
            img.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(this).asBitmap().load(student.getImg()).into(img);
        }        // Inflate the layout for this fragment

        name_view.setText(student.getName());
        String age = String.valueOf(student.getAge());
        age_view.setText(age);
        uni_view.setText(student.getUni());
        address_view.setText(student.getAddress());
        nationality_view.setText(student.getNationality());
        deg_view.setText(student.getDegree());
        return view;
    }

}