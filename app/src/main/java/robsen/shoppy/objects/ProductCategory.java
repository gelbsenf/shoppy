package robsen.shoppy.objects;

/**
 * Created by robeschm on 15.03.2018.
 */

public class ProductCategory {
    // Properties
    private String _name;

    // Methods
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    // Constructors
    public ProductCategory(String name) {
        this._name = name;
    }
}
