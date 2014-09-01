package br.com.fgr.testerest;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import br.com.fgr.testerest.modelos.Banda;

public class MainActivity extends ListActivity {

    private ArrayAdapter<Banda> adapter;
    private List<Banda> bandas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bandas = new ArrayList<Banda>();
        Banda b1 = new Banda(0, "x", 1900, 123);
        Banda b2 = new Banda(1, "y", 1910, 334);
        bandas.add(b1);
        bandas.add(b2);
        
        adapter = new ArrayAdapter<Banda>(this,
                android.R.layout.simple_list_item_1, bandas);

        setListAdapter(adapter);

    }

}