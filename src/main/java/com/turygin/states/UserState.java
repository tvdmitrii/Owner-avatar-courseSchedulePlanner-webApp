package com.turygin.states;

import java.util.UUID;

/**
 * Stores information about a user during their session.
 */
public class UserState {
    /** Unique database user id. */
    private long userId;

    /** Cognito UUID. */
    private UUID cognitoUuid;

    /** Cognito username. */
    private String username;

    /** User's first name from Cognito. */
    private String firstName;

    /** User's last name from Cognito. */
    private String lastName;

    /** Indicates whether the user has administrator privileges. */
    private boolean isAdmin;

    /** Indicates whether this is the first user session. */
    private boolean isNew;

    /**
     * Get full name.
     * @return user full name
     */
    public String getFullName(){
        return firstName + " " + lastName;
    }

    /**
     * Gets first name.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Checks whether the user has administrator privileges.
     * @return true if admin, false otherwise
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets administrator indicator variable.
     * @param isAdmin true if admin, false otherwise
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Checks whether this is the first user session.
     * @return true if it is the first user session, false otherwise
     */
    public boolean getIsNew() {
        return isNew;
    }

    /**
     * Sets the new user indicator variable.
     * @param isNew true if it is the first user session, false otherwise
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * Gets last name.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user database id.
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user database id.
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     * @param username the username
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Gets cognito uuid.
     * @return the cognito uuid
     */
    public UUID getCognitoUuid() {
        return cognitoUuid;
    }

    /**
     * Sets cognito uuid.
     * @param cognitoUuid the cognito uuid
     */
    public void setCognitoUuid(UUID cognitoUuid) {
        this.cognitoUuid = cognitoUuid;
    }
}
