package pcedev.bencomu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
public class CandidListFragment extends Fragment {

    ExpandableCandAdapter adapter;
    static Singleton helper = Singleton.getInstance();
    ArrayList<Provincia> provincies = new ArrayList<>();
    ExpandableListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            Provincia provBcn = getCandidates(proviBcnArray, "Barcelona");
            provincies.add(provBcn);


            JSONArray proviTgnArray = proviBcnobj.getJSONArray("Tarragona");
            Provincia provTgn = getCandidates(proviTgnArray, "Tarragona");
            provincies.add(provTgn);

            JSONArray proviLleiArray = proviBcnobj.getJSONArray("Lleida");
            Provincia provLlei = getCandidates(proviLleiArray,"LLeida");
            provincies.add(provLlei);

            JSONArray proviGiArray = proviBcnobj.getJSONArray("Girona");
            Provincia provGi = getCandidates(proviGiArray, "Girona");
            provincies.add(provGi);

            System.out.println(proviBcnArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.prog_fragment, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.lvExp);
        adapter = new ExpandableCandAdapter(getActivity(),provincies);
        lv.setAdapter(adapter);
        lv.setGroupIndicator(null);
        adapter.notifyDataSetChanged();
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Candidat cd = provincies.get(groupPosition).candidats.get(childPosition);
                Bundle bundle = new Bundle();
                TextView tv = (TextView)v.findViewById(R.id.candid_list_name);
                bundle.putString("nom", (String) tv.getText());
                bundle.putSerializable("value", cd);
                // set Fragmentclass Arguments
               CandidFragment cf = new CandidFragment();
                cf.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, cf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

          /*      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, cf).commit();*/
                return true;
            }
        });


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
    public Provincia getCandidates(JSONArray array, String provincia){
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
        Provincia prov = new Provincia(provincia, candidats);
        return prov;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
