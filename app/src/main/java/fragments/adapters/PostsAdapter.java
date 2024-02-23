package fragments.adapters;

        import android.content.Context;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;

        import androidx.annotation.NonNull;
        import androidx.databinding.DataBindingUtil;
        import androidx.navigation.Navigation;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.random1.R;
        import com.example.random1.databinding.DetailedPostAdapteerBinding;
        import com.example.random1.databinding.PostsAdapterBinding;

        import java.util.ArrayList;
        import java.util.List;

        import apis.ApiResponse;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<ApiResponse> datalist = new ArrayList<>();
    PostsAdapterBinding binding;

    public PostsAdapter(Context context, List<ApiResponse> datalist) {
        this.context = context;
        this.datalist = datalist;

    }

    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        PostsAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.posts_adapter,group,false);
        return new ViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        PostsAdapterBinding binding;

        public ViewHolder(PostsAdapterBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(ApiResponse data){
            if(data.title != null) {
                binding.titleTv.setText(data.title);
            }
            if(data.body != null) {
                binding.subTitleTv.setText(data.body);
            }
            itemView.setOnClickListener(view -> {
                Bundle b = new Bundle();
                b.putString("id", String.valueOf(data.userId));
                Navigation.findNavController(binding.getRoot()).navigate(R.id.postsDetails,b);
            });
        }

    }
}
