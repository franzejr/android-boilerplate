package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Base {

    protected int id;

    public Base() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
