package jh.listr.view

import javafx.scene.Parent

import jh.App

import scalafx.scene.control.ScrollPane
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.layout.HBox
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class TimelineViewController(
        private val scrollPane: ScrollPane,
        private val hbox: HBox
	) {

	hbox.children.clear()
	for (item <- App.todoItems) {

		// Load timeline card, and set its controller's todoitem.
		val timelineCard = getClass.getResourceAsStream("TimelineCard.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(timelineCard)
		val roots = loader.getRoot[Parent]()
		val timelineCardController = loader.getController[TimelineCardController#Controller]()
		timelineCardController.setItem(item)

		// Adds timeline card to hbox
		hbox.children.add(roots)
	}


}
