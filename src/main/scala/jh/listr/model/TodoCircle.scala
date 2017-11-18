package jh.listr.model
import Importance._

import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.text.{Font, Text, TextBoundsType}

/** A StackPane that includes a circle, and a text in the middle. This represents a single TodoItem
  *
  * The size of the circle varies based on the importance of the TodoItem.
  * The text will be centered in the circle.
  *
  *  @constructor create a new TodoCircle with the given todo item
  *  @param item the todo item
  */
class TodoCircle(item: TodoItem) extends StackPane with SwipeDownToggle {

	// Todo change this to follow template
	// This is a lightblue color.
	val circleColor = Color(68.0/255.0, 108.0/255.0, 179.0/255.0, 0.8)

	// Set the initial state for the toggling on and off
	toggleOnProperty.value = !item.completed.value

	// If circle is "on" means item is not completed.
	// Circle on means the circle is visible
	// Circle off means the circle is semi-transparent
	// When an item is completed, the circle becomes semi-transparent
	toggleOnProperty.onChange({ (_, _, newValue) =>
		item.completed.value = !newValue
	})

	// circle
	val circle: Circle = Circle(0)
	item.importance.value match {
		case Low =>
			circle.radius = 100
		case Medium =>
			circle.radius = 120
		case High =>
			circle.radius = 150
	}
	circle.fill = circleColor

	// text
	val text: Text = new Text() {
		text <== item.title
		font = Font(20)
		fill = Color.White
		boundsType = TextBoundsType.Visual
	}

	// Adds circle and text to self
	this.children.addAll(circle, text)
}
