package com.br.android.manageruserstories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NovoEdicaoActivity extends Activity {
    private EditText edtTitulo;
    private EditText edtInteressado;
    private EditText edtNecessidade;
    private EditText edtRazao;
    private UserStory story;

    private LinearLayout corpoStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_edicao);

        corpoStory = (LinearLayout) findViewById(R.id.corpoStory);

        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        edtInteressado = (EditText) findViewById(R.id.edtInteressado);
        edtNecessidade = (EditText) findViewById(R.id.edtNecessidade);
        edtRazao = (EditText) findViewById(R.id.edtRazao);

/*
        spEstado = (Spinner) findViewById(R.id.spEstado);
        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int selecionado, long arg3) {
                if (selecionado == 3){
                    dtPublicacao.setVisibility(View.VISIBLE);
                } else {
                    dtPublicacao.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
*/

/*
        spDestino = (Spinner) findViewById(R.id.spDestino);
        spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(
                    AdapterView<?> arg0, View arg1,
                    int selecionado, long arg3) {
                switch (selecionado){
                    case 0:
                        itensRevista.setVisibility(View.VISIBLE);
                        itensSite.setVisibility(View.GONE);
                        itensLivro.setVisibility(View.GONE);

                        break;
                    case 1:
                        itensRevista.setVisibility(View.GONE);
                        itensSite.setVisibility(View.VISIBLE);
                        itensLivro.setVisibility(View.GONE);

                        break;
                    case 2:
                        itensRevista.setVisibility(View.GONE);
                        itensSite.setVisibility(View.GONE);
                        itensLivro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
*/

/*
        cbPago = (CheckBox) findViewById(R.id.cbPago);
        dtPublicacao = (DatePicker) findViewById(R.id.dtPublicacao);
        edtSite = (EditText) findViewById(R.id.edtSite);
        edtLivro = (EditText) findViewById(R.id.edtLivro);
*/
        // dtPublicacao.

        Intent intent = getIntent();
        story = (UserStory) intent.getSerializableExtra("story");
        if (story != null) {
            edtTitulo.setText(story.titulo);
            edtInteressado.setText(story.interessado);
            edtNecessidade.setText(story.necessidade);
            edtRazao.setText(story.razao);
        }
    }

    public void salvar(View v) {
        BancoDeDados db = new BancoDeDados(this);
        db.abrir();

        if (story != null) {
            db.atualizaStory(story.id, edtTitulo.getText().toString(),
                    edtInteressado.getText().toString(), edtNecessidade.getText()
                            .toString(), edtRazao.getText().toString() );
        } else {
            db.insereStory(edtTitulo.getText().toString(), edtInteressado.getText()
                            .toString(), edtNecessidade.getText().toString(), edtRazao.getText().toString());
        }

        db.fechar();
        setResult(StoriesActivity.REQUEST_SALVOU);
        finish();
    }

}
