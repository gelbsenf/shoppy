package robsen.shoppy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import robsen.shoppy.objects.Product;
import robsen.shoppy.objects.ProductContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProductDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProductListActivity extends AppCompatActivity {
    private static final String TAG = "ProductListActivity";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ProductContent _productContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.product_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        final View recyclerView = findViewById(R.id.product_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        FloatingActionButton fabAddProduct = (FloatingActionButton) findViewById(R.id.fabInsertProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Product newProductToAdd = new Product("test3", "Beschreibung3");
                if (newProductToAdd.save(ProductListActivity.this)) {

                    // Message about success, UNDO Action deletes entry
                    Snackbar.make(view, newProductToAdd.get_description() + " added successfully", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    newProductToAdd.delete(ProductListActivity.this);
                                    Snackbar.make(view, newProductToAdd.get_description() + " deleted!", Snackbar.LENGTH_LONG);
                                }


                            }).show();
                    setupRecyclerView((RecyclerView) recyclerView);
                } else  {
                    // Todo: Pop Error on UI
                    Log.e(TAG, "ERROR while creating new Product, onClick() of fabAddProduct");
                }
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        this._productContent = new ProductContent(this);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, _productContent.getItems(), mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ProductListActivity mParentActivity;
        private final List<Product> mProducts;
        private final boolean mIsTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Product item = (Product) view.getTag();
                if (mIsTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ProductDetailFragment.ARG_ID, String.valueOf(item.get_id()));
                    ProductDetailFragment fragment = new ProductDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.product_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailFragment.ARG_ID, item.get_id());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ProductListActivity parent,
                                      List<Product> items,
                                      boolean twoPane) {
            mProducts = items;
            mParentActivity = parent;
            mIsTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        /**
         * Fills the ListView with ProductContent
         */
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(mProducts.get(position).get_id()));
            holder.mNameView.setText(mProducts.get(position).get_name());
            holder.mContentView.setText(mProducts.get(position).get_description());

            holder.itemView.setTag(mProducts.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mNameView;
            final TextView mContentView;

            public ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.textView_id);
                mNameView = (TextView) view.findViewById(R.id.textView_name);
                mContentView = (TextView) view.findViewById(R.id.textView_description);
            }

            // Update View with added content
            public void updateViewHolder(ArrayList<Product> productList) {
                notifyDataSetChanged();
            }
        }

    }
}
