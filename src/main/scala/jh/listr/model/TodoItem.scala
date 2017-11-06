package jh.listr.model
import java.time.LocalDate
import java.util.{Calendar, Date}

import jh.listr.model.Importance.Importance

import scalafx.beans.property.{BooleanProperty, IntegerProperty, ObjectProperty, StringProperty}
import java.time.LocalDate;

/**
  * A class to represent a single ``To-do item`` in the todo-list.
  *
  * @constructor Create a new `TodoItem` by specifying the `title`, `date`,
  *              and `importance`.
  * @param titleS The TodoItem's title.
  * @param dateD The TodoItem's date, in epoch.
  * @param importanceI The level of importance for this particular TodoItem.
  *                   Default value is `Importance.Low` unless changed.
  * @example val todoItem = TodoItem("Do homework", new Date(), Importance.High)
  */
class TodoItem(titleS: String, dateD: LocalDate, importanceI: Importance = Importance.Low) {
	var title: StringProperty = new StringProperty(titleS)
	var date: ObjectProperty[LocalDate] = ObjectProperty(dateD)
	var importance: ObjectProperty[Importance.Value] = ObjectProperty(importanceI)
	var completed: BooleanProperty = BooleanProperty(false)

	def asProperty(): ObjectProperty[TodoItem] = {
		ObjectProperty(this)
	}
}
