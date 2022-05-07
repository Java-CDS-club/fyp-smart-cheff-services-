package com.example.onlinesmartcheffservices.Model;

public class Response {

    String resposnsenotifcationkey ;
    private String NameHire = "" ;
    private  String HireSalery  = "" ;
    private  String HireSetPrice  = " ";
    private  String  HireSkills = "" ;
    private String picturewhowanttohire = "" ;
    private  String   Days = ""  ;
    private  String  Namepersonwanttohire =" " ;
    private  String  Location = " " ;
    private  String  LoggerKey  = " ";
    private  String  LoggerSkill = ""  ;
    private String Respose  = ""  ;
    private String NotificationPersonKey   = ""  ;


    public Response() {
    }

    public Response(String nameHire, String hireSalery, String hireSetPrice, String picturewhowanttohire, String days, String namepersonwanttohire, String location, String loggerKey, String respose , String resposnsenotifcationkey1 , String hireskill
    , String notificationPersonKey) {
        NameHire = nameHire;
        HireSalery = hireSalery;
        HireSetPrice = hireSetPrice;
        NotificationPersonKey = notificationPersonKey ;
        this.picturewhowanttohire = picturewhowanttohire;
        Days = days;
        Namepersonwanttohire = namepersonwanttohire;
        Location = location;
        LoggerKey = loggerKey;
        Respose = respose;
        this.resposnsenotifcationkey = resposnsenotifcationkey1;
        HireSkills = hireskill;
    }

    public void setHireSkills(String hireSkills) {
        HireSkills = hireSkills;
    }

    public String getHireSkills() {
        return HireSkills;
    }

    public String getLoggerSkill() {
        return LoggerSkill;
    }

    public void setLoggerSkill(String loggerSkill) {
        LoggerSkill = loggerSkill;
    }

    public String getResposnsenotifcationkey() {
        return resposnsenotifcationkey;
    }

    public void setNameHire(String nameHire) {
        NameHire = nameHire;
    }

    public void setHireSalery(String hireSalery) {
        HireSalery = hireSalery;
    }

    public void setHireSetPrice(String hireSetPrice) {
        HireSetPrice = hireSetPrice;
    }

    public void setPicturewhowanttohire(String picturewhowanttohire) {
        this.picturewhowanttohire = picturewhowanttohire;
    }

    public String getNotificationPersonKey() {
        return NotificationPersonKey;
    }

    public void setNotificationPersonKey(String notificationPersonKey) {
        NotificationPersonKey = notificationPersonKey;
    }

    public void setDays(String days) {
        Days = days;
    }

    public void setNamepersonwanttohire(String namepersonwanttohire) {
        Namepersonwanttohire = namepersonwanttohire;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setLoggerKey(String loggerKey) {
        LoggerKey = loggerKey;
    }

    public void setRespose(String respose) {
        Respose = respose;
    }

    public String getNameHire() {
        return NameHire;
    }

    public String getHireSalery() {
        return HireSalery;
    }

    public String getHireSetPrice() {
        return HireSetPrice;
    }

    public String getPicturewhowanttohire() {
        return picturewhowanttohire;
    }

    public String getDays() {
        return Days;
    }

    public String getNamepersonwanttohire() {
        return Namepersonwanttohire;
    }

    public String getLocation() {
        return Location;
    }

    public String getLoggerKey() {
        return LoggerKey;
    }

    public String getRespose() {
        return Respose;
    }

    public void setResposnsenotifcationkey(String resposnsenotifcationkey) {
        this.resposnsenotifcationkey = resposnsenotifcationkey;
    }
}

