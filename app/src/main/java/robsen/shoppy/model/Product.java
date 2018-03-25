package robsen.shoppy.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import robsen.shoppy.utils.DBHelper;
import robsen.shoppy.utils.ProductContract;

/**
 * Created by robeschm on 15.03.2018.
 */

public class Product extends Object {
    private final static String TAG = "PRODUCT OPERERATIONS";
    // Properties
    private int _id;
    private String _name;
    private String _description;
    private boolean _favorite;

    private Context _context;

    // Methods
    @Override
    public String toString() {
        return this._name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public boolean is_favorite() {
        return _favorite;
    }

    public void set_favorite(boolean _favorite) {
        this._favorite = _favorite;
    }

    /**
     * Inserts this Product
     * @param callingContext Activity conext
     * @return success
     */
    public boolean save(Context callingContext) {
        DBHelper dbHelper = new DBHelper(callingContext);

        Log.e(TAG,"saving Product...");
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.FIELDS.ID, this._id);
        contentValues.put(ProductContract.FIELDS.NAME, this._name);
        contentValues.put(ProductContract.FIELDS.DESCRIPTION, this._description);
        contentValues.put(ProductContract.FIELDS.IS_FAVORITE, (this._favorite ? 1 : 0));

        // Add Data to SQLite
        this._id = dbHelper.addData(ProductContract.TABLENAME, contentValues);
        dbHelper.close();
        Log.e(TAG, "Product saved: " + String.valueOf(this._id));
        return (this._id > 0);
    }

    /**
     * Deletes this Product
     * @return success
     */
    public boolean delete(Context callingContext){
        DBHelper dbHelper = new DBHelper(callingContext);

        Log.e(TAG,"deleting Product...");
        dbHelper.deleteData(ProductContract.TABLENAME, this._id);
        Log.e(TAG, "Product deleted: " + String.valueOf(this._id));
        dbHelper.close();
        return true;
    }

    /**
     * Get Product fields from SqLite Database
     */
    private void fillInformation(){
        DBHelper dbHelper = new DBHelper(this._context);
        Log.e(TAG, "retrieving Product data ...");

        Cursor productCursor =  dbHelper.getTableRow(ProductContract.TABLENAME,
                null,
                "id=?",
                 new String[]{ String.valueOf(this._id) },
                null,
                null,
                null);
        if (productCursor.moveToFirst()) {
            this._name = productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.NAME));
            this._description = productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.DESCRIPTION));
            this._favorite = (productCursor.getInt(productCursor.getColumnIndex(ProductContract.FIELDS.IS_FAVORITE)) == 1);
        }
        productCursor.close();
    }


    // Constructors
    /**
     * For loading in ListActivities
     * @param id
     * @param name
     * @param description
     */
    public Product(int id, String name, String description, boolean favorite) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._favorite = favorite;
    }

    /**
     * For creating new Products
     * @param name
     * @param description
     */
    public Product(String name, String description, boolean favorite) {
        this._name = name;
        this._description = description;
        this._favorite = favorite;
    }

    /**
     * Get ProductInfo by ID
     * @param id
     */
    public Product(int id, Context callingContext) {
        this._id = id;
        this._context = callingContext;
        fillInformation();
    }

    /**
     * blank
     */
    public Product() {
    }

}
