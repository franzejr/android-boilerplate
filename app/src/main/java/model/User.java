package model;

import android.os.Parcel;
import android.os.Parcelable;

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

    protected String name;
    protected String email;
    protected String password;

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(email);
    }


    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("email", getEmail());
            json.put("name", getName());
            json.put("password", getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
