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

	// When a todoitem is set, updates card
	var todoItem: TodoItem = null

	def setItem(item: TodoItem): Unit = {
		if (item != null) {
			// Updates circle
			val todoCircle = new TodoCircle(item)
			todoCircleBorderPane.setCenter(todoCircle)
			// Updates date
			dateText.text = item.dueDate.value.asString
		}
	}

}
