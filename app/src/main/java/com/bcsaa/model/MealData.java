package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class MealData {

    String mealtime_name;
    List<MealAttendanceInfo> mealtime_attend = new ArrayList();

    public String getMealtime_name() {
        return mealtime_name;
    }

    public void setMealtime_name(String mealtime_name) {
        this.mealtime_name = mealtime_name;
    }

    public List<MealAttendanceInfo> getMealtime_attend() {
        return mealtime_attend;
    }

    public void setMealtime_attend(List<MealAttendanceInfo> mealtime_attend) {
        this.mealtime_attend = mealtime_attend;
    }
}
