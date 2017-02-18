package com.administrator.gdemo.bean;
/**
 * Created by Administrator on 2017/2/18.
 */

public class LoginBean {

    /**
     * data : {"idcard_contrary_url":"","isPay":1,"user_type":"1","useraddress_contact_name":"","useraddress_type":-1,"useraddress_contact_address":"","customer_phone":"076933368867","isPerfect":"1","open_push":1,"idcard_front_url":"","activate":0,"useraddress_contact_phone":"","user_name":"15917100459","user_account":15917100459,"licences_photo_url":"","user_face_url":"img/user/fac1cada68214fe99c739b64d9c557d5/user_face_url/20170213111604625.jpg","user_id":"fac1cada68214fe99c739b64d9c557d5","useraddress_id":""}
     * msg : success
     * result : 1
     */

    private DataBean data;
    private String msg;
    private String result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class DataBean {
        /**
         * idcard_contrary_url :
         * isPay : 1
         * user_type : 1
         * useraddress_contact_name :
         * useraddress_type : -1
         * useraddress_contact_address :
         * customer_phone : 076933368867
         * isPerfect : 1
         * open_push : 1
         * idcard_front_url :
         * activate : 0
         * useraddress_contact_phone :
         * user_name : 15917100459
         * user_account : 15917100459
         * licences_photo_url :
         * user_face_url : img/user/fac1cada68214fe99c739b64d9c557d5/user_face_url/20170213111604625.jpg
         * user_id : fac1cada68214fe99c739b64d9c557d5
         * useraddress_id :
         */

        private String idcard_contrary_url;
        private int isPay;
        private String user_type;
        private String useraddress_contact_name;
        private int useraddress_type;
        private String useraddress_contact_address;
        private String customer_phone;
        private String isPerfect;
        private int open_push;
        private String idcard_front_url;
        private int activate;
        private String useraddress_contact_phone;
        private String user_name;
        private long user_account;
        private String licences_photo_url;
        private String user_face_url;
        private String user_id;
        private String useraddress_id;

        public String getIdcard_contrary_url() {
            return idcard_contrary_url;
        }

        public void setIdcard_contrary_url(String idcard_contrary_url) {
            this.idcard_contrary_url = idcard_contrary_url;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUseraddress_contact_name() {
            return useraddress_contact_name;
        }

        public void setUseraddress_contact_name(String useraddress_contact_name) {
            this.useraddress_contact_name = useraddress_contact_name;
        }

        public int getUseraddress_type() {
            return useraddress_type;
        }

        public void setUseraddress_type(int useraddress_type) {
            this.useraddress_type = useraddress_type;
        }

        public String getUseraddress_contact_address() {
            return useraddress_contact_address;
        }

        public void setUseraddress_contact_address(String useraddress_contact_address) {
            this.useraddress_contact_address = useraddress_contact_address;
        }

        public String getCustomer_phone() {
            return customer_phone;
        }

        public void setCustomer_phone(String customer_phone) {
            this.customer_phone = customer_phone;
        }

        public String getIsPerfect() {
            return isPerfect;
        }

        public void setIsPerfect(String isPerfect) {
            this.isPerfect = isPerfect;
        }

        public int getOpen_push() {
            return open_push;
        }

        public void setOpen_push(int open_push) {
            this.open_push = open_push;
        }

        public String getIdcard_front_url() {
            return idcard_front_url;
        }

        public void setIdcard_front_url(String idcard_front_url) {
            this.idcard_front_url = idcard_front_url;
        }

        public int getActivate() {
            return activate;
        }

        public void setActivate(int activate) {
            this.activate = activate;
        }

        public String getUseraddress_contact_phone() {
            return useraddress_contact_phone;
        }

        public void setUseraddress_contact_phone(String useraddress_contact_phone) {
            this.useraddress_contact_phone = useraddress_contact_phone;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public long getUser_account() {
            return user_account;
        }

        public void setUser_account(long user_account) {
            this.user_account = user_account;
        }

        public String getLicences_photo_url() {
            return licences_photo_url;
        }

        public void setLicences_photo_url(String licences_photo_url) {
            this.licences_photo_url = licences_photo_url;
        }

        public String getUser_face_url() {
            return user_face_url;
        }

        public void setUser_face_url(String user_face_url) {
            this.user_face_url = user_face_url;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUseraddress_id() {
            return useraddress_id;
        }

        public void setUseraddress_id(String useraddress_id) {
            this.useraddress_id = useraddress_id;
        }
    }
}
