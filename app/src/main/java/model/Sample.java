package model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Sample {
    protected static final String TAG = "JSON_RESPONSE";
    protected static final String ID_KEY = "id";
    protected int id;
    protected Date createdAt;


    protected String environment;

    public Sample() {
    }

    public Sample(JSONObject jsonStory) {
        try {
            if (jsonStory.has(ID_KEY))
                setId(jsonStory.getInt(ID_KEY));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringID() {
        return String.valueOf(this.id);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        try {
            this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPrettyTimeCreatedAt(){
        return getPrettyTime(createdAt);
    }

    public String getPrettyTime(Date date){
        PrettyTime p = new PrettyTime(new Locale("pt"));
        String formattedDate = p.format(date);
        return formattedDate;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        return json;
    }
}
