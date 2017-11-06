package jh.listr.view

import jh.App
import scalafx.animation.TranslateTransition
import scalafx.event.ActionEvent
import scalafx.scene.image.ImageView
import scalafx.scene.layout.StackPane
import scalafx.util.Duration
import scalafxml.core.macros.sfxml

@sfxml
class RootMenuController(
                        private val todoListStackPane: StackPane,
                        private val timelineStackPane: StackPane,
                        private val settingsStackPane: StackPane,
                        private val trianglePointer: ImageView
                        ) {

	/**
	  * Display the TodoListView, and move the triangular pointer to the correct position
	  * */
	def showTodoListView(): Unit = {
		App.showToDoList()
		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 0
		transition.play()
	}
	/**
	  * Display the TimelineView, and move the triangular pointer to the correct position
	  * */
	def showTimelineView(): Unit = {
		App.showTimeline()
		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 113 - 33
		transition.play()
	}
	/**
	  * Display the SettingsView, and move the triangular pointer to the correct position
	  * */
	def showSettingsView(): Unit = {
		App.showSettings()
		val transition = new TranslateTransition(Duration(300), trianglePointer)
		transition.toY = 193 - 33
		transition.play()
	}

}
