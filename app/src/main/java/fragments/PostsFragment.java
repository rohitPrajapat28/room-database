package fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.random1.R;
import com.example.random1.databinding.FragmentPostsBinding;

import java.util.List;

import apis.ApiInterface;
import apis.ApiResponse;
import fragments.adapters.PostsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsFragment extends BaseFragment {
    FragmentPostsBinding binding;
    PostsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false);
        getPosts();
        binding.includedBottomNav.consprofile.setOnClickListener(view -> {
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.);
        });
        return binding.getRoot();
    }

    public void getPosts() {
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<ApiResponse>> call = apiService.getPosts();

        call.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                if (response.isSuccessful()) {
                    List<ApiResponse> posts = response.body();
                    Toast.makeText(getContext(), posts.get(1).title, Toast.LENGTH_SHORT).show();
                    adapter = new PostsAdapter(getContext(), posts);
                    binding.recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {

            }
        });
    }

}