package org.jai.kissan.persistence.entities;

import lombok.Getter;

@Getter
public enum DealStatus {

    NEW(0), REVIEWING(1), COMPLETED(2);

    private int status;

    DealStatus(int status) {
        this.status = status;
    }
}
