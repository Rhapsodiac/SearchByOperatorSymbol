package co.bhcc.googlelikeapro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Taylor on 12/21/2015.
 */
public class OperatorList {
    //Operator data initially from string resources
    public static final int NUM_OPERS = 14;

    public static final String DB_NAME = "operatorlist2.db";
    public static final int    DB_VERSION = 1;

    // list table constants
    public static final String OPERATOR_TABLE = "operators";

    public static final String OPERATOR_ID = "operID";
    public static final int    OPERATOR_ID_COL = 0;

    public static final String OPERATOR_SYMBOL = "operSymbol";
    public static final int    OPERATOR_SYMBOL_COL = 1;

    public static final String OPERATOR_NOTE = "operNote";
    public static final int    OPERATOR_NOTE_COL = 2;

    public static final String CREATE_OPERATOR_TABLE =
            "CREATE TABLE " + OPERATOR_TABLE + " (" +
                    OPERATOR_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    OPERATOR_SYMBOL + " TEXT," +
                    OPERATOR_NOTE + " TEXT)";

    public static final String DROP_OPERATOR_TABLE ="DROP TABLE IF EXISTS " + OPERATOR_TABLE;

    private static class DBHelper extends SQLiteOpenHelper{
        Context context;

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context = context; //necessary?
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_OPERATOR_TABLE);

            Resources res = context.getResources();
            String[] operText = res.getStringArray(R.array.oper_text);
            String[] operHelps = res.getStringArray(R.array.oper_help_array);

            for (int c = 1; c <= NUM_OPERS; c++) {
                db.execSQL("INSERT INTO operators VALUES(" + c + ", '" + operText[c-1] + "', '" + operHelps[c-1] + "')");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            db.execSQL(OperatorList.DROP_OPERATOR_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public OperatorList(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public ArrayList<Operator> getOperators(){
        ArrayList<Operator> opers = new ArrayList<>();
        openReadableDB();
        Cursor cursor = db.query(OPERATOR_TABLE, null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Operator oper = new Operator(cursor.getInt(OPERATOR_ID_COL), cursor.getString(OPERATOR_SYMBOL_COL), cursor.getString(OPERATOR_NOTE_COL));
            opers.add(oper);
        }

        cursor.close();
        closeDB();
        return opers;
    }

    public Operator getOperator(int num){
        String where = OPERATOR_ID + "= ?";
        String[] whereArgs = { Integer.toString(num) };

        openReadableDB();
        Cursor cursor = db.query(OPERATOR_TABLE, null,
                where, whereArgs, null, null, null);
        Operator oper = null;
        cursor.moveToFirst();
        oper = new Operator(cursor.getInt(OPERATOR_ID_COL), cursor.getString(OPERATOR_SYMBOL_COL), cursor.getString(OPERATOR_NOTE_COL));

        cursor.close();
        this.closeDB();

        return oper;
    }


}
