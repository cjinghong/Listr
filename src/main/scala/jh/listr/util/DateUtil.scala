package jh.listr.util

import java.text.{ParseException, SimpleDateFormat}
import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.util.Date;

object DateUtil {
	val DATE_PATTERN = "dd/MM/yyyy"
	val DATE_FORMATTER = new SimpleDateFormat(DATE_PATTERN)

	implicit class DateFormatter(val date: Date) {

		/**
		  * Returns the given date as a well formatted String. The above defined
		  * {@link DateUtil#DATE_PATTERN} is used.
		  *
		  * @param date the date to be returned as a string
		  * @return formatted string
		  */
		def asString: String = {
			if (date == null) {
				return null;
			}
			return DATE_FORMATTER.format(date);
		}
	}

	implicit class StringFormatter(val dateString: String) {
		/**
		  * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
		  * to a {@link LocalDate} object.
		  *
		  * Returns null if the String could not be converted.
		  *
		  * @param dateString the date as String
		  * @return the date object or null if it could not be converted
		  */
		def parseDate: Date = {
			try {
				DATE_FORMATTER.parse(dateString)
			} catch {
				case e: ParseException => null
			}
		}

		def isValid: Boolean = {
			parseDate != null
		}
	}

}