package com.sykmm.realestate;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sykmm.realestate.api.ApiClient;
import com.sykmm.realestate.models.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {

    private TextView titleView, contentView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        titleView = findViewById(R.id.postTitle);
        contentView = findViewById(R.id.postContent);
        imageView = findViewById(R.id.postImage);

        int postId = getIntent().getIntExtra("post_id", -1);

        if (postId != -1) {
            loadPost(postId);
        }
    }

    private void loadPost(int id) {
        ApiClient.getApi().getPost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Post post = response.body();

                    titleView.setText(post.getTitle().getRendered());
                    contentView.setText(Html.fromHtml(post.getContent().getRendered()));

                    // Load featured image
                    String imageUrl = null;
                    if (post.getEmbedded() != null &&
                            post.getEmbedded().getFeaturedMedia() != null &&
                            post.getEmbedded().getFeaturedMedia().size() > 0) {

                        imageUrl = post.getEmbedded().getFeaturedMedia().get(0).getSourceUrl();
                    }

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(PostDetailActivity.this)
                                .load(imageUrl)
                                .into(imageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // handle API failure
            }
        });
    }
}

