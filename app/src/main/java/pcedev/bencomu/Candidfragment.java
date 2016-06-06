package pcedev.bencomu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pcedev.bencomu.raw.utils.Singleton;


/**
 * Created by perecullera on 5/6/16.
 */
public class CandidFragment extends Fragment {

    ContentAdapter adapter;
    static Singleton helper = Singleton.getInstance();
    ArrayList<Candidat> candidatsBcn = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        String result = loadJSONFromAsset();

        try {
            /*JSONObject jObject = new JSONObject(result);
            //debug
            System.out.println(jObject);*/
            JSONArray jArray = new JSONArray(result);
            System.out.println(jArray);
            JSONObject object = jArray.getJSONObject(0);
            JSONObject proviBcnobj = object.getJSONObject("provincies");

            JSONArray proviBcnArray = proviBcnobj.getJSONArray("Barcelona");
            candidatsBcn = getCandidates(proviBcnArray);


            JSONArray proviTgnArray = proviBcnobj.getJSONArray("Tarragona");
            ArrayList<Candidat> candidatsTgn = getCandidates(proviTgnArray);

            JSONArray proviLleiArray = proviBcnobj.getJSONArray("Tarragona");
            ArrayList<Candidat> candidatsLlei = getCandidates(proviLleiArray);

            JSONArray proviGiArray = proviBcnobj.getJSONArray("Tarragona");
            ArrayList<Candidat> candidatsGi = getCandidates(proviLleiArray);


            System.out.println(proviBcnArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ContentAdapter(candidatsBcn);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        return recyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is  = getActivity().getAssets().open("candidatura.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    public ArrayList <Candidat> getCandidates(JSONArray array){
        ArrayList<Candidat> candidats = new ArrayList<Candidat>();
        for (int i=0; i < array.length(); i++)
        {
            try {
                JSONObject oneObject = array.getJSONObject(i);
                // Pulling items from the array
                String nom = oneObject.getString("nom");
                String descripcio = oneObject.getString("descripcio");
                String foto = oneObject.getString("foto");
                Candidat candidat = new Candidat (nom, descripcio, foto);
                candidats.add(candidat);
            } catch (JSONException e) {
                // Oops
                e.printStackTrace();
            }
        }
        return candidats;
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

        private ArrayList<Candidat> candidatsArray;

        public ContentAdapter(ArrayList<Candidat> array) {
            candidatsArray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
            if (candidatsArray.size()>0 ){
                TextView id = (TextView) holder.itemView.findViewById(R.id.txtUrl);
                TextView message = (TextView) holder.itemView.findViewById(R.id.txtStatusMsg);
                ImageView image = (ImageView) holder.itemView.findViewById(R.id.feedImage1);
                id.setText(candidatsArray.get(position).nom);
                message.setText(candidatsArray.get(position).descripcio);

                NetworkImageView nv = (NetworkImageView) holder.itemView.findViewById(R.id.feedImage1);
                nv.setTag(candidatsArray.get(position).foto);
                //nv.setDefaultImageResId((ImageView) holder.itemView.findViewById(R.id.list_avatar)); // image for loading...
                if (candidatsArray.get(position).foto!=null){
                    nv.setImageUrl(String.valueOf(Uri.parse(candidatsArray.get(position).foto)), helper.getImageLoader());
                }
                //ImgController from your code
                //image.setImageURI(Uri.parse(fbarray.get(position).picture));
            }else {

            }

        }

        @Override
        public int getItemCount() {
            return candidatsArray.size();
        }
    }

}
