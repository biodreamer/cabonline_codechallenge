package se.cabonline.codechallenge.biodreamer.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DogBreedsDao
{
    @Query("SELECT * FROM AnimalBreedEntry GROUP BY breed")
    LiveData<List<AnimalBreedEntry>> getAllBreeds();

    @Query("SELECT breed FROM AnimalBreedEntry")
    List<String> getAllBreedsList();

    //@Query("SELECT imageUri FROM AnimalBreedEntry WHERE breed = :breed")
    //List<String> getAllImagesForBreed(String breed);

    @Insert
    void insertAll(AnimalBreedEntry... breed);

    @Query("DELETE FROM AnimalBreedEntry WHERE Breed = :breed")
    void deleteBreed(String breed);
}
