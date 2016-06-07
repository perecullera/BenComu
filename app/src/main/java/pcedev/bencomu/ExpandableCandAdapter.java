package pcedev.bencomu;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pcedev.bencomu.raw.utils.Singleton;

/**
 * Created by perecullera on 6/6/16.
 */
public class ExpandableCandAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Provincia> provList; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    static Singleton helper = Singleton.getInstance();

    public ExpandableCandAdapter(Context context, ArrayList<Provincia> provincies) {
        this._context = context;
        this.provList = provincies;
        //this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.provList.get(groupPosition).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Candidat candid =(Candidat) getChild(groupPosition, childPosition);

        final String childText = candid.nom;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.candid_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.candid_list_name);

        txtListChild.setText(childText);
        NetworkImageView nv = (NetworkImageView) convertView.findViewById(R.id.candid_list_img);
        //nv.setTag(fbarray.get(position).picture);
        //nv.setDefaultImageResId((ImageView) holder.itemView.findViewById(R.id.list_avatar)); // image for loading...
        nv.setImageUrl(String.valueOf(Uri.parse(candid.foto)), helper.getImageLoader());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.provList.get(groupPosition)
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return provList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return provList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Provincia prov = (Provincia) getGroup(groupPosition);
        String headerTitle = prov.nom;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.prog_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
