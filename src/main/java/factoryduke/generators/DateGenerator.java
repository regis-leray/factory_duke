package factoryduke.generators;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import factoryduke.utils.Assert;

public final class DateGenerator implements Generator<Date> {

	// the number of chars in yyyy-mm-dd hh:mm:ss
	private static final int MIN_NUMBER_OF_CHARS_FOR_TIMESTAMP = 19;

	public enum CalendarField {
		YEAR(Calendar.YEAR),
		MONTH(Calendar.MONTH),
		DAY(Calendar.DATE),
		HOUR(Calendar.HOUR),
		MINUTE(Calendar.MINUTE),
		SECOND(Calendar.SECOND),
		MILLISECOND(Calendar.MILLISECOND);

		private int field;

		CalendarField(int field) {
			this.field = field;
		}

		private int getField() {
			return field;
		}
	}

	private Calendar next;
	private int increment;
	private CalendarField unit;

	DateGenerator() {
		this(today(), 1, CalendarField.DAY);
	}

	private DateGenerator(Calendar next, int increment, CalendarField unit) {
		this.next = next;
		this.increment = increment;
		this.unit = unit;
	}

	private static Calendar today() {
		Calendar result = Calendar.getInstance();
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}


	public DateGenerator startingAt(Date startDate, TimeZone timeZone) {
		Assert.that().notNull(startDate, "startDate may not be null")
				.notNull(timeZone, "timeZone may not be null");
		
		next = Calendar.getInstance(timeZone);
		next.setTime(startDate);
		return this;
	}


	public DateGenerator startingAt(Date startDate) {
		return startingAt(startDate, TimeZone.getDefault());
	}


	public DateGenerator startingAt(Calendar startDate) {
		Assert.that().notNull(startDate, "startDate may not be null");
		next = (Calendar) startDate.clone();
		return this;
	}

	public DateGenerator startingAt(String startDate) {
		Assert.that().notNull(startDate, "startDate may not be null");
		if (startDate.length() >= MIN_NUMBER_OF_CHARS_FOR_TIMESTAMP) {
			return startingAt(Timestamp.valueOf(startDate));
		}
		else {
			return startingAt(java.sql.Date.valueOf(startDate));
		}
	}

	public DateGenerator incrementingBy(int increment, CalendarField unit) {
		Assert.that().notNull(unit, "unit may not be null");
		this.increment = increment;
		this.unit = unit;
		return this;
	}

	@Override
	public Date nextValue() {
		Date result = next.getTime();
		next.add(unit.getField(), increment);
		return result;
	}

	@Override
	public String toString() {
		return "DateGenerator["
				+ "next=" + next
				+ ", increment=" + increment
				+ ", unit=" + unit
				+ "]";
	}
}
