package pcedev.bencomu.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import pcedev.bencomu.Models.Candidat;
import pcedev.bencomu.R;
import pcedev.bencomu.VolleyUtils.Singleton;

/**
 * Created by perecullera on 6/6/16.
 */
public class CandidFragment extends Fragment {
    Candidat candid;
    static Singleton helper = Singleton.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strtext = getArguments().getString("nom");
        Bundle bundle = getArguments();
        candid= (Candidat) bundle.getSerializable("value");
        Log.e("division TEST", "" + candid.get_nom());
/*
        TextView tv = (TextView) getView().findViewById(R.id.candid_name);
        String nom = candid.get_nom();
        tv.setText(nom);*/

        return inflater.inflate(R.layout.candid_item, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) getView().findViewById(R.id.candid_name);
        String nom = candid.get_nom();
        tv.setText(nom);
       NetworkImageView nv = (NetworkImageView) getView().findViewById(R.id.candid_img);
        //nv.setDefaultImageResId((ImageView) holder.itemView.findViewById(R.id.list_avatar)); // image for loading...
        nv.setImageUrl(String.valueOf(Uri.parse(candid.foto)), helper.getImageLoader());

        TextView tvDesc = (TextView)getView().findViewById(R.id.candid_desc);
        tvDesc.setText(candid.descripcio);

    }
}
