package com.example.allgoods;

// The User class represents a user in the system with their personal details and credentials.

import android.os.Parcel;
import android.os.Parcelable;
public class User implements Parcelable {
    private final String id;
    private String username;
    private String email;
    private String encryptedPassword;
    // UserHelper is transient because helper methods don't need to be parcelled
    transient UserHelper userHelper;

    public User(String id, String username, String password, String email) {
        this.userHelper = new UserHelper();
        this.id = id;
        if (!userHelper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        } else {
            this.email = email;
        }
        if (!userHelper.isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        } else {
            this.username = username;
        }
        if (!userHelper.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        } else {
            this.encryptedPassword = userHelper.encrypt(password);
        }
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        email = in.readString();
        encryptedPassword = in.readString();
        userHelper = new UserHelper();  // Re-instantiate userHelper as it's not parcelled
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(encryptedPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        // Decrypts the password for usage
        return userHelper.decrypt(encryptedPassword);
    }

    public void setUsername(String username) {
        if (userHelper.isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public void setPassword(String oldPassword, String newPassword, String newPassword2) {
        if (userHelper.changePassword(oldPassword, newPassword, newPassword2, encryptedPassword) != null) {
            this.encryptedPassword = userHelper.encrypt(newPassword);
        } else {
            throw new IllegalArgumentException("Password update failed");
        }
    }
}
