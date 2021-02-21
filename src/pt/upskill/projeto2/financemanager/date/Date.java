package pt.upskill.projeto2.financemanager.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pt.upskill.projeto2.financemanager.exceptions.BadDate;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Date {

    private java.util.Date date = Calendar.getInstance().getTime();

    public Date() {

    }

    public Date(Date date) {
        this.date = (java.util.Date) date.toDate().clone();
    }

    public Date(java.util.Date date) {
        this.date = ((java.util.Date) date.clone());
    }

    public Date(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month - 1, day);
        date = c.getTime();
        checkInvariant();
    }

    public Date(int day, Month month, int year) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month.ordinal() - 1, day);
        date = c.getTime();
        checkInvariant();
    }


    private void checkInvariant() {
        if (getDay() < 1 || getDay() > lastDayOf(getMonth(), getYear())) {
            System.out.println(getDay() + " "
                    + lastDayOf(getMonth(), getYear()));
            throw new BadDate();
        }
        if (getMonth().ordinal() < Month.JANUARY.ordinal() || getMonth().ordinal() > Month.DECEMBER.ordinal())
            throw new BadDate();
        if (getYear() < 0)
            throw new BadDate();

    }

    public static int lastDayOf(Month month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month.ordinal() - 1, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public boolean before(Date d) {
        return date.before(d.date);
    }

    public boolean after(Date d) {
        return date.after(d.date);
    }

    public int getYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public Month getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return c.get(Calendar.MONTH) + 1; // ATT: January = 0 !?
        return Month.values()[c.get(Calendar.MONTH) + 1]; // ATT: January = 0 !?
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

//	private void setDay(int d) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.set(Calendar.DAY_OF_MONTH, d);
//		checkInvariant();
//		date = c.getTime();
//	}

    public int compareTo(Date d) {
        if (getYear() != d.getYear())
            return getYear() - d.getYear();
        if (getMonth() != d.getMonth())
            return getMonth().ordinal() - d.getMonth().ordinal();
        if (getDay() != d.getDay())
            return getDay() - d.getDay();
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Date other = (Date) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    public String toString() {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public java.util.Date toDate() {
        return date;
    }

    public static Date firstOfMonth(Date d) {
        return new Date(1, d.getMonth(), d.getYear());
    }

    public static Date endOfMonth(Date d) {
        return new Date(lastDayOf(d.getMonth(), d.getYear()), d.getMonth(),
                d.getYear());
    }

    public static Date endOfNextMonth(Date d) {
        Month m = d.getMonth();
        int y = d.getYear();
        if (m == Month.DECEMBER) {
            m = Month.JANUARY;
            y++;
        } else
            m = Month.values()[m.ordinal() + 1];
        return new Date(lastDayOf(m, y), m, y);
    }

    public static Date firstOfNextMonth(Date d) {
        Month m = d.getMonth();
        int y = d.getYear();
        if (m == Month.DECEMBER) {
            m = Month.JANUARY;
            y++;
        } else
            m = Month.values()[m.ordinal() + 1];
        return new Date(1, m, y);
    }

    public static Date lastDayOfPreviousMonth(Date d) {
        Month m = d.getMonth();
        int y = d.getYear();
        if (m == Month.JANUARY) {
            m = Month.DECEMBER;
            y--;
        } else
            m = Month.values()[m.ordinal() - 1];
        return new Date(lastDayOf(m, y), m, y);
    }

    public int diffInDays(Date d1) {
        long diffInMilis = 0;
        if (before(d1)) {
            diffInMilis = d1.date.getTime() - date.getTime();
        } else {
            diffInMilis = date.getTime() - d1.date.getTime();
        }
        // Math.round() is necessary to deal with winter/summer timezones
        int diffInDays = (int) Math.round(diffInMilis / (1000.0 * 3600.0 * 24.0));
        return diffInDays;
    }

    public static Month intToMonth(int i) {
        return Month.values()[i];
    }

}
