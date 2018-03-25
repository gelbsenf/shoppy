package robsen.shoppy;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import robsen.shoppy.model.*;
import robsen.shoppy.utils.ProductContract;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String PRODUCT_ID = ProductContract.TABLENAME + "." + ProductContract.FIELDS.ID;

    /**
     * The dummy content this fragment is presenting.
     */
    private Product _mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(PRODUCT_ID)) {

            ProductContent productContent = new ProductContent(getContext());
            _mItem = productContent.getItem_Map().get(getArguments().getString(PRODUCT_ID));

            // If Product not found in arguments, manually search intent for extrasField "product_id"
            if (_mItem == null) {
                if (!this.getActivity().getIntent().getExtras().isEmpty()) {
                    _mItem = new Product(Integer.getInteger(this.getActivity().getIntent().getExtras().get(PRODUCT_ID).toString()), getContext());
                }
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(_mItem.get_name());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (_mItem != null) {
            ((TextView) rootView.findViewById(R.id.product_detail)).setText(_mItem.get_description());
        }

        return rootView;
    }
}
