package com.turygin.states;

import java.util.UUID;

/**
 * Stores information about a user during their session.
 */
public class UserState {
    /** Unique database user id. */
    private long userId;
    private UUID cognitoUuid;
    private String userName;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private boolean isNew;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean aNew) {
        isNew = aNew;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getCognitoUuid() {
        return cognitoUuid;
    }

    public void setCognitoUuid(UUID cognitoUuid) {
        this.cognitoUuid = cognitoUuid;
    }
}
