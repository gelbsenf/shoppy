package robsen.shoppy.utils;

/**
 * Created by robsen on 25.03.2018.
 */

public interface PriceContract {

    public static final String TABLENAME = "prices";

    public static abstract class FIELDS {
        public static final String ID = "id";
        public static final String NETTO = "netto";
        public static final String IS_OFFER = "is_offer";
        public static final String VALID_FROM = "valid_from";
        public static final String VALID_TO = "valid_to";

        // Constraints
        public static final String PRODUCT_ID = "product_id";
        public static final String SHOP_ID = "shop_id";
    }

    public static abstract class FIELD_TYPES {
        public static final String ID = "INTEGER";
        public static final String NETTO = "REAL DEFAULT 0";
        public static final String IS_OFFER = "INTEGER DEFAULT 0";
        public static final String VALID_FROM = "INTEGER";
        public static final String VALID_TO = "INTEGER";

        // Constraints
        public static final String PRODUCT_ID = "INTEGER DEFAULT 0";
        public static final String SHOP_ID = "INTEGER DEFAULT 0";
    }

}
