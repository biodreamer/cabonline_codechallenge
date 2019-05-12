package se.cabonline.codechallenge.biodreamer.gui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import se.cabonline.codechallenge.biodreamer.R;
import se.cabonline.codechallenge.biodreamer.gui.adapter.DogBreedRecycleViewAdapter;

public class DogBreedsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DogBreedsViewModel dogBreedsViewModel = ViewModelProviders.of(this).get(DogBreedsViewModel.class);
        dogBreedsViewModel.init(this);

        RecyclerView rv = findViewById(R.id.dogList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new DogBreedRecycleViewAdapter(this,dogBreedsViewModel));
    }
}
