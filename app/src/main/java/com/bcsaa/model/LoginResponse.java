package com.bcsaa.model;

public class LoginResponse {
//    {
//        "errormsg": "email or password not correct",
//            "successmsg": "you are login now",
//            "authentication_access": "no",
//            "authentication_info": null,
//            "logged_session_data": {
//        "id": 90,
//                "salutation": null,
//                "name": "Rumpa Sikder",
//                "email": "rumpa.sikder31@gmail.com",
//                "usertype": "participant",
//                "role": 15,
//                "status": 1,
//                "provider": null,
//                "provider_key": null,
//                "created_by": null,
//                "updated_by": null,
//                "created_at": "2019-04-01 04:12:31",
//                "updated_at": "2019-05-05 23:44:14",
//                "store_username": null,
//                "store_password": null,
//                "library_username": null,
//                "library_password": null,
//                "library_staff_username": null,
//                "library_staff_password": null
//    }
//    }

//    "authentication_info": {
//        "email": "rumpa.sikder31@gmail.com",
//        "password": "123456",
//        "oldauthenticate_code": 752964
//    },

    private String errormsg,successmsg,authentication_access;

    Authentication_info authentication_info = new Authentication_info();

    Logged_session_data logged_session_data = new Logged_session_data();

    public String getAuthentication_access() {
        return authentication_access;
    }

    public void setAuthentication_access(String authentication_access) {
        this.authentication_access = authentication_access;
    }

    public Authentication_info getAuthentication_info() {
        return authentication_info;
    }

    public void setAuthentication_info(Authentication_info authentication_info) {
        this.authentication_info = authentication_info;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getSuccessmsg() {
        return successmsg;
    }

    public void setSuccessmsg(String successmsg) {
        this.successmsg = successmsg;
    }

    public Logged_session_data getLogged_session_data() {
        return logged_session_data;
    }

    public void setLogged_session_data(Logged_session_data logged_session_data) {
        this.logged_session_data = logged_session_data;
    }
}
