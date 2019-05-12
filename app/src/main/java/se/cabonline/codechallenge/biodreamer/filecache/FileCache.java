package se.cabonline.codechallenge.biodreamer.filecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

public class FileCache implements Target
{
    private final File root;
    private final String url;
    private final String name;

    public FileCache(Context context, String imageUrl)
    {
        root = context.getCacheDir();
        url = imageUrl;
        name = getImageNameFromUrl(url);
    }

    private String getImageNameFromUrl(String imageUrl)
    {
        String[] Split = imageUrl.split("/");
        if(Split.length >= 2)
        {
            return Split[Split.length - 2] + "-" + Split[Split.length - 1];
        }
        return imageUrl;
    }

    public String getImageCacheUrl()
    {
        String filePath = root.getPath() +"/"+ name;
        File image = new File(filePath);
        if(image.exists()) return filePath;
        else return url;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
    {
        try
        {
            if (root.exists())
            {
                File image = new File(root, name);
                FileOutputStream out = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();
            }
        }
        catch(Exception e)
        {
            Log.e(getClass().getSimpleName(),e.getMessage());
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable)
    {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable)
    {
    }
}
