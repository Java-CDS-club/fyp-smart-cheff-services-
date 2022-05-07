package com.example.onlinesmartcheffservices.Model;

public class Notification {
    private String Date = "" ;
    private String Name = "";
    private  String HirePrsonSkill = "" ;
    private  String Notificationpersonkey = "" ;
    private  String Namepersonwanttohire = ""  ;


    private  String Location = ""  ;
    private  String LoggerKey = ""  ;
    private String  LoggerSkill = "" ;

    private String HireSalery = "" ;
    private  String HireSetPrice = "" ;
    private String picturewhowanttohire = "" ;
    private  String Days = "" ;


    private String Notkey  = "";
    private  String IsApproveByAdmin = "" ;

    ////////////////////////////////////////for My understanding //////////////////////////////////////////
    /*
    In case of Items
    Name hire is items name
    hiiresalery is item price
    hireskillperson is materialsaler
    picturewhowanttohire isitempic
    days is ammount of food
    location where placed it
    logger key
    logger skill


     */
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public Notification() {
    }

    public Notification(String nameHire, String hireSalery, String hireSetPrice, String picturewhowanttohire, String days, String namepersonwanttohire, String location , String loggerKey , String loggerSkill , String hirePrsonSkill , String date , String notkey , String notificationpersonkey , String isApproveByAdmin ) {
        Name = nameHire;
        HireSalery = hireSalery;
        HireSetPrice = hireSetPrice;
        this.picturewhowanttohire = picturewhowanttohire;
        Days = days;
        Namepersonwanttohire = namepersonwanttohire;
        Location = location;
        LoggerKey = loggerKey;
        LoggerSkill = loggerSkill ;
    HirePrsonSkill = hirePrsonSkill ;
    Date = date ;
    Notkey = notkey ;
    Notificationpersonkey = notificationpersonkey ;
       IsApproveByAdmin = isApproveByAdmin ;
    }

    public String getNotkey() {
        return Notkey;
    }

    public void setNotkey(String notkey) {
        Notkey = notkey;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHirePrsonSkill() {
        return HirePrsonSkill;
    }

    public void setHirePrsonSkill(String hirePrsonSkill) {
        HirePrsonSkill = hirePrsonSkill;
    }

    public String getLoggerSkill() {
        return LoggerSkill;

    }

    public String getIsApproveByAdmin() {
        return IsApproveByAdmin;
    }

    public void setIsApproveByAdmin(String isApproveByAdmin) {
        IsApproveByAdmin = isApproveByAdmin;
    }

    public String getNotificationpersonkey() {
        return Notificationpersonkey;
    }

    public void setNotificationpersonkey(String notificationpersonkey) {
        Notificationpersonkey = notificationpersonkey;
    }

    public void setLoggerSkill(String loggerSkill) {
        LoggerSkill = loggerSkill;
    }

    public String getLoggerKey() {
        return LoggerKey;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public void setDays(String days) {
        Days = days;
    }

    public void setNamepersonwanttohire(String namepersonwanttohire) {
        Namepersonwanttohire = namepersonwanttohire;
    }

    public void setLoggerKey(String loggerKey) {
        LoggerKey = loggerKey;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
