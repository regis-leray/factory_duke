package factoryduke.generators;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
public class DateGeneratorTest {

	@Test
	public void start_with_default() {
		Date date = Generators.dateSequence().nextValue();

		Calendar now = Calendar.getInstance();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		assertThat(now.get(Calendar.YEAR)).isEqualTo(calendar.get(Calendar.YEAR));
		assertThat(now.get(Calendar.MONTH)).isEqualTo( calendar.get(Calendar.MONTH));
		assertThat(now.get(Calendar.DATE)).isEqualTo( calendar.get(Calendar.DATE));

		assertThat(0).isEqualTo(calendar.get(Calendar.HOUR_OF_DAY));
		assertThat(0).isEqualTo(calendar.get(Calendar.MINUTE));
		assertThat(0).isEqualTo(calendar.get(Calendar.SECOND));
		assertThat(0).isEqualTo(calendar.get(Calendar.MILLISECOND));
	}

	@Test
	public void increment_by_1_day() throws ParseException {
		DateGenerator sequence = Generators.dateSequence().startingAt(july19Of2013AtMidnight());
		sequence.nextValue();
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-20 00:00:00 0");
	}

	@Test
	public void set_start_date() throws ParseException {
		DateGenerator sequence = Generators.dateSequence().startingAt(july19Of2013AtMidnight());
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");

		sequence.startingAt(july19Of1975AtMidnight());
		assertThat(toLongString(sequence.nextValue())).isEqualTo("1975-07-19 00:00:00 0");
	}

	@Test
	public void set_start_date_with_timezone() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnightInParisTimeZone(), TimeZone.getTimeZone("UTC"));
		assertThat(toLongStringInUTC(sequence.nextValue())).isEqualTo("2013-07-18 22:00:00 0");
	}

	@Test
	public void set_start_date_as_string() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt("2013-07-19");

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
	}

	@Test
	public void equals_to_now() throws ParseException {
		Calendar start = Calendar.getInstance();
		DateGenerator sequence = Generators.dateSequence().startingAt(start);

		assertThat(sequence.nextValue()).isEqualTo(start.getTime());
	}

	@Test
	public void increment_by_2_days() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(2, DateGenerator.CalendarField.DAY);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-21 00:00:00 0");
	}

	@Test
	public void increment_by_1_year() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.YEAR);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2014-07-19 00:00:00 0");
	}

	@Test
	public void increment_by_1_month() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.MONTH);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-08-19 00:00:00 0");

	}

	@Test
	public void increment_by_1_hours() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.HOUR);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 01:00:00 0");
	}

	@Test
	public void increment_by_1_minute() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.MINUTE);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:01:00 0");
	}

	@Test
	public void increment_by_1_seconds() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.SECOND);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:01 0");
	}

	@Test
	public void increment_by_1_ms() throws ParseException {
		DateGenerator sequence =
				Generators.dateSequence()
						.startingAt(july19Of2013AtMidnight())
						.incrementingBy(1, DateGenerator.CalendarField.MILLISECOND);

		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 0");
		assertThat(toLongString(sequence.nextValue())).isEqualTo("2013-07-19 00:00:00 1");
	}

	private String toLongString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S").format(date);
	}

	private String toLongStringInUTC(Date date) {
		TimeZone zone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
		simpleDateFormat.setTimeZone(zone);
		return simpleDateFormat.format(date);
	}

	private Date july19Of2013AtMidnight() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse("2013-07-19");
	}

	private Date july19Of1975AtMidnight() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse("1975-07-19");
	}

	// offset is +02:00 in Paris at this date
	private Date july19Of2013AtMidnightInParisTimeZone() throws ParseException {
		TimeZone zone = TimeZone.getTimeZone("Europe/Paris");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		simpleDateFormat.setTimeZone(zone);
		return simpleDateFormat.parse("2013-07-19");
	}

}
