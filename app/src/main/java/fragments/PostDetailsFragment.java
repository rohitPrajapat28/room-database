package fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.random1.R;
import com.example.random1.databinding.FragmentPostDetailsBinding;

import java.util.List;

import apis.ApiInterface;
import apis.ApiResponse;
import apis.DetailsResponse;
import fragments.adapters.PostsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostDetailsFragment extends BaseFragment {
    FragmentPostDetailsBinding binding;
    String id;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, container, false);
        if (getArguments() != null) {
            if (getArguments().containsKey("id")){
            id= getArguments().getString("id");
                getPostsDetails();
            }
        }
        return binding.getRoot();
    }

    public void getPostsDetails() {
            ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<DetailsResponse> call = apiService.getUser(1); // Replace 2 with actual user ID
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful()) {
                    DetailsResponse details = response.body();
                    assert details != null;
                    binding.name.setText(details.getName());
                    binding.username.setText(details.getUsername());
                    binding.email.setText(details.getEmail());
                    binding.phone.setText(details.getPhone());
                    binding.street.setText(details.getAddress().getStreet());
                    binding.suite.setText(details.getAddress().getSuite());
                    binding.city.setText(details.getAddress().getCity());
                    binding.zip.setText(details.getAddress().getZipcode());
                    binding.compantName.setText(details.getCompany().getName());
                    binding.website.setText(details.getWebsite());
                } else {
                    Toast.makeText(getContext(), "get back after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}