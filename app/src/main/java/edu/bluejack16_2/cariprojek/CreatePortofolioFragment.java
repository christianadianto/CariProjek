package edu.bluejack16_2.cariprojek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.cariprojek.Controllers.PortofolioController;
import edu.bluejack16_2.cariprojek.Models.Portofolio;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePortofolioFragment extends Fragment implements View.OnClickListener{


    EditText txtName;
    EditText txtDescription;
    EditText txtUrl;
    Spinner spCategory;
    Button btnSubmit;

    public CreatePortofolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_portofolio, container, false);

        txtName = (EditText) view.findViewById(R.id.txtNamePortofolio);
        txtUrl = (EditText) view.findViewById(R.id.txtUrlPortofolio);
        txtDescription = (EditText) view.findViewById(R.id.txtDescriptionPortofolio);
        spCategory = (Spinner) view.findViewById(R.id.spinnerCategoryPortofolio);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmitPortofolio);

        btnSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmit){
            String name = txtName.getText().toString();
            String url = txtUrl.getText().toString();
            String description = txtDescription.getText().toString();
            String owner = new Session(getContext()).getUser().getEmail();
            String timestamp = String.valueOf(System.currentTimeMillis()/1000);
            String category = spCategory.getSelectedItem().toString();

            Portofolio portofolio = new Portofolio(owner,name,category,description,url,timestamp,"");
            PortofolioController.insertPortofolio(portofolio);
            Toast.makeText(getActivity(), "Success to create portofolio", Toast.LENGTH_SHORT).show();

        }
    }
}
