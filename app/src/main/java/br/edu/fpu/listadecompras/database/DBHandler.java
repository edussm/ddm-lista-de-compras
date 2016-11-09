package br.edu.fpu.listadecompras.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.fpu.listadecompras.domain.Status;
import br.edu.fpu.listadecompras.domain.Item;
import br.edu.fpu.listadecompras.domain.Unidade;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "compras";

    private static final String TABLE_COMPRAS = "compras";
    // Nome das colunas
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRICAO = "descricao";
    private static final String KEY_UNIDADE = "unidade";
    private static final String KEY_QUANTIDADE = "quantidade";
    private static final String KEY_STATUS = "status";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCompras = "CREATE TABLE " + TABLE_COMPRAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DESCRICAO + " TEXT,"
                + KEY_QUANTIDADE + " REAL, " + KEY_UNIDADE + " TEXT, "
                + KEY_STATUS + " TEXT )";
        db.execSQL(createTableCompras);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // Quero apagar a tabela antiga
        // TODO: Melhorar essa estratégia
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPRAS);
        // Cria a tabela
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, item.getDescricao());
        values.put(KEY_QUANTIDADE, item.getQuantidade());
        values.put(KEY_UNIDADE, item.getUnidade().toString());
        values.put(KEY_STATUS, item.getStatus().toString());

        db.insert(TABLE_COMPRAS, null, values);
        db.close();
    }

    public Item getItem(int id) {
        Item item = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPRAS,
                new String[]{KEY_ID, KEY_DESCRICAO, KEY_QUANTIDADE,
                        KEY_UNIDADE, KEY_STATUS},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            item = new Item();
            item.setId(cursor.getLong(0));
            item.setDescricao(cursor.getString(1));
            item.setQuantidade(cursor.getDouble(2));
            item.setUnidade(Unidade.valueOf(cursor.getString(3)));
            item.setStatus(Status.valueOf(cursor.getString(4)));
        }

        return item;
    }

    public List<Item> getAllItens() {
        List<Item> compras = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COMPRAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getLong(0));
                item.setDescricao(cursor.getString(1));
                item.setQuantidade(cursor.getDouble(2));
                item.setUnidade(Unidade.valueOf(cursor.getString(3)));
                item.setStatus(Status.valueOf(cursor.getString(4)));
                compras.add(item);
            } while (cursor.moveToNext());
        }

        return compras;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, item.getDescricao());
        values.put(KEY_QUANTIDADE, item.getQuantidade());
        values.put(KEY_UNIDADE, item.getUnidade().toString());
        values.put(KEY_STATUS, item.getStatus().toString());

        return db.update(TABLE_COMPRAS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPRAS, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPRAS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}

