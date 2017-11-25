package jh.listr.model
import java.util.Date

import jh.App

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

//	completed.onChange({ (_, _, newVal) =>
//		if (newVal) {
//			delete()
//		}
//	})

	def this(titleS: String, importance: Importance) = {
		this(titleS, new Date(), importance)
	}

	def this(titleS: String) = {
		this(titleS, new Date(), Importance.Low)
	}

	def asProperty(): ObjectProperty[TodoItem] = {
		ObjectProperty(this)
	}

	// TODO: - Delete items
//	def delete(): Unit = {
//		App.todoItems.remove(this)
//	}

	/** Used to determine if 2 TodoItem is equal to each other.
	  *
	  * Compares the `title`, `dueDate`, and `importance`
	  * to figure out if the TodoItem is the same.
	  */

	override def equals(obj: scala.Any): Boolean = {

		Option(obj.asInstanceOf[TodoItem]) match {
			case None =>
				false
			case Some(anotherItem) =>
				title.value == anotherItem.title.value
		}
	}

}


