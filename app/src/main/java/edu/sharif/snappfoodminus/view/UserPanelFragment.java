package edu.sharif.snappfoodminus.view;

import static android.content.Context.MODE_PRIVATE;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.LoginController;
import edu.sharif.snappfoodminus.controller.UserPanelController;

public class UserPanelFragment extends Fragment {

    private UserPanelController controller;

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new UserPanelController(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences(Shared_KEY, MODE_PRIVATE);

        EditText newNameTxt = view.findViewById(R.id.userPanelNewNameText);
        EditText oldPasswordTxt = view.findViewById(R.id.userPanelOldPasswordText);
        EditText oldPasswordConfirmationTxt = view.findViewById(R.id.userPanelConfirmOldPasswordText);
        EditText newPasswordTxt = view.findViewById(R.id.userPanelNewPasswordText);

        Button changeButton = view.findViewById(R.id.changeButton);

        SwitchCompat darkMode = view.findViewById(R.id.darkModeSwitch);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newNameTxt.getText().toString();
                String oldPassword = oldPasswordTxt.getText().toString();
                String oldPasswordConfirmation = oldPasswordConfirmationTxt.getText().toString();
                String newPassword = newPasswordTxt.getText().toString();
                String errorMsg = null;

                errorMsg = controller.getError(newName, oldPassword, oldPasswordConfirmation, newPassword);
            }
        });

        boolean isNight = AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES;
        darkMode.setChecked(isNight);

        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    saveDarkModeState(true);
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                }else {
                    saveDarkModeState(false);
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            }
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
