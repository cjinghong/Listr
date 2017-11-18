package jh.listr.model
import Importance._

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class TodoCircle(item: TodoItem) extends Circle with SwipeDownToggle {

	val lightBlueColor = Color(68.0/255.0, 108.0/255.0, 179.0/255.0, 0.8)

	// If circle is "on" means item is not completed.
	// Circle on means the circle is visible
	// Circle off means the circle is semi-transparent
	// When an item is completed, the circle becomes semi-transparent
	item.completed <== !toggleOnProperty
	item.importance.value match {
		case Low =>
			radius = 50
		case Medium =>
			radius = 70
		case High =>
			radius = 100
	}

	fill = lightBlueColor
}
