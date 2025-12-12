package com.sykmm.realestate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sykmm.realestate.PostAdapter;
import com.sykmm.realestate.R;
import com.sykmm.realestate.api.ApiClient;
import com.sykmm.realestate.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsByCategoryFragment extends Fragment {

    private final int categoryId;
    private final String title;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public PostsByCategoryFragment(int categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_posts_by_category, container, false);

        recyclerView = view.findViewById(R.id.recyclerCategoryPosts);
        progressBar = view.findViewById(R.id.progressCategoryPosts);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategoryPosts();

        return view;
    }

    private void loadCategoryPosts() {
        progressBar.setVisibility(View.VISIBLE);

        ApiClient.getApi().getPostsByCategory(categoryId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(new PostAdapter(getContext(), response.body()));
                } else {
                    Toast.makeText(getContext(), "Failed to load posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}