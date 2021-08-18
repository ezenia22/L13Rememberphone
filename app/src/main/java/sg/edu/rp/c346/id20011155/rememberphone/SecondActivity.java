package sg.edu.rp.c346.id20011155.rememberphone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Phone> phoneList;
    EditText etName, etNumber;
    Button btnInsert, btnDlt;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = findViewById(R.id.lv);
        btnInsert = findViewById(R.id.enterBtn);
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        btnDlt = findViewById(R.id.dltBtn);

        DBHelper dbh = new DBHelper(this);
        phoneList = dbh.getAllNumbers();

        dbh.close();

        adapter = new CustomAdapter(this,R.layout.row,phoneList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.insert, null);

                Phone currentPhone = phoneList.get(position);
                final EditText etName = viewDialog.findViewById(R.id.etNewName);
                final EditText etNumber = viewDialog.findViewById(R.id.etNewNumber);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Edit Phone Number");
                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String number = etNumber.getText().toString().trim();
                        if (name.length()==0 || number.length() == 0){
                            Toast.makeText(SecondActivity.this, "Data not completed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBHelper dbh = new DBHelper(SecondActivity.this);

                        dbh.updatePhone(name, number, currentPhone);
                        for (int i = 0; i < phoneList.size(); i++) {
                            if (phoneList.get(i).getName().equalsIgnoreCase(currentPhone.getName())) {
                                Phone updatedPhone = phoneList.get(i);
                                updatedPhone.setName(name);
                                updatedPhone.setNumbers(number);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        dbh.close();
                        Toast.makeText(SecondActivity.this, "Number Edited", Toast.LENGTH_LONG).show();

                        etName.setText("");
                        etNumber.setText("");
                    }
                });
                    myBuilder.setNegativeButton("cancel", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
            });

            btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString().trim();
                    String number = etNumber.getText().toString().trim();
                    if (name.length() == 0 || number.length() == 0) {
                        Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DBHelper dbh = new DBHelper(SecondActivity.this);

                    int count = 0;
                    for (int i = 0; i < phoneList.size(); i++) {
                        count = i;
                    }
                    long result = dbh.insertSong(name, number);
                    phoneList.add(new Phone(count+1, name, number));
                    adapter.notifyDataSetChanged();
                    dbh.close();

                    if (result != -1) {
                        Toast.makeText(SecondActivity.this, "Number inserted", Toast.LENGTH_LONG).show();
                        etName.setText("");
                        etNumber.setText("");
                    }
                }
            });

            btnDlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString().trim();
                    if (name.length() == 0) {
                        Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        for (int i = 0; i < phoneList.size(); i++) {
                            if (phoneList.get(i).getName().equalsIgnoreCase(name)) {
                                DBHelper dbh = new DBHelper(SecondActivity.this);
                                long result = dbh.deletePhone(phoneList.get(i).getId());
                                phoneList.remove(i);
                                adapter.notifyDataSetChanged();
                                dbh.close();
                                break;
                            }
                        }
                    }
                }
            });
    }
}