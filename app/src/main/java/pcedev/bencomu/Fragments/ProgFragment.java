package pcedev.bencomu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import pcedev.bencomu.Utils.ExpandableListAdapter;
import pcedev.bencomu.Models.MesuraProg;
import pcedev.bencomu.R;

/**
 * Created by perecullera on 5/6/16.
 */
public class ProgFragment extends Fragment {
    View rootView;
    ExpandableListView lv;
    ArrayList<MesuraProg> progArray = new ArrayList<>();
    ExpandableListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // preparing list data
        prepareListData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // recyclerView.setAdapter(new ExpandableListAdapter(getActivity(),progArray));
       /* adapter = new ExpandableListAdapter(getActivity(),progArray);
        lv = (ExpandableListView) findViewById(R.id.lvExp);
        lv.setAdapter(adapter);*/
        /*lv.setHasFixedSize(true);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));*/


        return inflater.inflate(R.layout.prog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.lvExp);
        adapter = new ExpandableListAdapter(getActivity(),progArray);
        lv.setAdapter(adapter);
        lv.setGroupIndicator(null);
        adapter.notifyDataSetChanged();

    }
    private void prepareListData() {
        MesuraProg mesura1 = new MesuraProg(
                "1 El referèndum és la millor solució","El 80% de la gent de Catalunya vol decidir el seu futur a través d’una consulta. Nosaltres ens hem compromès a defensar-lo perquè hi creiem, contribuint a què les forces que defensen el referèndum guanyin a Espanya per primera vegada a la història."
        );
        progArray.add(mesura1);
        MesuraProg mesura2 = new MesuraProg(
                "2 Pla de Rescat i emergència social","Combatre les desigualtats: primer recuperar drets (revertir la reforma laboral, garantir el dret a l’accés a uns serveis socials públics i de qualitat), i revertir retallades (educació i sanitat, llei dependència). I després, una fase d’ampliació: pobresa energètica, permís de paternitat igual i intransferible, renda garantida de ciutadania."
        );
        progArray.add(mesura2);
        MesuraProg mesura3 = new MesuraProg(
                "3 Creació d’ocupació que acabi amb la precarietat","Generar nova activitat econòmica i dinamitzar l’existent, i fomentar una ocupació digna que deixi enrere la precarietat. Derogació de les reformes laborals afavorint els sector econòmics més sostenibles. Lluita contra l’atur juvenil i el dels majors de 55 anys. Foment de la formació laboral."
        );
        progArray.add(mesura3);
        MesuraProg mesura4 = new MesuraProg(
                "4 Blindem els serveis públics, ampliem drets i benestar","Millorar el servei de salut, garantir una educació pública i democràtica, que respecti la immersió lingüística. Garantir l’equitat en l’accés. Facilitar l’accés a l’educació universitària amb reducció de taxes i ampliació de beques."
        );
        progArray.add(mesura4);

        MesuraProg mesura5 = new MesuraProg(
               "5 Lluita contra la corrupció i el paradisos fiscals","Lluita decidida contra la corrupció, els paradisos fiscals i pla de lluita contra el frau fiscal. Foment de la banca pública, les finances ètiques i les iniciatives cooperatives, així com sortir del corsé del deute. Implementar polítiques reals de transparència i rendició de comptes."
        );
        progArray.add(mesura5);

    }
}
