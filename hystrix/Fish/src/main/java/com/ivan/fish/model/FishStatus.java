package com.ivan.fish.model;

public enum FishStatus {
    OPEN(0, "打开"), CLOSE(1, "关闭"), HALF_OPEN(2, "半开");

    FishStatus(int status, String desc) {
    }
}
