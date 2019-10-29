package edu.cmu.andrew.foodish.server.models;

public class User {

    private int idUser = -1;
    private String FullName;
    private String Email;
    private String VerificationToken;
    private String Password;
    private int IsVerified;
    private String phone;

    public User(int idUser, String fullName, String email, String verificationToken, String password, int isVerified, String phone) {
        this.idUser = idUser;
        FullName = fullName;
        Email = email;
        VerificationToken = verificationToken;
        Password = password;
        IsVerified = isVerified;
        this.phone = phone;
    }

    public void setIdUsers(int idUser) {
        this.idUser = idUser;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setVerificationToken(String verificationToken) {
        VerificationToken = verificationToken;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setIsVerified(int isVerified) {
        IsVerified = isVerified;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getVerificationToken() {
        return VerificationToken;
    }

    public String getPassword() {
        return Password;
    }

    public int getIsVerified() {
        return IsVerified;
    }

    public String getPhone() {
        return phone;
    }
}
