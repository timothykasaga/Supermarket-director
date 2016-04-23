package com.victoria.timothykasaga.gorret;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Product_list extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> arrayList = new ArrayList();
    Button bAdd;
    Button bDone;
    final Context context = this;
    DetailsPack detailsPack;
    EditText edtpname;
    ListView list;
    ArrayList<Product> prodtArray = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        initialize();
        detailsPack = ((DetailsPack)getIntent().getParcelableExtra("com.timothykasaga.victoria.pack"));
        bAdd.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                if (edtpname.getText().toString().equals(""))
                {
                    arrayList.add(edtpname.getText().toString());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, arrayList);
                    list.setAdapter(arrayAdapter);
                    edtpname.setText("");
                }
            }
        });
        bDone.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent intent;
                if (getIntent().getStringExtra("flag").equals("modify")) {
                    intent = new Intent(Product_list.this, ModificationPage.class);

                }
               else{
                    intent = new Intent(Product_list.this, Supermarket_details.class);
                }
                intent.putParcelableArrayListExtra("com.timothykasaga.victoria.prodts", prodtArray);
                intent.putExtra("com.timothykasaga.victoria.returnedpack", detailsPack);
                intent.putExtra("flags", "list");
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize(){
        bAdd = ((Button)findViewById(R.id.btn_padd));
        bDone = ((Button)findViewById(R.id.btndone));
        edtpname = ((EditText)findViewById(R.id.edtpname));
        list = ((ListView)findViewById(R.id.list_prodts));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product list");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.home);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_list.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
