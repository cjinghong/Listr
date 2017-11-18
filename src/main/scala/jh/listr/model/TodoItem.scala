package jh.listr.model
import java.util.{Date}

import scalafx.beans.property.{BooleanProperty, ObjectProperty, StringProperty}

import jh.listr.model.Importance.Importance

/**
  * A class to represent a single ``To-do item`` in the todo-list.
  *
  * @constructor Create a new `TodoItem` by specifying the `title`, `date`,
  *              and `importance`.
  * @param titleS The TodoItem's title.
  * @param dueDateD The TodoItem's due date.
  * @param importanceI The level of importance for this particular TodoItem.
  *                   Default value is `Importance.Low` unless changed.
  * @example val todoItem = TodoItem("Do homework", new Date(), Importance.High)
  */
class TodoItem(titleS: String, dueDateD: Date, importanceI: Importance = Importance.Low) {
	var title: StringProperty = new StringProperty(titleS)
	var dueDate: ObjectProperty[Date] = ObjectProperty(dueDateD)
	var importance: ObjectProperty[Importance.Value] = ObjectProperty(importanceI)
	var completed: BooleanProperty = BooleanProperty(false)

	def asProperty(): ObjectProperty[TodoItem] = {
		ObjectProperty(this)
	}

	/** Used to determine if 2 TodoItem is equal to each other.
	  *
	  * Compares the `title`, `dueDate`, and `importance`
	  * to figure out if the TodoItem is the same.
	  */
	override def equals(obj: scala.Any): Boolean = {
		val anotherItem = obj.asInstanceOf[TodoItem]
		if (anotherItem == null) { return false }

		title.value == anotherItem.title.value &&
			dueDate.value.getTime == anotherItem.dueDate.value.getTime &&
			importance.value.id == anotherItem.importance.value.id
	}

}


