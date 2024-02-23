package fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.random1.R;
import com.example.random1.databinding.FragmentLoginBinding;
import com.example.random1.db.AppDatabase;
import com.example.random1.db.UserDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import apis.ApiInterface;
import apis.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ValidationUtils;


public class LoginFragment extends BaseFragment {
    FragmentLoginBinding binding;
    AppDatabase myDb;
    UserDao userDao;
    String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDb = Room
                .databaseBuilder(getContext(), AppDatabase.class, "user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        userDao = myDb.userDao();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
         if(getArguments() != null){
             email = getArguments().getString("email");
             binding.emailEdt.setText(email);
         }
        binding.reegisterTv.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.register);
        });
        binding.loginBtn.setOnClickListener(view -> {
            if (checkValidation()) {
                String email = binding.emailEdt.getText().toString();
                String password = binding.passwordEdt.getText().toString();
                if (userDao.login(email, password)) {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.posts);
                    Toast.makeText(getContext(), "logged successful", Toast.LENGTH_SHORT).show();
                }else if(!userDao.login(email, password)){
                    Toast.makeText(getContext(), "user not found register now", Toast.LENGTH_SHORT).show();
                }
            }

        });
        return binding.getRoot();
    }

    private boolean checkValidation() {
        if (TextUtils.isEmpty(binding.emailEdt.getText().toString())) {
            Toast.makeText(getContext(), "please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationUtils.validateEmail(binding.emailEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEdt.getText().toString())) {
            Toast.makeText(getContext(), "please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.passwordEdt.getText().length() < 8) {
            Toast.makeText(getContext(), "password less than 8 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.passwordEdt.getText().length() > 16) {
            Toast.makeText(getContext(), "not more then sixteen", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}