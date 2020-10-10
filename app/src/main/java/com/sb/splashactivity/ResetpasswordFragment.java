package com.sb.splashactivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetpasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetpasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetpasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetpasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetpasswordFragment newInstance(String param1, String param2) {
        ResetpasswordFragment fragment = new ResetpasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText registerEmail;
    private Button resetPassWordBtn;
    private TextView goBack;
    private FrameLayout parentFramelayout;
    private FirebaseAuth firebaseAuth;
    private ViewGroup emailIcContainer;
    private ImageView emailIC;
    private TextView emailIconText;
    private ProgressBar progressBar;


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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_resetpassword, container, false);
        registerEmail = view.findViewById(R.id.forgot_password_email);
        resetPassWordBtn = view.findViewById(R.id.reset_password_btn);
        goBack = view.findViewById(R.id.forgot_password_goback);

        emailIcContainer = view.findViewById(R.id.forgot_passW_icon_container);
        emailIC = view.findViewById(R.id.forgot_pasword_emailIC);
        emailIconText = view.findViewById(R.id.forgot_pasword_ic_text);
        progressBar = view.findViewById(R.id.forgot_passW_progressBar);

        parentFramelayout = getActivity().findViewById(R.id.register_framelayout);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        resetPassWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(emailIcContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIcContainer);
                emailIC.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetPassWordBtn.setEnabled(false);
                resetPassWordBtn.setTextColor(Color.argb(50f,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registerEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Email sent succesfully",Toast.LENGTH_SHORT).show();

                                }else{
                                    String error = task.getException().getMessage();

                                    resetPassWordBtn.setEnabled(true);
                                    resetPassWordBtn.setTextColor(Color.rgb(255,255,255));

                                    emailIconText.setText(error);
                                    emailIconText.setTextColor(getResources().getColor(R.color.colorRed));
                                    TransitionManager.beginDelayedTransition(emailIcContainer);
                                    emailIconText.setVisibility(View.VISIBLE);
                                }
                                progressBar.setVisibility(View.GONE);
                                ///???????????????/

                            }
                        });
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void checkInput() {
        if(TextUtils.isEmpty(registerEmail.getText())){
            resetPassWordBtn.setEnabled(false);
            resetPassWordBtn.setTextColor(Color.argb(50f,255,255,255));
        }else{
            resetPassWordBtn.setEnabled(true);
            resetPassWordBtn.setTextColor(Color.rgb(255,255,255));
        }
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}