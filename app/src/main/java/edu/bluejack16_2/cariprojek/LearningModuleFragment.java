package edu.bluejack16_2.cariprojek;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningModuleFragment extends Fragment {


    public LearningModuleFragment() {
        // Required empty public constructor
    }

    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_learning_module, container, false);
        gridView = (GridView) view.findViewById(R.id.gridProject);
        final GridViewProjectAdapter gridViewProjectAdapter = new GridViewProjectAdapter(getContext());
        gridView.setAdapter(gridViewProjectAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailModuleActivity.class);
                intent.putExtra("CATEGORY",gridViewProjectAdapter.getItem(position).toString());
                startActivity(intent);
            }
        });
        return view;
    }


}
