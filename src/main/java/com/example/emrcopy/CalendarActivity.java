package com.example.emrcopy;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String clientName;
    private Integer serviceNo;


    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
