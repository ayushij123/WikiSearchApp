package com.aj.wikisearchapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aj.wikisearchapp.BaseConstant;
import com.aj.wikisearchapp.IApiMethods;
import com.aj.wikisearchapp.remote.Note;
import com.aj.wikisearchapp.R;
import com.aj.wikisearchapp.util.Utils;
import com.aj.wikisearchapp.adapter.WikiAdapter;
import com.aj.wikisearchapp.model.Page;
import com.aj.wikisearchapp.model.SearchResult;
import com.aj.wikisearchapp.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Retrofit retrofitInitialisation;
    public static final int ADD_NOTE_REQUEST = 1;
    private MainViewModel mainViewModel;
    private WikiAdapter wikiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        RecyclerView recyclerView = findViewById(R.id.rv_wiki);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        wikiAdapter = new WikiAdapter(MainActivity.this);
        recyclerView.setAdapter(wikiAdapter);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update our RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_LONG).show();
//                wikiAdapter.setNotes(notes);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Initializes Retrofit
     */
    public IApiMethods initializeRetrofit() {
        if (retrofitInitialisation == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofitInitialisation = new Retrofit.Builder()
                    .baseUrl(BaseConstant.BASE_URL)
                    .client(Utils.getApiLogCat())
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInitialisation.create(IApiMethods.class);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);

        final MenuItem searchItem = menu.findItem(R.id.item_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.trim().isEmpty())
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.trim().isEmpty())
               search(newText);
                return true;
            }
        });

        return true;
    }



    private void search(String query) {
        IApiMethods requestInterface = initializeRetrofit();
        Call<SearchResult> call = requestInterface.groupList("query", "json", "2",
                "prefixsearch", query, "20",
                "pageimages|pageterms", "thumbnail", "150", "10", "", "description");
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {

                SearchResult sr = response.body();
                List<Page> pages = sr.getQuery().getPages();
                wikiAdapter.setNotes(pages);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });
    }
}
