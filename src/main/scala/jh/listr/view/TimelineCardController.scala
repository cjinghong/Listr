package jh.listr.view

import jh.listr.model.{TodoCircle, TodoItem}
import jh.listr.util.DateUtil._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class TimelineCardController(
    private val todoCircleBorderPane: BorderPane,
	private val dateText: Text) {

	private var todoItem: TodoItem = null

	/** Populates TimelineCard with the given `TodoItem`
	  *
	  * @param item The TodoItem to be displayed in the TimelineCard
	  */
	def setItem(item: TodoItem): Unit = {
		if (item != null) {
			todoItem = item

			// Updates circle
			val todoCircle = new TodoCircle(item)
			todoCircleBorderPane.setCenter(todoCircle)
			// Updates date
			dateText.text = item.dueDate.value.asString

			// TODO: - Show edit dialog
			todoCircleBorderPane.onMouseClicked = { _ =>
				println("Todo circle clicked")
			}
		}
	}

}
