package robsen.shoppy.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robsen.shoppy.utils.DBHelper;
import robsen.shoppy.utils.ProductContract;


public class ProductContent {

    private List<Product> items = new ArrayList<Product>();
    private Map<String, Product> item_Map = new HashMap<String, Product>();

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public Map<String, Product> getItem_Map() {
        return item_Map;
    }

    public void setItem_Map(Map<String, Product> item_Map) {
        this.item_Map = item_Map;
    }

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private void initItemList() {

        Cursor productCursor = dbHelper.getTable(ProductContract.TABLENAME);
        for (productCursor.moveToFirst(); !productCursor.isAfterLast(); productCursor.moveToNext()) {
            Product productToAdd = new Product(
                    productCursor.getInt(productCursor.getColumnIndex(ProductContract.FIELDS.ID)),
                    productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.NAME)),
                    productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.DESCRIPTION)),
                    productCursor.getInt(productCursor.getColumnIndex(ProductContract.FIELDS.IS_FAVORITE)) == 1
            );
            addItem(productToAdd);
        }
        productCursor.close();
    }

    private void addItem(Product product) {
        this.items.add(product);
        this.item_Map.put(String.valueOf(product.get_id()), product);
    }

    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        builder.append(this.items.get(position).get_description());
        return builder.toString();
    }

    public ProductContent(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.sqLiteDatabase = this.dbHelper.getReadableDatabase();
        initItemList();
    }




}
