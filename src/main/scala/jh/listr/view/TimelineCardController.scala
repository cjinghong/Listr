package jh.listr.view

import jh.App
import jh.listr.model.{TodoCircle, TodoItem}
import jh.listr.util.DateUtil._

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class TimelineCardController(
    private val todoCircleBorderPane: BorderPane,
	private val dateText: Text) {

	/** Populates TimelineCard with the given `TodoItem`
	  *
	  * @param item The TodoItem to be displayed in the TimelineCard
	  */
	def setItem(item: TodoItem): Unit = {
		val todoCircle = new TodoCircle(item)

		// Shows edit dialog when mouse clicked
		todoCircle.onMouseClicked = { _ =>
			App.showEditDialog(item)
		}

		todoCircleBorderPane.setCenter(todoCircle)
		dateText.text = item.dueDate.value.asString

		item.dueDate.onChange({ (_, _, newDate) =>
			dateText.text = newDate.asString
		})
	}
}
