package jh.listr.view

import jh.App
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javax.swing.event.{ChangeEvent, ChangeListener}

import scalafx.collections.ObservableBuffer
import scalafx.scene.control._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.animation.{FillTransition, TranslateTransition}
import scalafx.beans.property.BooleanProperty
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.text.Font
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.util.Duration
import scalafx.stage.{FileChooser, Modality}

@sfxml
class SettingsViewController(
                              // General tab variable
                              private val gn_hbox: HBox,
                              private val gn_anchor: AnchorPane,
                              private val gn_tog_circle: Circle,
                              private val gn_tog_rec: Rectangle,
                              private val gn_browse: Button,
                              private val gn_del_period: ComboBox[String],
                              private val gn_save_bar: TextField,

                              // Appearance tab variable
                              private val ap_hbox: HBox,
                              private val ap_cancel: Button,
                              private val ap_font: ComboBox[String],
                              private val ap_save: Button,
                              private val ap_t_red: Button,
                              private val ap_t_blue: Button,
                              private val ap_t_silver: Button,
                              private val ap_t_black: Button

                            ) {
  private def settingsInitialize: Unit = {
    ap_hbox.visible_=(false)
    gn_hbox.visible_=(false)
    val delPeriod = List("2 Days", "3 Days", "1 Week")
    val fonts = scalafx.scene.text.Font.families
    for(x <- delPeriod) {
      gn_del_period.+=(x)
    }
    //http://code.makery.ch/library/javafx-8-tutorial/part4/
    //https://github.com/scalafx/scalafx/blob/master/scalafx/src/main/scala/scalafx/beans/value/ObservableValue.scala
    for(x <- fonts) {
      ap_font.+=(x)
    }
  }
  settingsInitialize

  val obj = ObservableBuffer(gn_del_period)
  var changed = false
  private var toggle = false

  obj.onChange(showSave())
  gn_del_period.valueProperty().onChange(showSave())
  ap_font.valueProperty().onChange(showSave())

  private def toggleSwitch(duration: Int, x: Color, y: Color, circ: Circle, rect: Rectangle, pos: Int): Unit = {
    val transition = new TranslateTransition(Duration(duration), circ)
    transition.toX = pos
    transition.play()
    val fill = new FillTransition(Duration(duration), rect, x, y)
    fill.play()
  }

  private def toggleSwitch(): Unit = {
    if (!toggle) {
      toggleSwitch(200, Color.White, Color.LightGreen, gn_tog_circle, gn_tog_rec, 20)
      toggle <== true
      gn_del_period.disable = false

    } else {
      toggleSwitch(200, Color.LightGreen, Color.White, gn_tog_circle, gn_tog_rec, 0)
      toggle <== false
      gn_del_period.disable = true
    }
  }

  private def showSave(): Unit = {
    ap_hbox.visible = true
    gn_hbox.visible = true
  }

  private def saveLocation(): Unit = {
    val save = new FileChooser()
    save.setTitle("Save Database Location")
    save.getExtensionFilters().add(new ExtensionFilter("Database File", "*.db"))
    val saveWindow = save.showSaveDialog(
      ownerWindow = App.stage
    )

    if (saveWindow != null) {
      val savelocation = saveWindow.getAbsolutePath
      gn_save_bar.text = savelocation

    }
  }

  gn_tog_rec.onMouseClicked =(e:MouseEvent) => toggleSwitch()
  gn_tog_circle.onMouseClicked =(e:MouseEvent) => toggleSwitch()
  gn_browse.onMouseClicked = (e:MouseEvent) => saveLocation()
  gn_save_bar.onMouseClicked = (e:MouseEvent) => saveLocation()
}
