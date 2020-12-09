package com.example.stomanage.firebase.dataObject;

public class FactoryObj {
    private String _name;
    private String _troop;
    private String _date;

    public FactoryObj(String _name, String _troop, String _date) {
        this._name = _name;
        this._troop = _troop;
        this._date = _date;
    }

    public FactoryObj() {
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_troop() {
        return _troop;
    }

    public void set_troop(String _troop) {
        this._troop = _troop;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
