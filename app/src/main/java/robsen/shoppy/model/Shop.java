package robsen.shoppy.model;

import java.util.List;

/**
 * Created by robeschm on 15.03.2018.
 */

public class Shop {
    // Properties
    private String _name;
    private List<Product> _associatedProductsList;

    // Methods
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public List<Product> get_associatedProductsList() {
        return _associatedProductsList;
    }

    public void set_associatedProductsList(List<Product> _associatedProductsList) {
        this._associatedProductsList = _associatedProductsList;
    }

    // Constructors
    public Shop(String _name, List<Product> _associatedProductsList) {
        this._name = _name;
        this._associatedProductsList = _associatedProductsList;
    }
}
