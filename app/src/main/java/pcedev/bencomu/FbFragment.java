package pcedev.bencomu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pcedev.bencomu.raw.utils.CustomJsonRequest;
import pcedev.bencomu.raw.utils.Singleton;

/**
 * Created by perecullera on 25/5/16.
 */
public class FbFragment extends Fragment {
    //https://graph.facebook.com/247118052078814/posts?fields=message,picture&limit=10&access_token=
    // 718141334984518%7C2de9dd91f69660269bf70f0ac39208e0
    private static final String TOKEN =
            "718141334984518%7C2de9dd91f69660269bf70f0ac39208e0";
    private static final String FBGraphUrl = "https://graph.facebook.com";
    private static final String UrlParameters = "/247118052078814/posts?fields=message,picture&limit=10&access_token=";
    private static final String RECENT_API_ENDPOINT = FBGraphUrl + UrlParameters +TOKEN;

    static Singleton helper = Singleton.getInstance();
    final ArrayList<FBPost> FBArray = new ArrayList<>();
    ContentAdapter adapter;
    ArrayList<FBPost> fbposts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        //Requests req = new Requests();


        adapter = new ContentAdapter(fbposts);

        loadFBPosts();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        return recyclerView;

    }
    public void loadFBPosts() {
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
                                if(o.has("message")){
                                    fbpost.message = o.getString("message");
                                }
                                if (o.has("picture")){
                                    fbpost.picture = o.getString("picture");
                                }
                                fbposts.add(fbpost);

                            }

                        } catch (Exception e) {
                            Log.d("", e.toString());
                        }
                        adapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("", error.toString());
                    }
                });

        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //click to fb article detail
                    /*Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);

                    TextView Viewid = (TextView) itemView.findViewById(R.id.list_title);
                    String id = (String) Viewid.getText();
                    TextView ViewDesc = (TextView) itemView.findViewById(R.id.list_desc);
                    String title = (String) ViewDesc.getText();
                    intent.putExtra("id", id);
                    intent.putExtra("desc",title);
                    NetworkImageView image = (NetworkImageView) itemView.findViewById(R.id.list_avatar);
                    String imgUrl = (String) image.getTag();
                    intent.putExtra("img",imgUrl);
                    context.startActivity(intent);*/
                }
            });

        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        //private static final int LENGTH = 18;

        private ArrayList<FBPost> fbarray;

        public ContentAdapter(ArrayList<FBPost> array) {
            fbarray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
            if (fbarray.size()>0 ){
                TextView id = (TextView) holder.itemView.findViewById(R.id.list_title);
                TextView message = (TextView) holder.itemView.findViewById(R.id.list_desc);
                ImageView image = (ImageView) holder.itemView.findViewById(R.id.list_avatar);
                id.setText(fbarray.get(position).id);
                message.setText(fbarray.get(position).message);

                NetworkImageView nv = (NetworkImageView) holder.itemView.findViewById(R.id.list_avatar);
                nv.setTag(fbarray.get(position).picture);
                //nv.setDefaultImageResId((ImageView) holder.itemView.findViewById(R.id.list_avatar)); // image for loading...
                nv.setImageUrl(String.valueOf(Uri.parse(fbarray.get(position).picture)), helper.getImageLoader()); //ImgController from your code
                //image.setImageURI(Uri.parse(fbarray.get(position).picture));
            }else {

            }

        }

        @Override
        public int getItemCount() {
            return fbarray.size();
        }
    }

}
