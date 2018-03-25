package robsen.shoppy.utils;

/**
 * Created by robeschm on 15.03.2018.
 */

public interface ShopContract {

    public static final String TABLENAME = "shops";

    public static abstract class FIELDS {
        public static final String NAME = "name";
    }

    public static abstract class FIELD_TYPES {
        public static final String NAME = "TEXT NOT NULL UNIQUE";
    }


}
