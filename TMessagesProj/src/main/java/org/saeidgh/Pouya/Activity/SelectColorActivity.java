package org.saeidgh.Pouya.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.saeidgh.Pouya.Adapter.SelectColorAdapter;
import org.saeidgh.Pouya.Helper.ThemeChanger;
import org.saeidgh.messenger.LocaleController;
import org.saeidgh.messenger.R;

public class SelectColorActivity extends ActionBarActivity {
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);
        gridview=(GridView)findViewById(R.id.grid_view);
        gridview.setAdapter(new SelectColorAdapter(this));
        setTitle(LocaleController.getString("Themes", R.string.Themes));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ThemeChanger.ChangeTheme(ThemeChanger.LoadThemes().get(i));
                finish();
            }
        });
    }


}
