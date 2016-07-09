package models;

import com.avaje.ebean.Model;
import play.Logger;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Douglas on 6/6/2016.
 * User Entity
 */
@Entity
public class User extends Model{
    @Id
    protected int id;
    @Column(unique = true)
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected int loginAttempts;
    protected boolean isLocked;
    protected boolean isSuperUser;
    @OneToMany(mappedBy = "user")
    protected ArrayList<Role> roles = new ArrayList<>();


    /**
     * User no-arg constructor
     */
    public User(){}

    /**
     * Creates User with given parameters
     * @param userName user username
     * @param firstName user firstname
     * @param lastName user lastname
     * @param phoneNumber user phone number
     * @param email user email address
     * @param password user password
     */
    public User(String userName,
                String firstName,
                String lastName,
                String phoneNumber,
                String email,
                String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.isSuperUser = false;
    }

    /**
     * Creates user with only username and password
     * @param userName user username
     * @param password user password
     */
    public User(String userName, String password) {
        this(userName, null, null, null, null, password);
    }

    /**
     * user id getter method
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     * user username getter method
     * @return user username
     */
    public String getUserName() { return userName; }

    /**
     * user first name getter method
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * user last name getter method
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * user phone number getter method
     * @return user phone number
     */
    public String getPhone() {
        return phoneNumber;
    }

    /**
     * user email address getter method
     * @return user email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * this method finds the role of a user
     *
     * @return administration list
     */
    public String getRole() {
        if (Role.fetchByUserId(id).size() > 0) {
            String roles = "";
            for (int i = 0; i < Role.fetchByUserId(id).size(); i++) {
                roles += Role.fetchByUserId(id).get(i).getRole().toString() + " ";
            }
            return roles;
        } else {
            return "No Roles Assigned";
        }
    }

    /**
     * creates Finder for User Entity
     */
    public static Finder<Integer, User> find
            = new Finder<>(User.class);

    /**
     * returns the number of current user's login attempts
     * @return user login attempts
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * shows whether user is locked or not.
     * @return true if user locked, false otherwise.
     */
    public boolean getIsLocked() {
        return isLocked;
    }

    /**
     * checks whether user is superuser or not.
     * @return true if user is superuser, false otherwise.
     */
    public boolean getIsSuperUser() {
        return isSuperUser;
    }

    /**
     * user first name setter method
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * user last name setter method
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * user phone number setter method
     * @param phone phone number
     */
    public void setPhone(String phone)
    {
        this.phoneNumber = phone;
    }

    /**
     * user email address setter method
     * @param email user email address
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * sets user's number of login attempts
     * @param loginAttempts number of login attempts
     */
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    /**
     * sets whether user is locked or not
     * @param isLocked true if user is locked, false otherwise.
     */
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * superuser setter method.
     * @param isSuperUser is user superuser
     */
    public void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    /**
     * user password getter method
     * @return user password
     */
    public String getPassword() { return password; }

    /**
     * validates password
     * @param check password input
     * @return true if password is correct, false otherwise
     */
    public boolean checkPassword(String check) {
        return password.equals(check);
    }

    /**
     * makes instance of User with UserFormData
     * @param formData UserFormData
     * @return User corresponding to the data
     */
    public static User makeInstance(UserFormData formData) {
        User user = new User(formData.username,
                formData.firstname,
                formData.lastname,
                formData.phone,
                formData.email,
                formData.password);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User tester = (User) o;
            if (tester.getUserName() != null && userName.equals(tester.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public static User fetchById(int id) {
        User user = User.find.byId(id);
        return user;
    }

    /**
     * Fetches user by username
     * @param username user username
     * @return user corresponding to username
     */
    public static User fetchByUsername(String username) {
        User user = User.find.select("*").where().eq("userName", username).findUnique();
        return user;
    }

    public static List<User> fetchByIds(List<Integer> ids) {
        List<User> users = new LinkedList<>();
        User user;
        for (Integer id : ids) {
            user = User.find.byId(id);
            if (user != null)
                users.add(user);
        }
        return users;
    }

    public static List<User> fetchAllUsers() {
        List<User> allUsers = find.select("*").findList();
        if (allUsers == null)
            allUsers = new ArrayList<>();
        return allUsers;
    }
}
