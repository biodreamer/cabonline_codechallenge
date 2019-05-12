package se.cabonline.codechallenge.biodreamer.gui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import se.cabonline.codechallenge.biodreamer.database.AnimalBreedEntry;
import se.cabonline.codechallenge.biodreamer.filecache.FileCache;
import se.cabonline.codechallenge.biodreamer.gui.DogBreedsViewModel;

public class DogBreedRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Observer<List<AnimalBreedEntry>>
{
    private List<AnimalBreedEntry> breedList;
    public DogBreedRecycleViewAdapter(LifecycleOwner Owner, DogBreedsViewModel model)
    {
        model.getBreeds().observe(Owner,this);
    }

    @Override
    public void onChanged(@Nullable final List<AnimalBreedEntry> newBreedList)
    {
        // Update the UI, in this case, a TextView.
        breedList = newBreedList;
        notifyDataSetChanged();
    }

    @Override
    public @NonNull RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ImageView view = new ImageView(parent.getContext());
        return new DogBreedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof DogBreedViewHolder && position < breedList.size())
        {
            ((DogBreedViewHolder) holder).bind(breedList.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        if(breedList == null) return 0;
        return breedList.size();
    }

    class DogBreedViewHolder extends RecyclerView.ViewHolder
    {
        DogBreedViewHolder(@NonNull ImageView itemView)
        {
            super(itemView);
        }

        void bind(AnimalBreedEntry item)
        {
            itemView.setTag(item);
            itemView.setContentDescription(item.breed);
            if(itemView instanceof ImageView)
            {
                FileCache imageCache = new FileCache(itemView.getContext(),item.imageUri);
                String fileUrl = imageCache.getImageCacheUrl();
                RequestCreator image = Picasso.get().load(fileUrl);
                image.into((ImageView) itemView);
                if(item.imageUri.equals(fileUrl))
                {
                    image.into(imageCache);
                }
            }
        }
    }
}
