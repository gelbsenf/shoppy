package robsen.shoppy.model;

import java.util.Date;

import robsen.shoppy.utils.Tools;

/**
 * Created by robsen on 25.03.2018.
 */

public class Price {
    // Properties
    private static final String TAG = "PRICE";
    private int _id;
    private double _netto;
    private boolean _isOffer;
    private Date _valid_from;
    private Date _valid_until;

    // Methods
    public double get_netto() {
        return _netto;
    }

    public void set_netto(double _netto) {
        this._netto = _netto;
    }

    public boolean is_isOffer() {
        return _isOffer;
    }

    public void set_isOffer(boolean _isOffer) {
        this._isOffer = _isOffer;
    }

    public Date get_valid_from() {
        return _valid_from;
    }

    public void set_valid_from(Date _valid_from) {
        this._valid_from = _valid_from;
    }

    public Date get_valid_until() {
        return _valid_until;
    }

    public void set_valid_until(Date _valid_until) {
        this._valid_until = _valid_until;
    }

    // Constructors
    public Price(int id, double _netto, boolean _isOffer, Date _valid_from, Date _valid_until) {
        this._id = id;
        this._netto = _netto;
        this._isOffer = _isOffer;
        this._valid_from = _valid_from;
        this._valid_until = _valid_until;
    }

    public Price(int id, double _netto, boolean _isOffer, long _valid_from, long _valid_until) {
        this._id = id;
        this._netto = _netto;
        this._isOffer = _isOffer;
        this._valid_from = Tools.getDate(_valid_from);
        this._valid_until = Tools.getDate(_valid_until);
    }
}
