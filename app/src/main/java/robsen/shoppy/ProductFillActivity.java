package robsen.shoppy;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import robsen.shoppy.model.Product;

public class ProductFillActivity extends AppCompatActivity {

    private static final String TAG = "ProductFillActivity";
    private String _infoText;
    private Product _product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_fill);
        initFields();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (createOrUpdateProduct()) {
                    _infoText = _product.get_name() + _infoText;
                    // Message about success, UNDO Action deletes entry
                    Snackbar.make(view, _infoText, Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    _product.delete(ProductFillActivity.this);
                                    Snackbar.make(view, _product.get_name() + " deleted!", Snackbar.LENGTH_LONG);
                                }
                            }).show();
                } else {
                    _infoText = "Error while updating / creating Product";
                    Toast.makeText(ProductFillActivity.this, _infoText, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Initialize the layout fields
     */
    private void initFields() {
        // Recognize Title value
        String title = "";
        Intent passedIntent = getIntent();
        boolean isNewProduct = passedIntent.getBooleanExtra("robsen.shoppy.NEW", false);
        int product_id = passedIntent.getIntExtra(ProductDetailFragment.PRODUCT_ID, -1);

            if (isNewProduct) {
                title = getResources().getString(R.string.product_detail_fields_Title_NEW);
                this._infoText = " added successfully";
            } else {
                if (product_id > -1) {
                    title = getResources().getString(R.string.product_detail_fields_Title_UPDATE);
                    // Todo: Right context?
                    this._product = new Product(product_id, getBaseContext());
                    displayProductFields();
                    this._infoText = " updated successfully";
                } else {
                    Log.e(TAG, "Error in passing " + ProductDetailFragment.PRODUCT_ID);
                }
            }

        // Set Title text
        TextView textView_title = (TextView) findViewById(R.id.textView_product_fill_title);
        textView_title.setText(title);
    }

    /**
     * Writes the _product values into the layout fields
     */
    private void displayProductFields() {
        // Name
        EditText editText_name = (EditText) findViewById(R.id.editText_product_name);
        editText_name.setText(this._product.get_name());
        // Desrciption
        EditText editText_description = (EditText) findViewById(R.id.editText_product_description);
        editText_description.setText(this._product.get_description());
    }

    /**
     * Update or Create new Product
     */
    private boolean createOrUpdateProduct(){
        EditText textView_product_name = (EditText) findViewById(R.id.editText_product_name);
        EditText textView_product_description = (EditText) findViewById(R.id.editText_product_description);

        this._product.set_name(textView_product_name.getText().toString());
        this._product.set_description(textView_product_description.getText().toString());

        return this._product.save(ProductFillActivity.this);
    }


}
