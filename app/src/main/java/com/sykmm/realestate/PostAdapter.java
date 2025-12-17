package com.sykmm.realestate;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sykmm.realestate.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context context;
    private final List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = posts.get(position);

        holder.title.setText(post.getTitle().getRendered());
        holder.excerpt.setText(Html.fromHtml(post.getContent().getRendered()));

        // Load featured image
        String img = null;
        try {
            img = post.getEmbedded().getFeaturedMedia().get(0).getSourceUrl();
        } catch (Exception ignored) {
        }

        if (img != null) {
            Glide.with(context)
                    .load(img)
                    .into(holder.thumbnail);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("post_id", post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title, excerpt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.postThumbnail);
            title = itemView.findViewById(R.id.postTitle);
            excerpt = itemView.findViewById(R.id.postExcerpt);
        }
    }
}
