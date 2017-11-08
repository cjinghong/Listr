package jh.listr.view

import jh.App
import jh.listr.model.TodoItem

import scalafx.geometry.Insets
import scalafx.scene.control.{CheckBox}
import scalafx.scene.layout.{AnchorPane, Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.{Color}
import scalafx.scene.shape.{Rectangle}
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

	// Resize line when the view is resized
	line.width <== root.width * 0.9

	private val defaultColor = Color(68.0/255.0, 108.0/255.0, 179.0/255.0, 0.8)


	/** Populates node with the given `TodoItem`
	  *
	  * @param item The TodoItem to be displayed in the view
	  */
	def setTodoItem(item: TodoItem): Unit = {
		this.item = item
		title.text <== item.title
		line.visible <== item.completed
		checkBox.selected <==> item.completed

		// Set the background color according to the completed state of the todoitem
		updateBackgroundState(item.completed.value)

		// Change background color whenever the todoitem's completion state is changed
		item.completed.onChange({ (_, _, newValue) =>
			// Re-sort items
			App.sortTodoItems()
			updateBackgroundState(newValue)
		})
	}

	// Updates the state of the layout, depending on whether the item is completed
	private def updateBackgroundState(completed: Boolean): Unit = {
		if (completed) {
			// Set text to black, background to transparent
			title.fill = Color.Black
			root.background = null
		} else {
			// Set text to white, background to blue
			title.fill = Color.White
			root.setBackground(
				new Background(
					Array(new BackgroundFill(defaultColor, new CornerRadii(10), Insets.Empty))
				)
			)
		}
	}
}

