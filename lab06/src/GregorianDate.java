public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }



    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
    }

    @Override
    public Date nextDate() {
        int newYear = year;
        int newMonth = month;
        int newDayOfMonth = dayOfMonth + 1;

        if (newDayOfMonth > getMonthLength(month)) {
            newDayOfMonth = 1;
            newMonth += 1;
        }

        if (newMonth > MONTH_LENGTHS.length) {
            newMonth = 1;
            newYear += 1;
        }

        return new GregorianDate(newYear, newMonth, newDayOfMonth);
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }
}
