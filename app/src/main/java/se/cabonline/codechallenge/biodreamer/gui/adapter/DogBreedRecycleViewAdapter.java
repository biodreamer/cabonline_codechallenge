package se.cabonline.codechallenge.biodreamer.gui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import se.cabonline.codechallenge.biodreamer.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.breed_list_item,parent,false);
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
        private final ImageView image;
        private final TextView label;
        DogBreedViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            label = itemView.findViewById(R.id.label);
        }

        void bind(AnimalBreedEntry item)
        {
            itemView.setTag(item);
            image.setContentDescription(item.breed);
            label.setText(item.breed);

            FileCache imageCache = new FileCache(itemView.getContext(),item.imageUri);
            String fileUrl = imageCache.getImageCacheUrl();
            RequestCreator imageCreator = Picasso.get().load(fileUrl);
            imageCreator.into(image);
            /*if(item.imageUri.equals(fileUrl))
            {
                imageCreator.into(imageCache);
            }*/
        }
    }
}
