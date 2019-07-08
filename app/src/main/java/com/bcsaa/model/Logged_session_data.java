package com.bcsaa.model;

public class Logged_session_data {

//        "id": 90,
//        "salutation": null,
//        "name": "Rumpa Sikder",
//        "email": "rumpa.sikder31@gmail.com",
//        "usertype": "participant",
//        "role": 15,
//        "status": 1,
//        "provider": null,
//        "provider_key": null,
//        "created_by": null,
//        "updated_by": null,
//        "created_at": "2019-04-01 04:12:31",
//        "updated_at": "2019-05-05 23:44:14",
//        "store_username": null,
//        "store_password": null,
//        "library_username": null,
//        "library_password": null,
//        "library_staff_username": null,
//        "library_staff_password": null

    String id,salutation,name,email,usertype,role,status,provider,provider_key,store_username,store_password,library_username,
            library_password,library_staff_username,library_staff_password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
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

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider_key() {
        return provider_key;
    }

    public void setProvider_key(String provider_key) {
        this.provider_key = provider_key;
    }

    public String getStore_username() {
        return store_username;
    }

    public void setStore_username(String store_username) {
        this.store_username = store_username;
    }

    public String getStore_password() {
        return store_password;
    }

    public void setStore_password(String store_password) {
        this.store_password = store_password;
    }

    public String getLibrary_username() {
        return library_username;
    }

    public void setLibrary_username(String library_username) {
        this.library_username = library_username;
    }

    public String getLibrary_password() {
        return library_password;
    }

    public void setLibrary_password(String library_password) {
        this.library_password = library_password;
    }

    public String getLibrary_staff_username() {
        return library_staff_username;
    }

    public void setLibrary_staff_username(String library_staff_username) {
        this.library_staff_username = library_staff_username;
    }

    public String getLibrary_staff_password() {
        return library_staff_password;
    }

    public void setLibrary_staff_password(String library_staff_password) {
        this.library_staff_password = library_staff_password;
    }
}


