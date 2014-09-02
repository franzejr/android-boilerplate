package model;

import com.facebook.model.GraphUser;

import org.json.JSONObject;

public class CurrentUser extends User {

    public CurrentUser(GraphUser facebookUser, String oAuthToken) {
        super(facebookUser, oAuthToken);
    }

    public CurrentUser(JSONObject jsonFan) {
        super(jsonFan);
    }

    public CurrentUser() {

    }
}
