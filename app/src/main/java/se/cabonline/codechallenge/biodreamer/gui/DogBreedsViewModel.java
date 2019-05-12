package se.cabonline.codechallenge.biodreamer.gui;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import se.cabonline.codechallenge.biodreamer.database.AnimalBreedEntry;
import se.cabonline.codechallenge.biodreamer.database.AsyncUpdateAllBreeds;
import se.cabonline.codechallenge.biodreamer.database.DogBreedsDao;
import se.cabonline.codechallenge.biodreamer.database.DogBreedsDatabase;

public class DogBreedsViewModel extends ViewModel
{
    private DogBreedsDao dao;
    void init(Context context)
    {
        dao = Room.databaseBuilder(context.getApplicationContext(),
                DogBreedsDatabase.class, "dogBreeds").build().dogBreedDao();
    }

    private LiveData<List<AnimalBreedEntry>> dogBreeds;
    public LiveData<List<AnimalBreedEntry>> getBreeds()
    {
        if (dogBreeds == null)
        {
            dogBreeds = dao.getAllBreeds();
            loadBreeds();
        }
        return dogBreeds;
    }

    private void loadBreeds()
    {
        new AsyncUpdateAllBreeds().execute(dao);
    }
}
