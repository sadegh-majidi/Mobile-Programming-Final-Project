package edu.sharif.snappfoodminus.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import edu.sharif.snappfoodminus.R;

public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText usernameLoginText = view.findViewById(R.id.usernameLoginText);
        EditText passwordLoginText = view.findViewById(R.id.passwordLoginText);

        Button loginBtn = view.findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLoginText.getText().toString();
                String password = passwordLoginText.getText().toString();
                String errorMessage = getErrorMessage(username, password);
                if (errorMessage != null) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });

        TextView registerLink = view.findViewById(R.id.registerLinkText);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new RegisterFragment()).commit();

            }
        });
    }


    public String getErrorMessage(String username, String password) {

        if (username.isEmpty() || password.isEmpty())
            return "Both fields are required and cannot be empty.";
        else if (username.length() < 5)
            return "Username must be at least 5 characters long.";
        else if (password.length() < 5)
            return "Password must be at least 5 characters long.";
        else if (!Pattern.matches("^[a-zA-Z0-9]+$", username)) {
            return "Only letters and numbers are allowed in your username.";
        } else
            return null;

    }
}