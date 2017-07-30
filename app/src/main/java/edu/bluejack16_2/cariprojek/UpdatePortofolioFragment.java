package edu.bluejack16_2.cariprojek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.PortofolioController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Portofolio;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePortofolioFragment extends Fragment implements View.OnClickListener{

    Session session;
    User user;
    Vector<Portofolio> portofolios;

    Spinner spinnerPortofolio;
    Spinner spinnerCategory;
    EditText txtPortofolioName;
    EditText txtUrl;
    EditText txtDescription;
    TextView tvKey;
    Vector<String> portofolioNames;
    Portofolio selectedPortofolio;

    Button btnUpdate;
    Button btnDelete;

    public UpdatePortofolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_portofolio, container, false);

        spinnerPortofolio = (Spinner) view.findViewById(R.id.spPortofolioList);
        spinnerCategory = (Spinner) view.findViewById(R.id.spCategoryListPortofolio);
        txtPortofolioName = (EditText) view.findViewById(R.id.txtUpdatePortofolioName);
        txtUrl = (EditText) view.findViewById(R.id.txtUpdateUrl);
        txtDescription = (EditText) view.findViewById(R.id.txtUpdateDescriptionPortofolio);
        tvKey = (TextView) view.findViewById(R.id.tvKeyPortofolio);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdatePortofolio);
        btnDelete = (Button) view.findViewById(R.id.btnDeletePortofolio);

        session = new Session(getContext());
        user = session.getUser();
        portofolioNames = new Vector<>();
        getPortofolioList();

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        return view;
    }

    public void getPortofolioList() {
        portofolioNames.clear();
        portofolios = PortofolioController.getPortofolioByEmail(user.getEmail());
        setProjectList();
    }

    public void setProjectList() {
        if(portofolios.size() == 0){
            toggleForm(false);
            portofolioNames.add("You don't have any projects");
        }
        else{
            toggleForm(true);
            for (Portofolio portofolio:portofolios) {
                portofolioNames.add(portofolio.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                portofolioNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPortofolio.setAdapter(adapter);
        spinnerPortofolio.getOnItemSelectedListener();
        spinnerPortofolio.setSelection(0);

        if(portofolios.size() != 0) projectSpinnerListener();

    }

    private void projectSpinnerListener() {
        spinnerPortofolio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Portofolio portofolio = portofolios.get(position);
                selectedPortofolio = portofolio;
                setFormWithProjectData(portofolio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setFormWithProjectData(Portofolio portofolio){
        txtPortofolioName.setText(portofolio.getName());
        txtDescription.setText(portofolio.getDescription());
        txtUrl.setText(portofolio.getUrl());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_project, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        int categoryIndex = adapter.getPosition(portofolio.getCategory());
        spinnerCategory.setSelection(categoryIndex);
    }


    private void toggleForm(boolean bool) {
        txtPortofolioName.setEnabled(bool);
        txtDescription.setEnabled(bool);
        txtUrl.setEnabled(bool);
        spinnerCategory.setEnabled(bool);
        toggleButton(bool);
    }

    private void toggleButton(boolean bool){
        if(bool) {
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else{
            btnUpdate.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnUpdate){
            String name = txtPortofolioName.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            String description = txtDescription.getText().toString();
            String url = txtUrl.getText().toString();

            String timestamp = String.valueOf((System.currentTimeMillis()/1000));

            selectedPortofolio.setName(name);
            selectedPortofolio.setCategory(category);
            selectedPortofolio.setUrl(url);
            selectedPortofolio.setDescription(description);
            selectedPortofolio.setTimestamp(timestamp);

            PortofolioController.updatePortofolio(selectedPortofolio);
        }
        else if(v == btnDelete){
            PortofolioController.deletePortofolio(selectedPortofolio);
        }
    }
}
