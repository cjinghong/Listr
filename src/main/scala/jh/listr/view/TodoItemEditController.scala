package jh.listr.view

import jh.App
import jh.listr.model.Importance.Importance
import jh.listr.model.{Importance, TodoItem}

import scalafx.event.Event
import scalafx.scene.control.{Button, DatePicker, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class TodoItemEditController (
		private val title:TextField,
		private val datePicker:DatePicker,
		private val lowPriorityButton: Button,
		private val mediumPriorityButton: Button,
		private val highPriorityButton: Button,
		private val okButton:Button
	) {
	private val flatRedColorHex = "#D24D57"
	var dialogStage: Stage = _
	var todoItem: TodoItem = _
	var importance: Importance = _

	def setTodoItem(item: TodoItem): Unit ={
		todoItem = item

		title.text = todoItem.title.value
		var localDate = new java.sql.Date(todoItem.dueDate.value.getTime).toLocalDate
		datePicker.setValue(localDate)

		importance = item.importance.value
		item.importance.value match {
			case Importance.Low =>
				setLowPriority()
			case Importance.Medium =>
				setMediumPriority()
			case Importance.High =>
				setHighPriority()
		}
	}

	/** Set the TodoItem's `importance` to low priority */
	def setLowPriority(): Unit = {
		selectPriorityButton(lowPriorityButton)
		importance = Importance.Low
	}
	/** Set the TodoItem's `importance` to medium priority */
	def setMediumPriority(): Unit = {
		selectPriorityButton(mediumPriorityButton)
		importance = Importance.Medium
	}
	/** Set the TodoItem's `importance` to high priority */
	def setHighPriority(): Unit = {
		selectPriorityButton(highPriorityButton)
		importance = Importance.High
	}

	/** Make changes when the button is clicked*/
	def handleOK(event: Event): Unit ={
		todoItem.title.value = title.text.value
		todoItem.importance.value = importance
		todoItem.dueDate.value = java.sql.Date.valueOf(datePicker.getValue)
		dialogStage.close()
	}

	private def selectPriorityButton(button: Button): Unit = {
		// Remove styling for all buttons
		lowPriorityButton.setStyle("")
		mediumPriorityButton.setStyle("")
		highPriorityButton.setStyle("")

		// Style the selected button
		button.style = "-fx-background-color: " + flatRedColorHex + "; " +
			"-fx-text-fill: white"
	}

}
