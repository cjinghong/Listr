package jh.listr.view

import jh.listr.model.TodoItem

import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.{CheckBox, ListCell, ListView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.shape.{Line, Rectangle}
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class TodoItemController (
        private val root: AnchorPane,
	    private val title: Text,
	    private val checkBox: CheckBox,
	    private val line: Rectangle
    ) {

	private var item: TodoItem = null

	line.width <== root.width * 0.9

	def setTodoItem(item: TodoItem): Unit = {
		this.item = item
		title.text <== item.title
		checkBox.selected <==> item.completed
		line.visible <== item.completed
	}

	def checkboxClicked(): Unit = {

	}
}

