package learn.aaron.closet.shop.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Validations {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
    public static boolean isNullTimestamp(Timestamp value) { return value == null; }

    public static boolean isBlank(Long value) {
        return value == null || value.intValue()==0;
    }
    public static boolean isBlankDecimal(BigDecimal value) {
        return value == null || value.intValue()==0;
    }
    public static boolean isNullDate(Date value) {
        return value == null;
    }
    public static boolean isBlankNumber(Integer value) {return value == null || value==0;}


}
