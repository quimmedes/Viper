package com.kimede.viper.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SimpleDatabaseHelper {
    private SQLiteOpenHelper _openHelper;

    class SimpleSQLiteOpenHelper extends SQLiteOpenHelper {
        SimpleSQLiteOpenHelper(Context context) {
            super(context, "bancodados.db", null, 2);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("create table if not exists favorito (_id integer primary key, nome text, imagem text);");
            sQLiteDatabase.execSQL("create table if not exists episodio (_id integer primary key, episodio integer);");
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    public SimpleDatabaseHelper(Context context) {
        this._openHelper = new SimpleSQLiteOpenHelper(context);
    }

    public long add(long j, String str, String str2) {
        long j2 = 0;
        try {
            SQLiteDatabase writableDatabase = this._openHelper.getWritableDatabase();
            if (writableDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", Long.valueOf(j));
                contentValues.put("nome", str);
                contentValues.put("imagem", str2);
                j2 = writableDatabase.insert("favorito", null, contentValues);
                writableDatabase.close();
            }
        } catch (Exception e) {
        }
        return j2;
    }

    public long addEpi(long j, long j2) {
        long j3 = 0;
        try {
            SQLiteDatabase writableDatabase = this._openHelper.getWritableDatabase();
            if (writableDatabase != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", Long.valueOf(j));
                contentValues.put("episodio", Long.valueOf(j2));
                j3 = writableDatabase.replace("episodio", null, contentValues);
                writableDatabase.close();
            }
        } catch (Exception e) {
        }
        return j3;
    }

    public void delete(long j) {
        try {
            SQLiteDatabase writableDatabase = this._openHelper.getWritableDatabase();
            if (writableDatabase != null) {
                writableDatabase.delete("favorito", "_id = ?", new String[]{String.valueOf(j)});
                writableDatabase.close();
            }
        } catch (Exception e) {
        }
    }

    public ContentValues get(long j) {
        SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
        if (readableDatabase == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        Cursor rawQuery = readableDatabase.rawQuery("select _id, nome, imagem from favorito where _id = ?", new String[]{String.valueOf(j)});
        if (rawQuery.moveToFirst()) {
            contentValues.put("_id", Long.valueOf(rawQuery.getLong(0)));
            contentValues.put("nome", rawQuery.getString(1));
            contentValues.put("imagem", rawQuery.getString(2));
        }
        rawQuery.close();
        readableDatabase.close();
        return contentValues;
    }

    public Cursor getAll() {
        SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
        return readableDatabase == null ? null : readableDatabase.rawQuery("select _id, nome, imagem from favorito order by nome", null);
    }

    public Cursor getVisto() {
        SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
        return readableDatabase == null ? null : readableDatabase.rawQuery("select _id, episodio from episodio order by _id desc LIMIT 9", null);
    }

    public long getEpi(long j) {
        long j2 = 0;
        try {
            SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
            if (readableDatabase != null) {
                Cursor rawQuery = readableDatabase.rawQuery("select _id, episodio from episodio where _id = ?", new String[]{String.valueOf(j)});
                if (!(rawQuery == null || !rawQuery.moveToNext() || rawQuery.isNull(1))) {
                    j2 = rawQuery.getLong(1);
                }
                rawQuery.close();
                readableDatabase.close();
            }
        } catch (Exception e) {
        }
        return j2;
    }

    public Boolean isEpisodio(long j) {
        SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
        Boolean valueOf = Boolean.valueOf(false);
        if (readableDatabase == null) {
            return null;
        }
        Cursor rawQuery = readableDatabase.rawQuery("select _id from episodio where _id = ?", new String[]{String.valueOf(j)});
        if (rawQuery.getCount() > 0) {
            valueOf = Boolean.valueOf(true);
        }
        rawQuery.close();
        readableDatabase.close();
        return valueOf;
    }

    public Boolean isFavorito(long j) {
        Boolean valueOf = Boolean.valueOf(false);
        try {
            SQLiteDatabase readableDatabase = this._openHelper.getReadableDatabase();
            if (readableDatabase == null) {
                return null;
            }
            Cursor rawQuery = readableDatabase.rawQuery("select _id, nome, imagem from favorito where _id = ?", new String[]{String.valueOf(j)});
            if (rawQuery.getCount() > 0) {
                valueOf = Boolean.valueOf(true);
            }
            rawQuery.close();
            readableDatabase.close();
            return valueOf;
        } catch (Exception e) {
            return valueOf;
        }
    }

    public void update(long j, String str, int i) {
        SQLiteDatabase writableDatabase = this._openHelper.getWritableDatabase();
        if (writableDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", Long.valueOf(j));
            contentValues.put("nome", str);
            contentValues.put("imagem", Integer.valueOf(i));
            writableDatabase.update("favorito", contentValues, "_id = ?", new String[]{String.valueOf(j)});
            writableDatabase.close();
        }
    }
}
