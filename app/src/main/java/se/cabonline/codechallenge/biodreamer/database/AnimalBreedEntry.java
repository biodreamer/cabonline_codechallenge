package se.cabonline.codechallenge.biodreamer.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnimalBreedEntry
{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "breed")
    public String breed;

    @ColumnInfo(name = "imageUri")
    public String imageUri;
}