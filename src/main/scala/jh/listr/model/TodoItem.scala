package jh.listr.model
import java.util.Date

import jh.App

import scalafx.beans.property.{BooleanProperty, ObjectProperty, StringProperty}
import jh.listr.model.Importance.Importance
import jh.listr.util.Database
import jh.listr.util.DateUtil._
import scalikejdbc._

import scala.util.Try

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

	def this(titleS: String, importance: Importance) = {
		this(titleS, new Date(), importance)
	}

	def this(titleS: String) = {
		this(titleS, new Date(), Importance.Low)
	}

	// If delete item immediately is set to true, deletes immediately
	completed.onChange({ (_, _, newVal) =>
		if (App.settingsVal && newVal) {
			App.deleteItem(this)
		}
	})

	def asProperty(): ObjectProperty[TodoItem] = {
		ObjectProperty(this)
	}

	override def equals(obj: scala.Any): Boolean = {
		Option(obj.asInstanceOf[TodoItem]) match {
			case None =>
				false
			case Some(anotherItem) =>
				title.value == anotherItem.title.value
		}
	}

	// DATABASE CODE

	/** Updates the existing TodoItem,
	  * If it doesn't exist, insert the new TodoItem into database.
	  */
	def save() : Try[Int] = {
		val completedInt = if (completed.value) 1 else 0

		existingItemId match {
			case Some(id) =>
				Try(DB autoCommit { implicit session =>
					sql"""
					update todo
					set
					title       = ${title.value} ,
					duedate     = ${dueDate.value.getTime},
					importance  = ${importance.value.id},
					completed   = ${completedInt},

					where id = ${id}
				""".update.apply()
				})
			case None =>
				Try(DB autoCommit { implicit session =>
					sql"""
					insert into todo (title, duedate, importance, completed)
					values
					(${title.value}, ${dueDate.value.getTime}, ${importance.value.id},
					${completedInt})
					""".update.apply()
				})
		}
	}

	/** Delete the current TodoItem from database (If it exist) */
	def delete() : Try[Int] = {
		existingItemId match {
			case Some(id) =>
				Try(DB autoCommit { implicit session =>
					sql"""
					delete from todo where
					id = ${id}
					""".update.apply()
				})
			case _ =>
				throw new Exception("Person not Exists in Database")
		}
	}

	/** Get the existing id of the current TodoItem
	  *
	  * @return The unique ID of the TodoItem
	  */
	def existingItemId: Option[Int] =  {

		val completedInt = if (completed.value) 1 else 0

		DB readOnly { implicit session =>
			sql"""
				select * from todo where
				title = ${title.value}
			""".map(rs =>
				rs.int("id")
			).single.apply()
		}
	}

}

object TodoItem extends Database {

	/** Create a new TodoItem with values retrieved from database */
	private def apply (title: String, dateEpoch: Long, importance: Int, completedInt: Int) : TodoItem = {
		new TodoItem(title, new Date(dateEpoch), Importance(importance)) {
			completed.value = completedInt != 0
		}
	}

	/** Initialize table */
	def initializeTable(): Unit = {

		DB autoCommit { implicit session =>
			sql"""
			create table todo (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  title varchar(200),
			  duedate varchar(64),
			  importance int,
			  completed int
			)
			""".execute.apply()
		}
	}

	/** Gets all the TodoItems existing in the database */
	def getAllTodoItems : List[TodoItem] = {
		DB readOnly { implicit session =>
			sql"select * from todo".map(rs => TodoItem(
				rs.string("title"),
				rs.long("duedate"),
				rs.int("importance"),
				rs.int("completed"),
			) ).list.apply()
		}
	}
}
