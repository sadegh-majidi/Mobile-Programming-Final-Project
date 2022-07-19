package edu.sharif.snappfoodminus.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.UserController;
import edu.sharif.snappfoodminus.temp.LoginRepository;
import edu.sharif.snappfoodminus.temp.User;

public class LoginFragment extends Fragment {

    private UserController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new UserController(getContext());

        EditText usernameEditText = view.findViewById(R.id.usernameLoginText);
        EditText passwordEditText = view.findViewById(R.id.passwordLoginText);
        Button loginButton = view.findViewById(R.id.loginBtn);
        TextView registerLink = view.findViewById(R.id.registerLinkText);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = usernameEditText.getText().toString().trim();
                String usernameError = controller.getUsernameError(username);
                usernameEditText.setError(usernameError);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = passwordEditText.getText().toString().trim();
                String passwordError = controller.getPasswordError(password);
                passwordEditText.setError(passwordError);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String usernameError = controller.getUsernameError(username);
            String passwordError = controller.getPasswordError(password);
            if (usernameError == null && passwordError == null) {
                User user = controller.login(username, password);
                if (user == null) {
                    Toast.makeText(getContext(), "Incorrect username or password",
                            Toast.LENGTH_LONG).show();
                } else {
                    // TODO: Login for owner or admin
                    LoginRepository.username = user.username;
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
            }
        });

        registerLink.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new RegisterFragment()).commit();
        });
    }
}