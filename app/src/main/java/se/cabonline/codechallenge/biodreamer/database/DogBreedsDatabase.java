package se.cabonline.codechallenge.biodreamer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AnimalBreedEntry.class}, version = 1, exportSchema = false)
public abstract class DogBreedsDatabase extends RoomDatabase
{
    public abstract DogBreedsDao dogBreedDao();
}
