package jh.listr.view

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, DatePicker, TextField}
import scalafxml.core.macros.sfxml

@sfxml
class TodoListViewController(
	    private val titleTextField: TextField,
	    private val datePicker: DatePicker,
	    private val lowPriorityButton: Button,
	    private val mediumPriorityButton: Button,
	    private val highPriorityButton: Button,
	    private val addButton: Button
	) {

	private val flatRedColorHex = "D24D57"
	// To-do item priority ranges from
	// 1 (low priority),
	// 2 (medium priority),
	// 3 (high priority)
	private var todoItemPriority: Int = 1

	// Low priority by default
	setLowPriority(null)
	def setLowPriority(action: ActionEvent): Unit = {
		println("Low priority item")
		selectPriorityButton(lowPriorityButton)
		todoItemPriority = 1
	}

	def setMediumPriority(action: ActionEvent): Unit = {
		println("Medium priority item")
		selectPriorityButton(mediumPriorityButton)
		todoItemPriority = 2
	}

	def setHighPriority(action: ActionEvent): Unit = {
		println("High priority item")
		selectPriorityButton(highPriorityButton)
		todoItemPriority = 3
	}

	private def selectPriorityButton(button: Button): Unit = {
		// Remove styling for all buttons
		lowPriorityButton.setStyle("")
		mediumPriorityButton.setStyle("")
		highPriorityButton.setStyle("")

		val style = "-fx-background-color: " + flatRedColorHex + "; " +
					"-fx-text-fill: white"

		// Style the selected button
		button.setStyle(style)
	}

}
