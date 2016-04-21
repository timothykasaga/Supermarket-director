package com.victoria.timothykasaga.gorret;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
       TextView blogin,bcreate,bforgot;
       View view;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);
        initialize(view);
        // Inflate the layout for this fragment
        return view;
    }


    public void initialize(View view){
        blogin = (TextView) view.findViewById(R.id.btnLogin);
        bcreate = (TextView) view.findViewById(R.id.btnCreate);
        bforgot = (TextView) view.findViewById(R.id.btnfgPass);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Add_supermarket.class);
                startActivity(intent);
            }
        });

        bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Create_account.class);
                startActivity(intent);
            }
        });

        bforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new Reset());
                fragmentTransaction.commit();
            }
        });
    }
}
