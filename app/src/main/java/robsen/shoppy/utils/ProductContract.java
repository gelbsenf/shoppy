package robsen.shoppy.utils;

/**
 * Created by robeschm on 15.03.2018.
 */

public interface ProductContract {

    public static final String TABLENAME = "products";

    public static abstract class FIELDS {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String IS_FAVORITE = "is_favorite";
    }

    public static abstract class FIELD_TYPES {
        public static final String NAME = "TEXT NOT NULL";
        public static final String DESCRIPTION = "TEXT";
        public static final String IS_FAVORITE = "INTEGER DEFAULT 0";
    }

}
