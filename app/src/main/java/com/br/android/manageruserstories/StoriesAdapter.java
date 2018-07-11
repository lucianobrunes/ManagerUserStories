package com.br.android.manageruserstories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public abstract class StoriesAdapter extends BaseAdapter {

    private List<UserStory> stories;
    private LayoutInflater inflater;

    public StoriesAdapter(Context context, List<UserStory> stories){
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.stories = stories;
    }

    public void novosDados(List<UserStory> stories){
        this.stories = stories;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return stories.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.item_story, null);
        ((TextView)(v.findViewById(R.id.txtTitulo))).setText(stories.get(position).titulo);

        ((ImageButton)(v.findViewById(R.id.btnEditar))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                edita(stories.get(position));
            }
        });

        ((ImageButton)(v.findViewById(R.id.btnExcluir))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                deleta(stories.get(position));
            }
        });

        return v;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void edita(UserStory story);
    public abstract void deleta(UserStory story);
}
