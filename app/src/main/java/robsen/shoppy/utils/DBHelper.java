package robsen.shoppy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import robsen.shoppy.model.Price;


/**
 * Created by robeschm on 15.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Properties
    private static final String TAG = "DATABASE OPERATIONS";
    private static final String DATABASE_NAME = "shoppy.DB";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FIELD_PRIMARY_KEY = "id";
    private SQLiteDatabase sqLiteDatabase;

    // Methods
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // products
        createTable(sqLiteDatabase, ProductContract.TABLENAME, new HashMap<String, String>() {{
            put(ProductContract.FIELDS.NAME, ProductContract.FIELD_TYPES.NAME);
            put(ProductContract.FIELDS.DESCRIPTION, ProductContract.FIELD_TYPES.DESCRIPTION);
            put(ProductContract.FIELDS.IS_FAVORITE, ProductContract.FIELD_TYPES.IS_FAVORITE);
            // Todo: Update DB
            }
        });

        // shops
        createTable(sqLiteDatabase, ShopContract.TABLENAME, new HashMap<String, String>() {{
            put(ShopContract.FIELDS.NAME, ShopContract.FIELD_TYPES.NAME);
        }});

        // product_categories
        createTable(sqLiteDatabase, ProductCategoryContract.TABLENAME, new HashMap<String, String>() {{
            put(ProductCategoryContract.FIELDS.NAME, ProductCategoryContract.FIELD_TYPES.NAME);
        }});

        // Price
        createTable(sqLiteDatabase, PriceContract.TABLENAME, new HashMap<String, String>() {{
            put(PriceContract.FIELDS.NETTO, PriceContract.FIELD_TYPES.NETTO);
            put(PriceContract.FIELDS.IS_OFFER, PriceContract.FIELD_TYPES.IS_OFFER);
            put(PriceContract.FIELDS.VALID_FROM, PriceContract.FIELD_TYPES.VALID_FROM);
            put(PriceContract.FIELDS.VALID_TO, PriceContract.FIELD_TYPES.VALID_TO);
            put(PriceContract.FIELDS.PRODUCT_ID, PriceContract.FIELD_TYPES.PRODUCT_ID);
            put(PriceContract.FIELDS.SHOP_ID, PriceContract.FIELD_TYPES.SHOP_ID);
        }});

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Creates Tables with just Tablename and hashmap of Fields
     * @param db
     * @param tableName
     * @param fields
     */
    private void createTable(SQLiteDatabase db, String tableName, HashMap<String, String> fields) {
        String command = "CREATE TABLE " + tableName + " ( +" + DATABASE_FIELD_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT";

        for (Map.Entry<String, String> entry : fields.entrySet())
            command = command + ", " + entry.getKey() + " " + entry.getValue();

        command = command + " )";
        db.execSQL(command);
        Log.e(TAG,"Table created: " + tableName);
    }

    /**
     * Inserts or updates a new DataRow, depending on the id field in contentValues
     * @param tableName
     * @param contentValues
     */
    public int addData(String tableName, ContentValues contentValues) {
        int id = 0;
        this.sqLiteDatabase = this.getWritableDatabase();
        try {
            id = (int) this.sqLiteDatabase.insertWithOnConflict(tableName, null,
                    contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            if (id == -1) {
                if (!contentValues.getAsString(DATABASE_FIELD_PRIMARY_KEY).isEmpty() && contentValues.getAsInteger(DATABASE_FIELD_PRIMARY_KEY) > 0) {
                    id = (int) this.sqLiteDatabase.update(tableName, contentValues,
                            "id=?", new String[]{contentValues.getAsString(DATABASE_FIELD_PRIMARY_KEY)});
                }
            }
            return id;
        } catch (Exception ex) {
            Log.e(TAG, "ERROR trying to write to: " + tableName + " with contentValues: " + contentValues.toString());
        } finally {
            return id;
        }
    }

    /**
     * Deletes by ID specified row of given table
     * @param tableName
     * @param id
     * @return success
     */
    public boolean deleteData(String tableName, long id) {
        this.sqLiteDatabase = this.getWritableDatabase();
        try {
            this.sqLiteDatabase.delete(tableName, "id =?", new String[] { String.valueOf(id) });
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "ERROR trying to delete Row from: " + tableName);
            return  false;
        }
    }

    /**
     * Gets the whole Table
     * @param tableName
     */
    public Cursor getTable(String tableName) {
        this.sqLiteDatabase = this.getReadableDatabase();
        if (!tableName.isEmpty()) {
            return this.sqLiteDatabase.query(tableName, null, null, null, null, null, null);
        }
        Log.e(TAG, "No Tablename given");
        return null;
    }

    /**
     *
     * @param tableName Name of Table
     * @param columns array of Columns to get
     * @param selection Where fields e.g. id=?,name=?
     * @param selectionArgs Arguments to pass into selection placeholder
     * @param groupBy group statement
     * @param having having clause
     * @param orderBy order
     * @return Table Cursor
     */
    public Cursor getTableRow(String tableName, @Nullable String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        this.sqLiteDatabase = this.getReadableDatabase();
        if (!tableName.isEmpty()) {
            return this.sqLiteDatabase.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
        } else {
            Log.e(TAG, "No Tablename given");
            return null;
        }
    }

    // Constructors
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(TAG, "Database Helper intitialized, opening " + this.DATABASE_NAME);
    }
}
