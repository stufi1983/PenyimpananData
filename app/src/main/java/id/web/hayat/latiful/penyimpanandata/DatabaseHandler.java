package id.web.hayat.latiful.penyimpanandata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, "DataBaseMahasiswa", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE dataMahasiswa (id INTEGER, nama TEXT, nim TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versiLama, int versiBaru) {
        db.execSQL("DROP TABLE IF EXISTS dataMahasiswa");
        onCreate(db);
    }

    public void addContact(DataRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", record.id);
        values.put("nama", record.nama);
        values.put("nim", record.nim);
        db.insert("dataMahasiswa", null, values);
        db.close(); // Closing database connection
    }

    public DataRecord getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("dataMahasiswa", new String[] { "id",
                        "nama", "nim" }, "id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataRecord contact = new DataRecord(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
    public List<DataRecord> getAllContacts() {
        List<DataRecord> contactList = new ArrayList<DataRecord>();
        // Select All Query
        String selectQuery = "SELECT  * FROM dataMahasiswa";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataRecord contact = new DataRecord();

                contact.id = 0;
                try{
                    contact.id = cursor.getInt(0);
                }catch (Exception e){
                    Log.d("Name: ",e.getMessage());
                }
                contact.nama = (cursor.getString(1));
                contact.nim = (cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
