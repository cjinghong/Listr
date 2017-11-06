package jh.listr.view

import jh.App

import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafxml.core.macros.sfxml

@sfxml
class TimelineViewController(
        private val hbox: HBox
	) {

	hbox.children.clear()
	for (item <- App.todoItems) {
		val circle = Circle(0, 0, 100)
		circle.fill = Color(68.0/255.0, 108.0/255.0, 179.0/255.0, 0.8)

		hbox.children.add(circle)
	}


}
