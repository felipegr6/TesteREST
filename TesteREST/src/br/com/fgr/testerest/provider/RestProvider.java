package br.com.fgr.testerest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import br.com.fgr.testerest.BD.BDHelper;
import br.com.fgr.testerest.BD.TabelaBanda;

public class RestProvider extends ContentProvider {

    public static final String AUTHORITY = "br.com.fgr.testerest.provider";

    private static final String TAG = RestProvider.class.getSimpleName();
    private static final String CAMINHO_BASE = "bandas";
    private BDHelper bdHelper;
    private SQLiteDatabase bancoDados;
    private static final int BANDAS = 1;
    private static final int BANDAS_ID = 2;
    private static final UriMatcher sUriMatcher;

    static {

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, CAMINHO_BASE, BANDAS);
        sUriMatcher.addURI(AUTHORITY, CAMINHO_BASE + "/#", BANDAS_ID);

    }

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + CAMINHO_BASE);

    @Override
    public boolean onCreate() {

        Log.i(TAG, "onCreate()");

        bdHelper = new BDHelper(getContext());
        bancoDados = bdHelper.getWritableDatabase();

        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        Log.i(TAG, "query()");

        Cursor cursor = null;

        switch (sUriMatcher.match(uri)) {

        case BANDAS:
            cursor = bancoDados.query(TabelaBanda.TABELA_BANDA,
                    TabelaBanda.TODAS_COLUNAS, selection, selectionArgs, null,
                    null, sortOrder);
            break;
        case BANDAS_ID:
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.i(TAG, "insert()");

        long id = 0;

        switch (sUriMatcher.match(uri)) {

        case BANDAS:
            id = bancoDados.insert(TabelaBanda.TABELA_BANDA, null, values);
            break;
        case BANDAS_ID:
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(CAMINHO_BASE + "/" + id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.i(TAG, "delete()");

        int linhasDeletadas = 0;

        switch (sUriMatcher.match(uri)) {

        case BANDAS:
            linhasDeletadas = bancoDados.delete(TabelaBanda.TABELA_BANDA,
                    selection, selectionArgs);
            break;
        case BANDAS_ID:
            String id = uri.getLastPathSegment();
            linhasDeletadas = bancoDados.delete(TabelaBanda.TABELA_BANDA,
                    TabelaBanda.COLUNA_ID + "=" + id, null);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        return linhasDeletadas;

    }

    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {

        Log.i(TAG, "update()");

        int linhasAtualizadas = 0;

        switch (sUriMatcher.match(uri)) {

        case BANDAS:
            linhasAtualizadas = bancoDados.update(TabelaBanda.TABELA_BANDA,
                    values, selection, selectionArgs);
            break;
        case BANDAS_ID:
            String id = uri.getLastPathSegment();
            linhasAtualizadas = bancoDados.update(TabelaBanda.TABELA_BANDA,
                    values, TabelaBanda.COLUNA_ID + "=" + id, null);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        return linhasAtualizadas;

    }

    @Override
    public String getType(Uri uri) {

        Log.i(TAG, "getType()");

        return "";

    }

}