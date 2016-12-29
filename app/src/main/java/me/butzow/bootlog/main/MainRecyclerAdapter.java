package me.butzow.bootlog.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.butzow.bootlog.R;
import me.butzow.bootlog.realm.Boot;

@DebugLog
class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    private Callback callback;
    private Context context;
    private Realm realm;
    private RealmResults<Boot> boots;
    private Sort sort;

    interface Callback {
        void onDataLoaded(int length);
    }

    MainRecyclerAdapter(@NonNull Context context, Callback callback, boolean ascending) {
        this.callback = callback;
        this.context = context;

        realm = Realm.getDefaultInstance();

        if (ascending) {
            sort = Sort.ASCENDING;
        } else {
            sort = Sort.DESCENDING;
        }

        update();
    }

    void close() {
        realm.close();
    }

    boolean toggleSort() {
        boolean ascending;

        if (sort == Sort.ASCENDING) {
            ascending = false;
            sort = Sort.DESCENDING;
        } else {
            ascending = true;
            sort = Sort.ASCENDING;
        }

        update();

        return ascending;
    }

    private void update() {
        realm.where(Boot.class).findAllSortedAsync("date", sort)
                .addChangeListener(results -> {
                    boots = results;
                    notifyDataSetChanged();
                    callback.onDataLoaded(getItemCount());
                });
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.setText(DateFormat.getDateTimeInstance().format(boots.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        if (boots == null) return 0;
        return boots.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(android.R.id.text1) TextView textView;

        MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setText(String text) {
            textView.setText(text);
        }
    }
}