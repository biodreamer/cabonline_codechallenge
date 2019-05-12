package se.cabonline.codechallenge.biodreamer.dogapi;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;

public class DogApiClient
{
    private JSONObject getRequest(String requestUrl)
    {
        URLConnection urlConn;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(requestUrl);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            JSONObject response = new JSONObject(stringBuffer.toString());

            String status = response.getString("status");
            if("success".equals(status))
            {
                return response;
            }
            else
            {
                return null;
            }
        }
        catch(Exception ex)
        {
            Log.e("App", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public @NonNull List<String> getAllBreeds()
    {
        List<String> breeds = new ArrayList<>();
        String sBreed;
        String url="https://dog.ceo/api/breeds/list/all";
        JSONObject message = getRequest(url);
        JSONArray subBreed;
        if(message != null)
        {
           try
            {
                message = message.getJSONObject("message");
                Iterator<String> i = message.keys();
                while(i.hasNext())
                {
                    sBreed = i.next();
                    subBreed = message.getJSONArray(sBreed);
                    if(subBreed.length() > 0)
                    {
                        for(int j = 0; j < subBreed.length();j++)
                        {
                            breeds.add(sBreed + "-" + subBreed.getString(j));
                        }
                    }
                    else
                    {
                        breeds.add(sBreed);
                    }
                }
            }
            catch(Exception ignore)
            {
            }
        }
        return breeds;
    }

    public @NonNull String getBreedImageUri(String breed)
    {
        String result = "";
        String url = "https://dog.ceo/api/breed/"+ breed +"/images/random";
        JSONObject response = getRequest(url);
        if(response != null)
        {
            try
            {
                result = response.getString("message");
            }
            catch (Exception ignore)
            {
            }
        }
        return result;
    }
}
