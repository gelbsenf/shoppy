package robsen.shoppy.objects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robsen.shoppy.wrapper.DBHelper;
import robsen.shoppy.wrapper.ProductContract;


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
                    productCursor.getLong(productCursor.getColumnIndex(ProductContract.FIELDS.ID)),
                    productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.NAME)),
                    productCursor.getString(productCursor.getColumnIndex(ProductContract.FIELDS.DESCRIPTION))
            );
            addItem(productToAdd);
        }
        productCursor.close();
    }

    private void addItem(Product product) {
        this.items.add(product);
        this.item_Map.put(String.valueOf(product.get_id()), product);
    }

    public static Product createProduct(int position) {
        return new Product(position, "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public ProductContent(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.sqLiteDatabase = this.dbHelper.getReadableDatabase();
        initItemList();
    }




}