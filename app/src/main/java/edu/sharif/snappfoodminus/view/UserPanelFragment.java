package edu.sharif.snappfoodminus.view;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.LogoutController;
import edu.sharif.snappfoodminus.controller.UserController;
import edu.sharif.snappfoodminus.temp.LoginRepository;
import edu.sharif.snappfoodminus.temp.User;

public class UserPanelFragment extends Fragment {

    private UserController controller;

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new UserController(getContext());
        sharedPreferences = getActivity().getSharedPreferences(Shared_KEY, Context.MODE_PRIVATE);

        TextView nameTextView = view.findViewById(R.id.userPanelName);
        TextView usernameTextView = view.findViewById(R.id.userPanelUsername);
        EditText newNameEditText = view.findViewById(R.id.userPanelNewName);
        EditText newPasswordEditText = view.findViewById(R.id.userPanelNewPassword);
        EditText confirmPasswordEditText = view.findViewById(R.id.userPanelNewPasswordConfirm);
        EditText currentPasswordEditText = view.findViewById(R.id.userPanelCurrentPassword);
        Button confirmChangesButton = view.findViewById(R.id.userPanelChangeInfoButton);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch darkModeSwitch = view.findViewById(R.id.darkModeSwitch);

        User loggedInUser = LoginRepository.getLoggedInUser(getContext());
        nameTextView.setText(loggedInUser.name);
        usernameTextView.setText(loggedInUser.username);

        confirmChangesButton.setOnClickListener(v -> {
            String newName = newNameEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String currentPassword = currentPasswordEditText.getText().toString().trim();
            String newPasswordError = controller.getPasswordError(newPassword);

            User user = LoginRepository.getLoggedInUser(getContext());
            if (user.password.equals(currentPassword)) {
                if (!newName.isEmpty())
                    user.name = newName;
                if (newPassword.equals(confirmPassword) && newPasswordError == null)
                    user.password = newPassword;
                if ((newPassword.isEmpty() && confirmPassword.isEmpty()) ||
                        (newPassword.equals(confirmPassword) && newPasswordError == null)) {
                    User.updateUser(getContext(), user, user.username);
                    nameTextView.setText(user.name);
                    Toast.makeText(getContext(), "User info updated successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "New password not allowed", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_LONG).show();
            }
            newNameEditText.setText("");
            newPasswordEditText.setText("");
            confirmPasswordEditText.setText("");
            currentPasswordEditText.setText("");
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutController controller = LogoutController.getInstance();
                controller.logout(getContext());
            }
        });

        boolean isNight = AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES;
        darkModeSwitch.setChecked(isNight);
        darkModeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                saveDarkModeState(true);
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            } else {
                saveDarkModeState(false);
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            }
        });
    }

    private void saveDarkModeState(boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DarkMode", state);
        editor.putBoolean("DarkModeJustChanged", true);
        editor.apply();
    }
}
