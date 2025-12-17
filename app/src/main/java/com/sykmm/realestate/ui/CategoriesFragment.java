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

import com.sykmm.realestate.R;
import com.sykmm.realestate.adapters.CategoryAdapter;
import com.sykmm.realestate.api.ApiClient;
import com.sykmm.realestate.api.ApiService;
import com.sykmm.realestate.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.recyclerCategories);
        progressBar = view.findViewById(R.id.progressCategories);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategories();

        return view;
    }

    private void loadCategories() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService api = ApiClient.getApi();

        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body();

                    CategoryAdapter adapter = new CategoryAdapter(getContext(), categories, category -> {
                        Fragment fragment = new PostsByCategoryFragment(category.getId(), category.getName());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}