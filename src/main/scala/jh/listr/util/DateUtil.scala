package jh.listr.util

import java.text.{ParseException, SimpleDateFormat}
import java.time.LocalDate
import java.util.Date;

object DateUtil {
	val DATE_PATTERN = "dd/MM/yyyy"
	val DATE_FORMATTER = new SimpleDateFormat(DATE_PATTERN)

	implicit class DateFormatter(val date: Date) {
		/**
		  * Returns the given date as a well formatted String. The above defined
		  * [[DATE_PATTERN]] is used.
		  *
		  * @param date the date to be returned as a string
		  * @return formatted string
		  */
		def asString: String = {
			if (date == null) return null
			DATE_FORMATTER.format(date)
		}
	}

	implicit class DateToLocalDate(val date: Date) {
		/** Converts a [[java.util.Date]] to a [[java.time.LocalDate]] object.
		  *
		  * @param date the LocalDate object
		  * @return the [[java.time.LocalDate]] object
		  */
		def toLocalDate: LocalDate = {
			new java.sql.Date(date.getTime).toLocalDate
		}
	}

	implicit class LocalDateToDate(val localDate: LocalDate) {
		/** Converts a [[java.time.LocalDate]] to a [[Date]] object.
		  *
		  * @param localDate the LocalDate object
		  * @return the [[java.util.Date]] object
		  */
		def toDate: Date = {
			java.sql.Date.valueOf(localDate)
		}
	}

	implicit class StringFormatter(val dateString: String) {
		/** Converts a String in the format of the defined [[DATE_PATTERN]]
		  * to a [[Date]] object.
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