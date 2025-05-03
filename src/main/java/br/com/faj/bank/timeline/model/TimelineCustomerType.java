package br.com.faj.bank.timeline.model;

public enum TimelineCustomerType {
    NEW_CUSTOMER(0),
    UPDATE_PROFILE(1);

    public final int type;



    TimelineCustomerType(int type) {
        this.type = type;
    }
}
