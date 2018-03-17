package robsen.shoppy.wrapper;

/**
 * Created by robeschm on 15.03.2018.
 */

public class ProductCategoryContract {
    public static final String TABLENAME = "product_categories";

    public static abstract class FIELDS {
        public static final String NAME = "name";
    }

    public static abstract class FIELD_TYPES {
        public static final String NAME = "TEXT";
    }
}
