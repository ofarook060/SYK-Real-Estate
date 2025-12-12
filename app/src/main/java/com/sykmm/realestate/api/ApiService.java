package com.sykmm.realestate.api;

import com.sykmm.realestate.models.Category;
import com.sykmm.realestate.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("posts?_embed")
    Call<List<Post>> getPosts();

    @GET("posts/{id}?_embed")
    Call<Post> getPost(@Path("id") int id);

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("posts?_embed")
    Call<List<Post>> getPostsByCategory(@Query("categories") int categoryId);
}
