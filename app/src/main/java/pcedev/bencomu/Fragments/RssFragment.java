package pcedev.bencomu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import pcedev.bencomu.Models.RssItem;
import pcedev.bencomu.R;
import pcedev.bencomu.VolleyUtils.Singleton;

/**
 * Created by perecullera on 6/6/16.
 */
public class RssFragment extends Fragment {

    ContentAdapter adapter;
    ArrayList<RssItem> rssFeed = new ArrayList<>();
    String url = "http://encomupodem.cat/feed/";
    static Singleton helper = Singleton.getInstance();

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
                        String err = error.toString();
                        Log.e("error", err);
                    }
                }
        ){
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        //req.setPriority(Request.Priority.HIGH);
        helper.add(req);

    }
    private void parseXml(String response){
        //Log.e("response", response);
        //For String source
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(response));

            xpp.nextTag();
            boolean insideItem = false;
            int eventType = xpp.getEventType();
            String title = null;
            String link = null;
            String description = null;
            while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {

                if (xpp.getEventType()==XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem){
                            title = xpp.nextText();
                            Log.e("title", title);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem){
                            link = xpp.nextText();
                            Log.e("title", link);
                        }
                    }else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem){
                            description = xpp.nextText();
                            Log.e("description", description);
                        }
                        //item finished
                        if(insideItem && title != null && link != null){
                            RssItem item = new RssItem(title, description, link );
                            rssFeed.add(item);
                            adapter.notifyDataSetChanged();
                            //reset title, descrip...
                            title = null;
                            link = null;
                            description = null;
                        }
                    }
                }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                    insideItem=false;
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.rss_item_layout, parent, false));

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
                TextView titulo = (TextView) holder.itemView.findViewById(R.id.titulo);
                TextView description = (TextView) holder.itemView.findViewById(R.id.descripcion);

                titulo.setText(rssArray.get(position).getTitle());
                description.setText(rssArray.get(position).getDescripcion());
                //ImageView image = (ImageView) holder.itemView.findViewById(R.id.feedImage1);
                /*id.setText(rssArray.get(position).id);


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
