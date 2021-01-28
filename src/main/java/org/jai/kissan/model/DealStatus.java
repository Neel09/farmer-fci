package org.jai.kissan.model;

public enum DealStatus {

    NEW(0), REVIEWING(1), COMPLETED(2);

    private int status;

    private DealStatus(int status){
        this.status=status;
    }
}
