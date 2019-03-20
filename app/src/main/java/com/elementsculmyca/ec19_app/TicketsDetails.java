package com.elementsculmyca.ec19_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TicketsDetails extends AppCompatActivity{

    public TicketsDetails(){
        super();
    }
    private String event;
    private String fees;
    private String venue;
    private String name;
    private String status;

    public void setEvent(String event) {
        this.event = event;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TicketsDetails(String event, String fees, String venue, String name, String status) {
        this.event=event;
        this.fees=fees;
        this.venue=venue;
        this.name=name;
        this.status=status;

    }

    public String getEvent() {
        return event;
    }

    public String getFees() {
        return fees;
    }

    public String getVenue() {
        return venue;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}