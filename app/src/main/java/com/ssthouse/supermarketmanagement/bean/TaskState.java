package com.ssthouse.supermarketmanagement.bean;

/**
 * Created by ssthouse on 20/11/2016.
 */
public enum TaskState {

    UnConfirmed, OnGoing, Finished;

    public String toString() {
        switch (this) {
            case UnConfirmed:
                return "UnConfirmed";
            case OnGoing:
                return "OnGoing";
            case Finished:
                return "Finished";
            default:
                return "";
        }
    }

    public static TaskState valueOfString(String src) {
        String raw = src.toLowerCase();
        if (raw.equals("UnConfirmed")) {
            return UnConfirmed;
        }

        if (raw.equals("OnGoing")) {
            return OnGoing;
        }

        if (raw.equals("Finished")) {
            return Finished;
        }
        throw new IllegalArgumentException("Unknown Rank Type : " + raw);
    }
}
