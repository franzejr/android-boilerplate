package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.model.GraphUser;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends Base implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
    protected static final String NAME_KEY = "name";
    protected static final String SHORT_NAME_KEY = "short_name";
    protected static final String EMAIL_KEY = "email";
    protected static final String AUTHENTICATION_TOKEN_KEY = "authentication_token";
    protected static final String OAUTH_TOKEN_KEY = "oauth_token";
    protected static final String UID_KEY = "uid";

    protected String name;
    protected String shortName;
    protected String email;
    protected String authentication_token;
    protected String password;
    protected String oAuthToken;
    protected String uID;


    public User(JSONObject jsonFan) {
        super(jsonFan);
        try {
            if (jsonFan.has(NAME_KEY))
                setName(jsonFan.getString(NAME_KEY));
            if (jsonFan.has(SHORT_NAME_KEY))
                setShortName(jsonFan.getString(SHORT_NAME_KEY));

            if (jsonFan.has(EMAIL_KEY))
                setEmail(jsonFan.getString(EMAIL_KEY));
            if (jsonFan.has(AUTHENTICATION_TOKEN_KEY))
                setAuthentication_token(jsonFan.getString(AUTHENTICATION_TOKEN_KEY));
            if (jsonFan.has(UID_KEY))
                setuID(jsonFan.getString(UID_KEY));
            if (jsonFan.has(OAUTH_TOKEN_KEY))
                setoAuthToken(jsonFan.getString(OAUTH_TOKEN_KEY));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        shortName = in.readString();
        email = in.readString();
        authentication_token = in.readString();
    }

    public User(GraphUser facebookUser, String oAuthToken) {
        this.email = (String) facebookUser.getProperty("email");
        this.oAuthToken = oAuthToken;
        this.uID = facebookUser.getId();
        this.name = facebookUser.getName();
    }

    public User(int id) {
        this.id = id;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getoAuthToken() {
        return oAuthToken;
    }

    public void setoAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(shortName);
        parcel.writeString(email);
        parcel.writeString(authentication_token);
    }


    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("email", getEmail());
            json.put("name", getName());
            json.put("password", getPassword());
            json.put("oauth_token", getoAuthToken());
            json.put("uid", getuID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
