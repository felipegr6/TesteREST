package br.com.fgr.testerest;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import br.com.fgr.testerest.modelos.Banda;
import br.com.fgr.testerest.provider.RestProvider;

public class MainActivity extends ListActivity {

    private static final String ACCOUNT = "default_account";
    private static final String ACCOUNT_TYPE = "account.testerest.fgr.com.br";
    private static final long SYNC_INTERVAL = 10L;
    private ArrayAdapter<Banda> adapter;
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

        bandas = new ArrayList<Banda>();
        Banda b1 = new Banda(0, "x", 1900, 123);
        Banda b2 = new Banda(1, "y", 1910, 334);
        Banda b3 = new Banda(2, "z", 1980, 332334);
        bandas.add(b1);
        bandas.add(b2);
        bandas.add(b3);

        adapter = new ArrayAdapter<Banda>(this,
                android.R.layout.simple_list_item_1, bandas);

        setListAdapter(adapter);

    }

}