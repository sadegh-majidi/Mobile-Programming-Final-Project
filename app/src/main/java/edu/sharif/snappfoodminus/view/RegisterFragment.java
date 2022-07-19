package edu.sharif.snappfoodminus.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.UserController;
import edu.sharif.snappfoodminus.temp.Restaurant;
import edu.sharif.snappfoodminus.temp.Role;
import edu.sharif.snappfoodminus.temp.User;

public class RegisterFragment extends Fragment {

    private UserController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new UserController(getContext());

        EditText nameEditText = view.findViewById(R.id.registerFirstnameText);
        EditText usernameEditText = view.findViewById(R.id.usernameRegisterTxt);
        EditText passwordEditText = view.findViewById(R.id.passwordRegisterText);
        EditText confirmPasswordEditText = view.findViewById(R.id.confirmPasswordRegisterText);
        RadioGroup roleRadioGroup = view.findViewById(R.id.roleRadioGroup);
        Button registerButton = view.findViewById(R.id.registerBtn);
        TextView loginLink =  view.findViewById(R.id.loginLinkText);

        roleRadioGroup.check(R.id.userRadioBtn);

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String nameError = controller.getNameError(name);
            String usernameError = controller.getUsernameError(username);
            String passwordError = controller.getPasswordError(password);
            String confirmPasswordError = controller.getPasswordError(confirmPassword);
            if (nameError != null) {
                Toast.makeText(getContext(), nameError, Toast.LENGTH_LONG).show();
            } else if (usernameError != null) {
                Toast.makeText(getContext(), usernameError, Toast.LENGTH_LONG).show();
            } else if (passwordError != null) {
                Toast.makeText(getContext(), passwordError, Toast.LENGTH_LONG).show();
            } else if (confirmPasswordError != null) {
                Toast.makeText(getContext(), confirmPasswordError, Toast.LENGTH_LONG).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
            } else {
                int selectedId = roleRadioGroup.getCheckedRadioButtonId();
                Role role = selectedId == R.id.userRadioBtn ? Role.CUSTOMER : Role.OWNER;
                User user = new User(username, password, name, role);
                String registerMessage = controller.register(user);
                if (registerMessage != null) {
                    Toast.makeText(getContext(), registerMessage, Toast.LENGTH_LONG).show();
                } else {
                    User.addUser(getContext(), user);
                    if (user.role == Role.OWNER) {
                        String restaurantName = user.username + "'s restaurant";
                        Restaurant restaurant = new Restaurant(restaurantName, user.username, 0);
                        Restaurant.addRestaurant(getContext(), restaurant);
                    }
                    Toast.makeText(getContext(), "Successfully signed up", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new LoginFragment()).commit();
                }
            }
        });

        loginLink.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment()).commit();
        });
    }

}