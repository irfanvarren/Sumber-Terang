package com.irfanvarren.myapplication.Model;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    private Date date;
    private Long timestamp;
    public DateConverter(Long timestamp) {
        this.date = toDate(timestamp);
    }

    public DateConverter(Date date) {
        this.timestamp = toTimestamp(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @TypeConverter
    public static Date toDate(Long timestamp){
        if(timestamp == null){
            return null;
        }
        return new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        if(date == null){
            return null;
        }
        return date.getTime();
    }
}
