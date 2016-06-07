package pcedev.bencomu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import pcedev.bencomu.Models.RssItem;
import pcedev.bencomu.R;

/**
 * Created by perecullera on 6/6/16.
 */
public class RssFragment extends Fragment {

    ContentAdapter adapter;
    ArrayList<RssItem> rssFeed = new ArrayList<>();
    String url = "http://encomupodem.cat/feed/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        adapter = new ContentAdapter(rssFeed);

        getRssFeed();

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        return recyclerView;
    }

    private void getRssFeed() {
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parseXml(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                    }
                }
        );

    }
    private void parseXml(String response){

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

        private ArrayList<RssItem> rssArray;

        public ContentAdapter(ArrayList<RssItem> array) {
            rssArray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
            if (rssArray.size()>0 ){
                TextView id = (TextView) holder.itemView.findViewById(R.id.txtUrl);
                TextView message = (TextView) holder.itemView.findViewById(R.id.txtStatusMsg);
                ImageView image = (ImageView) holder.itemView.findViewById(R.id.feedImage1);
                /*id.setText(rssArray.get(position).id);
                message.setText(rssArray.get(position).message);

                NetworkImageView nv = (NetworkImageView) holder.itemView.findViewById(R.id.feedImage1);
                nv.setTag(rssArray.get(position).picture);
                //nv.setDefaultImageResId((ImageView) holder.itemView.findViewById(R.id.list_avatar)); // image for loading...
                if (rssArray.get(position).picture!=null){
                    nv.setImageUrl(String.valueOf(Uri.parse(rssArray.get(position).picture)), helper.getImageLoader());
                }*/
                //ImgController from your code
                //image.setImageURI(Uri.parse(fbarray.get(position).picture));
            }else {

            }

        }

        @Override
        public int getItemCount() {
            return rssArray.size();
        }
    }
}
