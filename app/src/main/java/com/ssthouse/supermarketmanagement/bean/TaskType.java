package com.ssthouse.supermarketmanagement.bean;

/**
 * Created by ssthouse on 20/11/2016.
 */
public enum TaskType {
    OrderTask, StrategyTask;

    public String toString() {
        switch (this) {
            case OrderTask:
                return "OrderTask";
            case StrategyTask:
                return "StrategyTask";
            default:
                return "";
        }
    }

    public static TaskType valueOfString(String src) {
        String raw = src.toLowerCase();
        if (raw.equals("OrderTask")) {
            return OrderTask;
        }

        if (raw.equals("StrategyTask")) {
            return StrategyTask;
        }
        throw new IllegalArgumentException("Unknown Rank Type : " + raw);
    }
}
