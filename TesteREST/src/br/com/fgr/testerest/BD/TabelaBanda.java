package br.com.fgr.testerest.BD;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TabelaBanda {

    public static final String TABELA_BANDA = "banda";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOMEBANDA = "nomebanda";
    public static final String COLUNA_ANOFORMACAO = "anoformacao";
    public static final String COLUNA_TS = "ts";
    public static final String[] TODAS_COLUNAS = { COLUNA_ID, COLUNA_NOMEBANDA,
            COLUNA_ANOFORMACAO, COLUNA_TS };

    private static final String TAG = TabelaBanda.class.getClass().getSimpleName();
    private static String DATABASE_CREATE = "CREATE TABLE " + TABELA_BANDA
            + "(" + COLUNA_ID + " INTEGER PRIMARY KEY, " + COLUNA_NOMEBANDA
            + " TEXT NOT NULL, " + COLUNA_ANOFORMACAO + " INTEGER NOT NULL, "
            + COLUNA_TS + " INTEGER NOT NULL);";
    private static String DATABASE_DESTROY = "DROP TABLE IF EXISTS "
            + TABELA_BANDA;

    public static void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
            int newVersion) {

        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data.");

        db.execSQL(DATABASE_DESTROY);
        onCreate(db);

    }
    
    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
        Log.w(TAG, "Downgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data.");
        
        db.execSQL(DATABASE_DESTROY);
        onCreate(db);
    
    }

}