package com.example.note_app;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDBHalper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="NoteAppDB" ;
    private static int DATABASE_VERSON = 1;
    private static final String TABLE_NOTES = "NOTES";
    private static final String NOTE_ID ="note_id";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String DATE = "date";
    private static final String TIME = "time";


    // -------------------------------------------



    public MyDBHalper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NOTES + " ("
                    + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT,"
                    + BODY + " TEXT,"
                    + DATE + " TEXT,"
                    + TIME + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public int insert_note(String title,String body){

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                String date = LocalDate.now().toString();
//            }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Calendar calendar = Calendar.getInstance();
        String date = formatter.format(calendar.getTime());
        String time ="15:12 AM";


        SQLiteDatabase db = this.getWritableDatabase();

        String query= "insert into "+TABLE_NOTES +"( '"+TITLE+"','"+BODY+"','"+DATE+"','"+TIME+"') VALUES ('"+title+"','"+body+"','"+date+"','"+time+"')";
        try {
            Log.d(TAG, "insert_note: try -> "+query);
            db.execSQL(query);

        }catch (Exception e){
            Log.d(TAG, "insert_note: catch "+e.getMessage());
        }
        return  0;

    }

    public List<notes> getAllNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<notes> list = new ArrayList<>();
       Cursor cursor =  db.rawQuery("Select * from "+TABLE_NOTES,null);
//        for (int i=0;i<=cursor.getCount();i++){
//            cursor.moveToNext();
//            String title = cursor.getString(1);
//            String body = cursor.getString(2);
//            list.add(new notes(title,body));
//        }
        for (int i = 0;i<cursor.getCount();i++){
            if(cursor.moveToNext()){
                String title = cursor.getString(1);
                String body = cursor.getString(2);
                list.add(new notes(title,body));

            }else{

                list.add(new notes("no data","no body"));
            }
        }
        db.close();
        return  list;

    }
}
