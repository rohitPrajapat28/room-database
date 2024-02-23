package apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("posts")
    Call<List<ApiResponse>> getPosts();

    @GET("/users/{id}")
    Call<DetailsResponse> getUser(@Path("id") int userId);
}
