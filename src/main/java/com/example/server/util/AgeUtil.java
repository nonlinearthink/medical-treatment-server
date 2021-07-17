package com.example.server.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author nonlinearthink
 */
public class AgeUtil {

    public static Integer countAge(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.before(birthday)) {
            throw new IllegalArgumentException("出生日期晚于现在，非法数据");
        }
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(birthday);
        int yearBirth = calendar.get(Calendar.YEAR);
        int monthBirth = calendar.get(Calendar.MONTH);
        int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);
        Integer age = yearNow - yearBirth;
        if (monthNow < monthBirth) {
            age--;
        } else if (monthNow == monthBirth && dayOfMonthNow < dayOfMonthBirth) {
            age--;
        }
        return age;
    }

}
