package by.topolev.contacts;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class Test {
    public static void main(String[] str){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM contact WHERE DAY(birthday) = ").append(day).append(" AND MONTH(birthday) = ").append(month);

        System.out.print(query.toString());

    }
}
