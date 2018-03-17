package robsen.shoppy.wrapper;

/**
 * Created by robeschm on 15.03.2018.
 */

public class ProductContract {

    public static final String TABLENAME = "products";

    public static abstract class FIELDS {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static abstract class FIELD_TYPES {
        public static final String NAME = "TEXT";
        public static final String DESCRIPTION = "TEXT";
    }

}
