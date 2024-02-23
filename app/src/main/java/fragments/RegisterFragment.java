package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.random1.R;
import com.example.random1.databinding.FragmentRegisterBinding;
import com.example.random1.db.AppDatabase;
import com.example.random1.db.User;
import com.example.random1.db.UserDao;

import utils.ValidationUtils;


public class RegisterFragment extends Fragment {
    AppDatabase myDb;
    UserDao userDao;
    FragmentRegisterBinding binding;
    public static boolean isAllowed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getView() != null)
                    Navigation.findNavController(getView()).navigateUp();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDb = Room
                .databaseBuilder(getContext(), AppDatabase.class, "user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        userDao = myDb.userDao();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.registerEmailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = editable.toString();
                if (userDao.is_taken(username)) {
                    isAllowed = false;
                    Toast.makeText(getContext(), "Already Taken", Toast.LENGTH_SHORT).show();
                } else isAllowed = true;
            }
        });

        binding.loginTv.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.login);
        });

        binding.registerBtn.setOnClickListener(view -> {
            if (checkValidation()) {
                if (isAllowed) {
                    User user = new User(0, binding.registerEmailEdt.getText().toString()
                            ,binding.registerNameEdt.getText().toString()
                            ,binding.lastNameEdt.getText().toString()
                            ,binding.numberEdt.getText().toString()
                            ,binding.registerPasswordEdt.getText().toString());
                    userDao.insert(user);
                    Toast.makeText(getContext(), " user Registered", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", binding.registerEmailEdt.getText().toString());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.login, bundle);
                } else {
                    Toast.makeText(getContext(), " email Already Taken", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }


    private boolean checkValidation() {

        if (TextUtils.isEmpty(binding.registerNameEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter user name",Toast.LENGTH_SHORT).show();
            return false;
        }  if (TextUtils.isEmpty(binding.lastNameEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter user last name",Toast.LENGTH_SHORT).show();
            return false;
        }  if (TextUtils.isEmpty(binding.numberEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter mobile number",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationUtils.isValidMobile(binding.numberEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter valid mobile number",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.registerEmailEdt.getText().toString())) {
            Toast.makeText(getContext(), "please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationUtils.validateEmail(binding.registerEmailEdt.getText().toString())) {
            Toast.makeText(getContext(), "enter vaild email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.registerPasswordEdt.getText().toString())) {
            Toast.makeText(getContext(), "please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.registerPasswordEdt.getText().length() < 8) {
            Toast.makeText(getContext(), "password less than 8 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.registerPasswordEdt.getText().length() > 16) {
            Toast.makeText(getContext(), "not more then sixteen", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}