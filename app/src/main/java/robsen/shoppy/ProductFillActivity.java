package robsen.shoppy;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import robsen.shoppy.objects.Product;
import robsen.shoppy.wrapper.DBHelper;

public class ProductFillActivity extends AppCompatActivity {

    private static final String TAG = "ProductFillActivity";
    private String _title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_fill);


        if (getIntent().getExtras().getBoolean("robsen.shoppy.NEW")) {
            _title = getResources().getString(R.string.product_detail_fields_Title_NEW);
        } else {
            _title = getResources().getString(R.string.product_detail_fields_Title_UPDATE);
        }

        TextView textView_title = (TextView) findViewById(R.id.textView_product_fill_title);
        textView_title.setText(_title);

        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Product newProductAdded = createOrUpdateProduct();
                if (newProductAdded != null) {
                    // Message about success, UNDO Action deletes entry
                    Snackbar.make(view, newProductAdded.get_name() + " added successfully", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    newProductAdded.delete(ProductFillActivity.this);
                                    Snackbar.make(view, newProductAdded.get_name() + " deleted!", Snackbar.LENGTH_LONG);
                                }


                            }).show();

                } else  {
                    // Todo: Pop Error on UI
                    Log.e(TAG, "ERROR while creating new Product, onClick() of fabAddProduct");
                }
                createOrUpdateProduct();
            }
        });

    }

    /**
     * Update or Create new Product
     */
    private Product createOrUpdateProduct(){
        TextView textView_product_name = findViewById(R.id.editText_product_name);
        TextView textView_product_description = findViewById(R.id.editText_product_description);
        Product newProductToAdd;

        //if (getIntent().getExtras().containsKey(DBHelper.DATABASE_FIELD_PRIMARY_KEY)) {
         newProductToAdd = new Product(
                 getIntent().getExtras().getInt(DBHelper.DATABASE_FIELD_PRIMARY_KEY, 0),
                 textView_product_name.getText().toString(),
                 textView_product_description.getText().toString());
        //} else {
//            newProductToAdd = new Product(textView_product_name.getText().toString(),
  //                  textView_product_description.getText().toString());
    //    }

        if (newProductToAdd.save(ProductFillActivity.this)) {
            return newProductToAdd;
        }
        return null;
    }

}
