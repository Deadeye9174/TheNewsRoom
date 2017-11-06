package com.example.company.thenewsroom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.company.thenewsroom.Common.Common;
import com.example.company.thenewsroom.Interface.IconBetterIdeaService;
import com.example.company.thenewsroom.Interface.ItemClickListener;
import com.example.company.thenewsroom.ListNews;
import com.example.company.thenewsroom.Model.IconBetterIdea;
import com.example.company.thenewsroom.Model.Source;
import com.example.company.thenewsroom.Model.WebSite;
import com.example.company.thenewsroom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jatin on 31/10/17.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    ItemClickListener itemClickListener;
    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(View itemView) {
        super(itemView);
        source_image = (CircleImageView) itemView.findViewById(R.id.source_image);
        source_title = (TextView) itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);
    }

    public ListSourceViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{
    private Context context;
    private List<Source> sources;

    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, List<Source> sources) {
        this.context = context;
        this.sources = sources;

        mService = Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {
        final Source source = sources.get(position);

        StringBuilder iconBetterAPI = new StringBuilder("https://icons.better-idea.org/allicons.json?url=");
        iconBetterAPI.append(source.getUrl());
        final ListSourceViewHolder listSourceViewHolder = holder;
        mService.getIconUrl(iconBetterAPI.toString())
                .enqueue(new Callback<IconBetterIdea>() {
                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                        if(response.body() != null) {
                            if (response.body().getIcons().size() > 0) {
                                Picasso.with(context)
                                        .load(response.body().getIcons().get(0).getUrl())
                                        .into(listSourceViewHolder.source_image);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                    }
                });

        holder.source_title.setText(source.getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context,ListNews.class);

                List<String> sortsByAvailable = source.getSortByAvailable();
                intent.putExtra("source",source.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sortBy", "top");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }
}
