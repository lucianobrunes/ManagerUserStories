package com.br.android.manageruserstories;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends ListActivity {

    public static final int REQUEST_EDICAO = 1;
    public static final int REQUEST_SALVOU = 2;

    private BancoDeDados db;
    private List<UserStory> stories = new ArrayList<>();
    private StoriesAdapter storiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        db = new BancoDeDados(this);
        lerDados();
    }

    public void lerDados(){
        db.abrir();
        stories.clear();
        Cursor cursor = db.retornaTodasStories();
        if (cursor.moveToFirst()){
            do {
                UserStory a = new UserStory();
                a.id = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_ID));
                a.titulo = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_TITULO));
                a.interessado = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_INTERESSADO));
                a.necessidade = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_NECESSIDADE));
                a.razao = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_RAZAO));
                stories.add(a);
            } while (cursor.moveToNext());
        }

        if (stories.size() > 0){
            if (storiesAdapter == null){
                storiesAdapter = new StoriesAdapter(this, stories){

                    @Override
                    public void edita(UserStory story) {
                        Intent intent = new Intent(getApplicationContext(), NovoEdicaoActivity.class);
                        intent.putExtra("story", story);
                        startActivityForResult(intent, REQUEST_EDICAO);
                    }

                    @Override
                    public void deleta(UserStory story) {
                        db.abrir();
                        db.apagaStory(story.id);
                        db.fechar();
                        lerDados();
                    }

                };
                setListAdapter(storiesAdapter);
            } else {
                storiesAdapter.novosDados(stories);
            }
        }
        db.fechar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stories, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == R.id.menu_relatorios){
/*
            Intent intent = new Intent(this, Relatorios.class);
            startActivity(intent);
*/
            return true;
        } else if (item.getItemId() == R.id.menu_add){
            Intent intent = new Intent(this, NovoEdicaoActivity.class);

            startActivityForResult(intent, REQUEST_EDICAO);
            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_EDICAO) {
            if (resultCode == REQUEST_SALVOU) {
                lerDados();
            }
        }
    }


}
