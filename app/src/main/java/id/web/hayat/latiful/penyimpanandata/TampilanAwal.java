package id.web.hayat.latiful.penyimpanandata;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TampilanAwal extends AppCompatActivity {

    EditText nama, nim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_awal);

        Spinner idSpinner =  findViewById(R.id.idSpinner);

        //Dftar isisn dalam bentuk String
        String[] daftarIsian = {
                "Teknik Elektro",
                "Teknik Sipil",
                "Tknik Informatika",
                "Teknik Kimia"
        };

        //Adapter
        ArrayAdapter<String> adapterIsian = new ArrayAdapter<String>(

                this, android.R.layout.simple_spinner_item, daftarIsian
        );

        idSpinner.setAdapter(adapterIsian);

        String prodiyangdipilih = idSpinner.getSelectedItem().toString();

        nama = findViewById(R.id.idNama);
        nim = findViewById(R.id.idNIM);


        Button idSave = (Button) findViewById(R.id.idSimpanPref);
        idSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                //jenis sifatnya bisa di-edit
                SharedPreferences.Editor edit = sp.edit();
                //isikan nama pada preference
                edit.putString("NamaDiSimpan", nama.getText().toString() );
                edit.putString("NIMDiSimpan", nim.getText().toString() );

                //simpan
                edit.commit();
            }
        });

    }

    public void idBacaKlik(View view) {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        nama.setText(sp.getString("NamaDiSimpan", ""));
        nim.setText(sp.getString("NIMDiSimpan", ""));
    }

    public void idSaveInternal (View view){
        byte[] byteNama = new byte[30];
        byte[] byteNIM = new byte[30];

        StringToBytes(byteNama, nama.getText().toString());
        StringToBytes(byteNIM, nim.getText().toString());

        try{
            FileOutputStream file =
                    openFileOutput("dataMhs.txt", MODE_PRIVATE /*MODE_APPEND*/);
            DataOutputStream data =
                    new DataOutputStream(file);

            data.write(byteNama);
            data.write(byteNIM);

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void idBacaInternal (View view){
        String dataString = "";
        byte[] byteNama = new byte[30];
        byte[] byteNIM = new byte[30];

        try {
            FileInputStream file = openFileInput("dataMhs.txt");
            DataInputStream data =
                    new DataInputStream(file);


            String StringNama="";
            String StringNIM="";

            while (data.available() > 0){
                data.read(byteNama);
                data.read(byteNIM);

                for(byte i = 0;i<byteNama.length;i++)
                    StringNama += (char)byteNama[i];

                for(byte i = 0;i<byteNIM.length;i++)
                    StringNIM += (char)byteNIM[i];

            }
            file.close();

            nama.setText(StringNama);
            nim.setText(StringNIM);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void idSaveExternal (View view){
        byte[] byteNama = new byte[30];
        byte[] byteNIM = new byte[30];

        StringToBytes(byteNama, nama.getText().toString());
        StringToBytes(byteNIM, nim.getText().toString());

        try{
            File fileName =
                    new File(Environment.getExternalStorageDirectory(),
                            "dataMhs.txt");
            FileOutputStream file =
                    new FileOutputStream(fileName);
            DataOutputStream data =
                    new DataOutputStream(file);

            data.write(byteNama);
            data.write(byteNIM);

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void idBacaExternal (View view){
        String dataString = "";
        byte[] byteNama = new byte[30];
        byte[] byteNIM = new byte[30];

        try {
            File fileName =
                    new File(Environment.getExternalStorageDirectory(),
                            "dataMhs.txt");
            FileInputStream file =
                    new FileInputStream(fileName);
            DataInputStream data =
                    new DataInputStream(file);


            String StringNama="";
            String StringNIM="";

            while (data.available() > 0){
                data.read(byteNama);
                data.read(byteNIM);

                for(byte i = 0;i<byteNama.length;i++)
                    StringNama += (char)byteNama[i];

                for(byte i = 0;i<byteNIM.length;i++)
                    StringNIM += (char)byteNIM[i];

            }
            file.close();

            nama.setText(StringNama);
            nim.setText(StringNIM);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void StringToBytes(byte[] byteVal, String stringVal){
        for(byte i=0;i<byteVal.length;i++)
            byteVal[i] = 0;
        for (byte i=0;i<stringVal.length();i++)
            byteVal[i] = (byte)stringVal.charAt(i);
    }

    public void idSimpanSQLKlik(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.addContact(new DataRecord(1,nama.getText().toString(), nim.getText().toString()));
    }
    public void idBacaSQLKlik(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        List<DataRecord> contacts = db.getAllContacts();

        for (DataRecord cn : contacts) {
            String log = "Id: " + cn.id + " ,Nama: " + cn.nama + " ,NIM: " + cn.nim;
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        /*
        DatabaseHandler db = new DatabaseHandler(this);
        //db.addContact(new DataRecord(1,nama.getText().toString(), nim.getText().toString()));
        DataRecord record = null;
        db.getContact(1);
        if(record != null) {
            nama.setText(record.nama);
            nim.setText(record.nim);
        }*/
    }

}
