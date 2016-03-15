package com.example.sari.ips_admin.activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.sari.ips_admin.R;
import com.example.sari.ips_admin.database.Database;
import com.example.sari.ips_admin.models.indoormapping.Building;
import com.example.sari.ips_admin.tools.Point;
import com.example.sari.ips_admin.tools.RectangleDB;

public class AddBuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    public void submitBuilding(View v){
        String lbx    = ((EditText) findViewById(R.id.addbuilding_text_lbx)).getText().toString();
        String lby    = ((EditText) findViewById(R.id.addbuilding_text_lby)).getText().toString();
        String rbx    = ((EditText) findViewById(R.id.addbuilding_text_rbx)).getText().toString();
        String rby    = ((EditText) findViewById(R.id.addbuilding_text_rby)).getText().toString();
        String ltx    = ((EditText) findViewById(R.id.addbuilding_text_ltx)).getText().toString();
        String lty    = ((EditText) findViewById(R.id.addbuilding_text_lty)).getText().toString();
        String rtx    = ((EditText) findViewById(R.id.addbuilding_text_rtx)).getText().toString();
        String rty    = ((EditText) findViewById(R.id.addbuilding_text_rty)).getText().toString();
        String name   = ((EditText) findViewById(R.id.addbuilding_text_name)).getText().toString();
        String width  = ((EditText) findViewById(R.id.addbuilding_text_width)).getText().toString();
        String height = ((EditText) findViewById(R.id.addbuilding_text_height)).getText().toString();

        if(!lbx.equals("") && !lby.equals("") && !rbx.equals("") &&
           !rby.equals("") && !ltx.equals("") && !lty.equals("") &&
           !rtx.equals("") && !rty.equals("") && !name.equals("") &&
           !width.equals("") && !height.equals("")){
            Point lb = new Point(Double.parseDouble(lbx),Double.parseDouble(lby));
            Point rb = new Point(Double.parseDouble(rbx),Double.parseDouble(rby));
            Point lt = new Point(Double.parseDouble(ltx),Double.parseDouble(lty));
            Point rt = new Point(Double.parseDouble(rtx),Double.parseDouble(rty));
            Building b = new Building(new RectangleDB(lt,rt,lb,rb),name,Double.parseDouble(width),
                    Double.parseDouble(height));
            Database.addBuilding(b);
            this.finish();
        }
    }
}
