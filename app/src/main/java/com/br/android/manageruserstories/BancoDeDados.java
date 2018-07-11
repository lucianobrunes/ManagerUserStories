package com.br.android.manageruserstories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados {
    static String KEY_ID = "_id";
    static String KEY_TITULO = "titulo";
    static String KEY_INTERESSADO = "interessado";
    static String KEY_NECESSIDADE = "necessidade";
    static String KEY_RAZAO = "razao";

    String NOME_BANCO = "dba";
    String NOME_TABELA = "story";
    int VERSAO_BANCO = 1;
    String SQL_CREATE_TABLE = "create table " + NOME_TABELA + " " +
            "("+KEY_ID+" integer primary key autoincrement, "
            + KEY_TITULO + " text not null, " +
            KEY_INTERESSADO + " text, " +
            KEY_NECESSIDADE + " text, " +
            KEY_RAZAO + " text);";

    final Context context;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    public BancoDeDados(Context ctx) {
        this.context = ctx;
        openHelper = new MeuOpenHelper(context);
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {
        MeuOpenHelper(Context context) {
            super(context, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(SQL_CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    public BancoDeDados abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void fechar() {
        openHelper.close();
    }

    public long insereStory(String titulo, String interessado,
                             String necessidade, String razao) {
        ContentValues campos = new ContentValues();
        campos.put(KEY_TITULO, titulo);
        campos.put(KEY_INTERESSADO, interessado);
        campos.put(KEY_NECESSIDADE, necessidade);
        campos.put(KEY_RAZAO, razao);

        return db.insert(NOME_TABELA, null, campos);
    }

    public boolean apagaStory(long id) {
        return db.delete(NOME_TABELA, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor retornaTodasStories() {
        return db.query(NOME_TABELA, new String[] {
                        KEY_ID, KEY_TITULO, KEY_INTERESSADO, KEY_NECESSIDADE, KEY_RAZAO},
                null, null, null, null, null, null);
    }

    public Cursor retornaStory(long id) throws SQLException {
        Cursor mCursor = db.query(true, NOME_TABELA, new String[] {
                        KEY_ID, KEY_TITULO, KEY_INTERESSADO, KEY_NECESSIDADE, KEY_RAZAO},
                KEY_ID + "=" + id,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean atualizaStory(long id, String titulo,
                                  String interessado, String necessidade, String razao) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITULO, titulo);
        args.put(KEY_INTERESSADO, interessado);
        args.put(KEY_NECESSIDADE, necessidade);
        args.put(KEY_RAZAO, razao);
        return db.update(NOME_TABELA, args, KEY_ID + "=" + id, null) > 0;
    }
}
