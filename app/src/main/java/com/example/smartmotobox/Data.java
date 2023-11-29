package com.example.smartmotobox;

public class Data {

    String btn_Alarm, btn_Lock, btn_Motor_Status, btn_Enroll, btn_Delete, btn_GPS_Enabler;
    int btn_Notification;

    public Data() {

    }

    public Data(String btn_Alarm, String btn_Lock, String btn_Motor_Status, String btn_Enroll, String btn_Delete, String btn_GPS_Enabler) {
        this.btn_Alarm = btn_Alarm;
        this.btn_Lock = btn_Lock;
        this.btn_Motor_Status = btn_Motor_Status;
        this.btn_Enroll = btn_Enroll;
        this.btn_Delete = btn_Delete;
        this.btn_GPS_Enabler = btn_GPS_Enabler;
    }

    public String getBtn_Alarm() { return  this.btn_Alarm; }
    public void setBtn_Alarm(String btn_Alarm) { this.btn_Alarm = btn_Alarm; }

    public String getBtn_Lock() { return  this.btn_Lock; }
    public void setBtn_Lock(String btn_Lock) {
        this.btn_Lock = btn_Lock;
    }

    public String getBtn_Motor_Status() { return  this.btn_Motor_Status; }
    public void setBtn_Motor_Status(String btn_Motor_Status) { this.btn_Motor_Status = btn_Motor_Status; }

    public String getBtn_Enroll() { return  this.btn_Enroll; }
    public void setBtn_Enroll(String btn_Enroll) { this.btn_Enroll = btn_Enroll; }

    public String getBtn_Delete() { return  this.btn_Delete; }
    public void setBtn_Delete(String btn_Delete) { this.btn_Delete = btn_Delete; }

    public String getBtn_GPS_Enabler() { return  this.btn_GPS_Enabler; }
    public void setBtn_GPS_Enabler(String btn_GPS_Enabler) { this.btn_GPS_Enabler = btn_GPS_Enabler; }

}
