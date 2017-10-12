package com.infovita.zywee.Supports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by madroid on 03-06-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 16;

    // Database Name
    public static final String DATABASE_NAME = "zywee";

    //    TABLE NAMES
    public static final String TABLE_TEST_CART = "tests_cart";  //Tests Cart
    public static final String TABLE_PACKAGE_CART = "packages_cart";  //Package Cart
    public static final String TABLE_DOCTOR_CART = "doctors_cart";  //Package Cart
    public static final String TABLE_EQUIPMENT_CART = "equipment_cart";//Equipment Cart
    public static final String TABLE_AMBULANCE_CART = "ambulance_cart";//Ambulance Cart
    public static final String TABLE_HOME_SERVICES_CART = "home_service_cart";//Home Services Cart

    //   Common column names
    public static final String KEY_ID = "id"; // 0
    public static final String KEY_ITEM_ID = "item_id"; // 1
    public static final String KEY_ITEM_NAME = "item_name"; // 2
    public static final String KEY_ITEM_PRICE = "item_price"; // 3
    public static final String KEY_ITEM_DISCOUNT = "discount"; // 4
    public static final String KEY_INSTITUTE_ID = "health_institute_id"; // 5
    public static final String KEY_INSTITUTE_NAME = "health_institute_name"; // 6
    private static final String KEY_DURATION = "duration";//8
    private static final String KEY_COUPON = "coupon";

    // TABLE_TEST_CART specific columns

    public static final String KEY_SPECIALIZATION_ID = "specialization_id"; // 7
    public final static String KEY_EQUIPMENT_COST_ID =  "equipment_cost_id";
    public final static String KEY_TRANSPORT =  "transport";
    public final static String KEY_PROVIDER_HOME_SERVICE_ID =  "provider_home_service_id";
    public final static String KEY_PROVIDER_SERVICE_CHARGE_ID =  "provider_service_charge_id";
    public final static String KEY_SERVICE_PROVIDER_ID =  "service_provider_id";
    public final static String KEY_COLLECTION_COST =  "collection_cost";
    private static final String TABLE_PRESCRIPTION_CART = "PrescriptionCartTable";
    private static final String KEY_HOME_TITLE = "title";



    //CREATE TABLES

    String CREATE_TEST_CART_TABLE = "CREATE  TABLE " + TABLE_TEST_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+
            KEY_ITEM_NAME + " VARCHAR ,"+
            KEY_ITEM_PRICE + " VARCHAR ,"+
            KEY_ITEM_DISCOUNT + " VARCHAR ,"+
            KEY_INSTITUTE_ID + " VARCHAR ,"+
            KEY_INSTITUTE_NAME + " VARCHAR ,"+
            KEY_SPECIALIZATION_ID + " VARCHAR,"+
            KEY_COUPON + " VARCHAR);";

    String CREATE_PACKAGE_CART_TABLE = "CREATE  TABLE " + TABLE_PACKAGE_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+
            KEY_ITEM_NAME + " VARCHAR ,"+
            KEY_ITEM_PRICE + " VARCHAR ,"+
            KEY_ITEM_DISCOUNT + " VARCHAR ,"+
            KEY_INSTITUTE_ID + " VARCHAR ,"+
            KEY_INSTITUTE_NAME + " VARCHAR);";

    String CREATE_DOCTOR_CART_TABLE = "CREATE  TABLE " + TABLE_DOCTOR_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+
            KEY_ITEM_NAME + " VARCHAR ,"+
            KEY_ITEM_PRICE + " VARCHAR ,"+
           // KEY_ITEM_DISCOUNT + " VARCHAR ,"+
            KEY_INSTITUTE_ID + " VARCHAR ,"+
            KEY_INSTITUTE_NAME + " VARCHAR);";


// Column names for equipment_cart



    String CREATE_EQUIPMENT_CART_TABLE = "CREATE  TABLE " + TABLE_EQUIPMENT_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ //0
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+ //1
            KEY_ITEM_NAME + " VARCHAR ,"+//2
            KEY_ITEM_PRICE + " VARCHAR ,"+//3
            KEY_INSTITUTE_ID + " VARCHAR ,"+//4
            KEY_INSTITUTE_NAME + " VARCHAR , "+//5
            KEY_EQUIPMENT_COST_ID + " VARCHAR  ," +//6
            KEY_TRANSPORT + " VARCHAR ," +//7
            KEY_DURATION + " VARCHAR);";//8


    // Column names for ambulance_cart

    public final static String KEY_START =  "start";
    public final static String KEY_DESTINATION =  "destination";
    public final static String KEY_DISTANCE_ID =  "'distance_id'";
    public final static String KEY_TOTAL_COST =  "total_cost";
    public final static String KEY_ACCESSORIES_LIST =  "accessories_list";
    public final static String KEY_PROVIDER_ID =  "provider_id";
    public final static String KEY_TITLE = "ambulance_name";

    String CREATE_AMBULANCE_CART_TABLE = "CREATE  TABLE " + TABLE_AMBULANCE_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ //0
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+ //1
            KEY_PROVIDER_ID + " VARCHAR ,"+ //2
            KEY_START + " VARCHAR ,"+//3
            KEY_DESTINATION + " VARCHAR ,"+//4
            KEY_DISTANCE_ID + " VARCHAR ,"+//5
            KEY_TOTAL_COST + " VARCHAR , "+//6
            KEY_ACCESSORIES_LIST + " VARCHAR, "+//7
            KEY_TITLE + " VARCHAR);";//8



    String CREATE_HOME_SERVICE_CART_TABLE = "CREATE  TABLE " + TABLE_HOME_SERVICES_CART + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ //0
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+ //1
            KEY_PROVIDER_HOME_SERVICE_ID + " VARCHAR ,"+ //2
            KEY_PROVIDER_SERVICE_CHARGE_ID + " VARCHAR ,"+//3
            KEY_SERVICE_PROVIDER_ID + " VARCHAR ,"+//4
            KEY_TOTAL_COST + " VARCHAR ,"+//5
            KEY_COLLECTION_COST + " VARCHAR, " +//6
            KEY_HOME_TITLE + " VARCHAR, " +//7
            KEY_DURATION + " VARCHAR);";//8

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(CREATE_TEST_CART_TABLE);
        db.execSQL(CREATE_PACKAGE_CART_TABLE);
        db.execSQL(CREATE_DOCTOR_CART_TABLE);
        db.execSQL(CREATE_EQUIPMENT_CART_TABLE);
        db.execSQL(CREATE_AMBULANCE_CART_TABLE);
        db.execSQL(CREATE_HOME_SERVICE_CART_TABLE);
        db.execSQL(CREATE_PRESCRIPTION_CART_TABLE);
        db.execSQL(CREATE_MANUAL_ENTRY_CART);
        Log.d("tables create","Table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMBULANCE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME_SERVICES_CART);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_PRESCRIPTION_CART);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_MANUAL_CART);
        onCreate(db);
    }

    //Insert value to the table

    public Boolean insertTableData(String table_name,ContentValues table_data){
        SQLiteDatabase sd = this.getWritableDatabase();

       try{
          sd.insertOrThrow(table_name,null,table_data);
           return true;
       }catch (Exception e){
           Log.d("sqldata_exp",e+"");
           return false;
       }

    }


    //Retrieve data from table
    public Cursor getTableData(String Table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from "+Table_name , null);
    }


    public static final String KEY_PRESCRIPTION_CART_ID = "cartId";
    public static final String KEY_PRESCRIPTION_IMAGE_CART_URI = "prescriptionUri"; // prescription image uri not null

    // add the detail for the Prescription Cart
    public void addZyweePrescriptionCartDetail(ZyweeData zyweeData) {
        SQLiteDatabase sd = this.getWritableDatabase();

        ContentValues valuesPrescriptionCartTable = new ContentValues();

        valuesPrescriptionCartTable.put(KEY_PRESCRIPTION_IMAGE_CART_URI, zyweeData.getPrescriptionImageUri().toString());

        sd.insert(TABLE_PRESCRIPTION_CART, null, valuesPrescriptionCartTable);
        sd.close();
    }
    //CREATE TABLES

    String CREATE_PRESCRIPTION_CART_TABLE = "CREATE  TABLE " + TABLE_PRESCRIPTION_CART + " ("
            + KEY_PRESCRIPTION_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_PRESCRIPTION_IMAGE_CART_URI + " TEXT);";




    //Delete table data
    public void clearTableData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + table_name);
    }

    public void removeTableData(String table_name,String column_name,String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from "+table_name+" where "+ column_name +" = " + id;
        db.execSQL(query);
       // db.close();
//        refreshTable();
    }

    /**
     * Give the number of rows in the table
     *
     * @param table Table name
     */
    public long fetchCartCount(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT COUNT(*) FROM " + table;
        SQLiteStatement statement = db.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        db.close();
        return count;
    }


    /**
     * Gives the number of prescriptions in the cart database
     */
    public long fetchPrescriptionCartCount() {
        long count = fetchCartCount(TABLE_PRESCRIPTION_CART);
        if (count != 0) return 1;
        else
            return count;
    }

    /***********************************************************************************************
     * Manual Entry Table Details
     * Creating table
     * adding data
     * deleting data
     * getting data
     **********************************************************************************************/
    public static final String TABLE_MANUAL_CART = "ManualEntryTable"; //Manual Entry table Name
    //    Columns
    public static final String KEY_TAG = "tag";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_NOTES = "notes";
    String CREATE_MANUAL_ENTRY_CART = "CREATE TABLE " + TABLE_MANUAL_CART + " ("
            + KEY_TAG + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_QUANTITY + " TEXT, "
            + KEY_NOTES + " TEXT);";

    // add the detail for the Manual Entry Cart
    public boolean addManualEntryDetail(ZyweeData zyweeData) {
        SQLiteDatabase sd = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TAG, zyweeData.get_tag().toString());
        values.put(KEY_DESCRIPTION, zyweeData.get_manual_description().toString());
        values.put(KEY_QUANTITY, zyweeData.get_manual_quantity().toString());
        values.put(KEY_NOTES, zyweeData.get_manual_notes().toString());


        long result = sd.insert(TABLE_MANUAL_CART, null, values);
        sd.close();

        if (result == -1) return false;
        else return true;
    }

    //remove the detail from the manual entry
    public boolean removeManualItem(String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_MANUAL_CART, KEY_TAG + "=" + tag, null);
        Log.d("DataBase", "Item removed from the manual cart");
        db.close();
        if (result == -1) return false;
        else return true;
    }

    //Fetch data from favourites
    public ArrayList<ManualItem> getManualEntryDetails() {
        ArrayList<ManualItem> items = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MANUAL_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ManualItem item = new ManualItem();
                item.setTag(cursor.getString(0));
                item.setDescrription(cursor.getString(1));
                item.setQuantity(cursor.getString(2));
                item.setNotes(cursor.getString(3));
                // Adding NonPrescriptions to list
                items.add(item);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return items;
    }

    /*
     * Gives the number of items in the manual entry cart
     */
    public long fetchManualCartCount() {
        long count = fetchCartCount(TABLE_MANUAL_CART);
        return count;
    }


    public ArrayList<PrescriptionCart> getPrescriptionCartDetail() {
        ArrayList<PrescriptionCart> array = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRESCRIPTION_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            PrescriptionCart prescriptionCart = new PrescriptionCart();
            prescriptionCart.setRow_id(cursor.getInt(0));
            prescriptionCart.setPrescriptionImage(cursor.getString(1));

            Log.d("Cart tables's data", prescriptionCart.getPrescriptionImage());
// Adding Non Prescriptions to list
            array.add(prescriptionCart);
        }
//        db.close();
        return array;
    }

    public Void deletePrescriptionCartItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRESCRIPTION_CART , KEY_PRESCRIPTION_CART_ID +  "=" + id, null);
        Log.d("DataBase", "Item removed from the table " + TABLE_PRESCRIPTION_CART);
        db.close();
        return null;
    }

    public void updatePrescriptionUri(ZyweeData zyweeData, int row_id) {
        SQLiteDatabase sd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PRESCRIPTION_IMAGE_CART_URI, zyweeData.getPrescriptionImageUri().toString());

        sd.update(TABLE_PRESCRIPTION_CART, contentValues, KEY_PRESCRIPTION_CART_ID + " = " + row_id, null);
        sd.close();
    }



    public void removeCartData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_PRESCRIPTION_CART);
        db.execSQL("delete from " + TABLE_MANUAL_CART);

        db.close();
    }

    // Returns the record count
    public int getRecordCount(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return 0;
        }
        return (int)(DatabaseUtils.queryNumEntries(db, table_name));
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
