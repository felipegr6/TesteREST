package br.com.fgr.testerest.syncadapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import br.com.fgr.testerest.BD.TabelaBanda;
import br.com.fgr.testerest.modelos.Banda;
import br.com.fgr.testerest.provider.RestProvider;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = SyncAdapter.class.getSimpleName();

    ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {

        super(context, autoInitialize);

        mContentResolver = context.getContentResolver();

    }

    public SyncAdapter(Context context, boolean autoInitialize,
            boolean allowParallelSyncs) {

        super(context, autoInitialize, allowParallelSyncs);

        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
            ContentProviderClient provider, SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()");
        long indice;

        ContentValues mNewValues;
        Uri mNewUri;

        indice = ultimoTs();

        String urlString = "http://192.168.43.240:8080/helloworld-webapp/rest/bandas/ts/"
                + indice;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urlString);

        try {

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();
                List<Banda> bandas = getBandas(json);

                for (Banda b : bandas) {

                    mNewValues = new ContentValues();

                    mNewValues.put(TabelaBanda.COLUNA_ID, b.getId());
                    mNewValues.put(TabelaBanda.COLUNA_NOMEBANDA,
                            b.getNomeBanda());
                    mNewValues.put(TabelaBanda.COLUNA_ANOFORMACAO,
                            b.getAnoFormacao());
                    mNewValues.put(TabelaBanda.COLUNA_TS, b.getTimeStamp());

                    mNewUri = mContentResolver.insert(RestProvider.CONTENT_URI,
                            mNewValues);

                    Log.i(TAG, mNewUri.toString());

                }

            }

        } catch (Exception e) {

            Log.e(TAG, "Falha ao acessar Web service", e);

        }

    }

    private List<Banda> getBandas(String jsonString) {

        List<Banda> listaBandas = new ArrayList<Banda>();

        try {

            JSONArray jsonArrayBanda = new JSONArray(jsonString);
            JSONObject jsonObjetoBanda;

            for (int i = 0; i < jsonArrayBanda.length(); i++) {

                Banda banda = new Banda();
                jsonObjetoBanda = jsonArrayBanda.getJSONObject(i);

                banda.setId(jsonObjetoBanda.getLong("id"));
                banda.setNomeBanda(jsonObjetoBanda.getString("nome"));
                banda.setAnoFormacao(jsonObjetoBanda.getInt("anoDeFormacao"));
                banda.setTimeStamp(jsonObjetoBanda.getLong("ts"));

                listaBandas.add(banda);

            }

        } catch (JSONException e) {

            Log.e(TAG, "Erro no parsing do JSON", e);

        }

        return listaBandas;

    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;

        while ((lidos = is.read(bytes)) > 0) {

            baos.write(bytes, 0, lidos);

        }

        return new String(baos.toByteArray());

    }

    private long ultimoTs() {

        Cursor c;
        long indice = 0;

        c = mContentResolver.query(RestProvider.CONTENT_URI,
                TabelaBanda.TODAS_COLUNAS, null, null, null);

        if (c.moveToLast())
            indice = c.getLong(3);

        c.close();

        return indice;

    }

}