package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantMealAttendanceRespons {

  List<TableHead> table_head = new ArrayList<>();
  List<MealTime> meal_time = new ArrayList<>();

    public List<TableHead> getTable_head() {
        return table_head;
    }

    public void setTable_head(List<TableHead> table_head) {
        this.table_head = table_head;
    }

    public List<MealTime> getMeal_time() {
        return meal_time;
    }

    public void setMeal_time(List<MealTime> meal_time) {
        this.meal_time = meal_time;
    }
}
