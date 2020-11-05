package ch.noseryoung.invist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ch.noseryoung.invist.persistence.Converters;

@Entity(tableName = "users")
@TypeConverters({Converters.class})
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String email;


    @ColumnInfo(name = "first_name")
    @NonNull
    private String firstname;

    @ColumnInfo(name = "last_name")
    @NonNull
    private String lastname;

    @NonNull
    private String password;

    @NonNull
    private Date birthday;

    @NonNull
    private String company;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String city;

    private String postcode;

    public User(@NonNull String email, @NonNull String firstname, @NonNull String lastname, @NonNull String password,
                @NonNull Date birthday, @NonNull String company, String phoneNumber, String address, String city, String postcode) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.birthday = birthday;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
