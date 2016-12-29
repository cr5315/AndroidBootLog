package me.butzow.bootlog.main;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import me.butzow.bootlog.R;

@DebugLog
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(android.R.id.text1) TextView textView;

    private MainRecyclerAdapter recyclerAdapter;
    private SharedPreferences preferences;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        recyclerAdapter = new MainRecyclerAdapter(this, callback, preferences.getBoolean(getString(R.string.key_sort_ascending), false));
        recyclerView.setAdapter(recyclerAdapter);
    }

    private MainRecyclerAdapter.Callback callback = length -> textView.setVisibility(length > 0 ? View.GONE : View.VISIBLE);

    @Override
    protected void onDestroy() {
        recyclerAdapter.close();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                preferences.edit().putBoolean(getString(R.string.key_sort_ascending), recyclerAdapter.toggleSort()).apply();
                return true;
            case R.id.action_about:
                startAboutActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startAboutActivity() {
        new LibsBuilder()
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withActivityTitle(getString(R.string.app_name))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withFields(R.string.class.getFields())
                .withLibraries("aboutlibraries", "butterknife", "realm")
                .start(this);
    }
}