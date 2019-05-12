package se.cabonline.codechallenge.biodreamer.database;

import android.os.AsyncTask;

import java.util.List;

import se.cabonline.codechallenge.biodreamer.dogapi.DogApiClient;

public class AsyncUpdateAllBreeds extends AsyncTask<DogBreedsDao,Void,Void>
{
    @Override
    protected Void doInBackground(DogBreedsDao... param)
    {
        if(param.length > 0)
        {
            DogBreedsDao dao = param[0];
            List<String> localBreeds = dao.getAllBreedsList();
            DogApiClient client = new DogApiClient();
            List<String> breeds = client.getAllBreeds();
            AnimalBreedEntry entry;
            String image;

            for(String breed : localBreeds) //Remove deprecated entries
            {
                if(!breeds.contains(breed))
                {
                    dao.deleteBreed(breed);
                }
            }

            for(String breed : breeds) //Add new entries
            {
                if(localBreeds.contains(breed)) continue;
                image = client.getBreedImageUri(breed);
                if(!image.isEmpty())
                {
                    entry = new AnimalBreedEntry();
                    entry.imageUri = image;
                    entry.breed = breed;
                    /*We have two cases here either we have a user who is new and we need to push something to the screen ASAP!
                     * Test shows me that this list contain 133 breeds and  sub breeds each requiring an api before we can fetch a image,
                     * this add up to a to long delay before we are ready to show something if we do them in one operation
                     *
                     * The second case is when the list has been updated and we should expect only a few entries to have changed
                     * since last time. it's not like new dog breeds is added or removed very often. Not enough reason to change
                      * behaviour in this case it will only run once per new addition*/
                    dao.insertAll(entry);
                }
            }
        }
        return null;
    }
}
