package pcedev.bencomu.VolleyUtils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pcedev.bencomu.Models.FBPost;

//import Models.FBPost;

/**
 * Created by perecullera on 2/12/15.
 */
public class Requests {


    //https://graph.facebook.com/247118052078814/posts?fields=message,picture&limit=10&access_token=
    // 718141334984518%7C2de9dd91f69660269bf70f0ac39208e0
    private static final String TOKEN =
            "718141334984518%7C2de9dd91f69660269bf70f0ac39208e0";
    private static final String FBGraphUrl = "https://graph.facebook.com";
    private static final String UrlParameters = "/247118052078814/feed?fields=message,picture&limit=10&access_token=";
    private static final String RECENT_API_ENDPOINT = FBGraphUrl + UrlParameters +TOKEN;

    Singleton helper = Singleton.getInstance();
    final ArrayList<FBPost> FBArray = new ArrayList<>();

    public ArrayList<FBPost> loadFBPosts() {
        CustomJsonRequest request = new CustomJsonRequest
                (Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //Debug
                            System.out.println(response);
                            String message, picture, id;


                            JSONArray responseArray = response.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {
                                FBPost fbpost = new FBPost();
                                JSONObject o = responseArray.getJSONObject(i);
                                fbpost.id = o.getString("id");
                                fbpost.message = o.getString("message");
                                fbpost.picture = o.getString("picture");
                                FBArray.add(fbpost);
                            }

                        } catch (Exception e) {
                            Log.d("", e.toString());
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("", error.toString());
                    }
                });

        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
        return FBArray;

    }
}
