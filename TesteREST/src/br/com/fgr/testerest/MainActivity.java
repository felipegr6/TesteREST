package br.com.fgr.testerest;

import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import br.com.fgr.testerest.BD.TabelaBanda;
import br.com.fgr.testerest.modelos.Banda;
import br.com.fgr.testerest.provider.RestProvider;

public class MainActivity extends ListActivity implements
        LoaderCallbacks<Cursor> {

    private static final String ACCOUNT = "default_account";
    private static final String ACCOUNT_TYPE = "account.testerest.fgr.com.br";
    private static final long SYNC_INTERVAL = 60L;
    private ArrayAdapter<Banda> adapter01;
    private SimpleCursorAdapter adapter02;
    private List<Banda> bandas;

    ContentResolver mContentResolver;
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentResolver = getContentResolver();
        mAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) this
                .getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(mAccount, null, null);

        ContentResolver.setIsSyncable(mAccount, RestProvider.AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(mAccount, RestProvider.AUTHORITY,
                true);
        ContentResolver.addPeriodicSync(mAccount, RestProvider.AUTHORITY,
                new Bundle(), SYNC_INTERVAL);

        getLoaderManager().initLoader(0, null, this);

        String from[] = new String[] { TabelaBanda.COLUNA_NOMEBANDA };
        int to[] = new int[] { R.id.label };

        adapter02 = new SimpleCursorAdapter(this, R.layout.layout_linha, null,
                from, to, 0);
        setListAdapter(adapter02);

        /*
         * bandas = new ArrayList<Banda>();
         * 
         * Cursor c = mContentResolver.query(RestProvider.CONTENT_URI,
         * TabelaBanda.TODAS_COLUNAS, null, null, null);
         * 
         * while (c.moveToNext()) {
         * 
         * Banda b = new Banda(c.getLong(0), c.getString(1), c.getInt(2),
         * c.getLong(3)); bandas.add(b);
         * 
         * }
         * 
         * adapter01 = new ArrayAdapter<Banda>(this, R.layout.layout_linha,
         * R.id.label, bandas);
         * 
         * setListAdapter(adapter01);
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        String item = (String) getListAdapter().getItem(position).toString();

        Toast.makeText(this, item + " selecionado.", Toast.LENGTH_LONG).show();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.i(this.getClass().getSimpleName(), "onCreateLoader()");

        CursorLoader cursorLoader = new CursorLoader(this,
                RestProvider.CONTENT_URI, null, null, null, null);

        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.i(this.getClass().getSimpleName(), "onLoadFinished()");

        adapter02.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        Log.i(this.getClass().getSimpleName(), "onLoaderReset()");

        adapter02.swapCursor(null);

    }

}