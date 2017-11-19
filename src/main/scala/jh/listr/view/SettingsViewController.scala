package jh.listr.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, ChoiceBox, Label}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, HBox}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.animation.{FillTransition, TranslateTransition}
import scalafx.beans.property.BooleanProperty
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.util.Duration
import scalafx.stage.FileChooser

@sfxml
class SettingsViewController(
                              // General tab variable
                              private val gn_hbox: HBox,
                              private val gn_anchor: AnchorPane,
                              private val gn_tog_circle: Circle,
                              private val gn_tog_rec: Rectangle,
                              private val gn_browse: Button,
                              private val gn_del_period: ChoiceBox[ObservableBuffer[App]],

                              // Appearance tab variable
                              private val ap_hbox: HBox,
                              private val ap_cancel: Button,
                              private val ap_save: Button,
                              private val ap_t_red: Button,
                              private val ap_t_blue: Button,
                              private val ap_t_silver: Button,
                              private val ap_t_black: Button

) {
    private def settingsInitialize: Unit = {
      ap_hbox.visible_=(false)
      gn_hbox.visible_=(false)
    }

    private var toggle = false

    private def toggleSwitch(): Unit = {
      if (!toggle) {
        val transition = new TranslateTransition(Duration(200), gn_tog_circle)
        transition.toX = 20
        transition.play()
        val fill = new FillTransition(Duration(200), gn_tog_rec, Color.White, Color.LightGreen)
        fill.play()
        toggle <== true
        gn_del_period.disable = false

      } else {
        val transition = new TranslateTransition(Duration(200), gn_tog_circle)
        transition.toX = 0
        transition.play()
        val fill = new FillTransition(Duration(200), gn_tog_rec, Color.LightGreen, Color.White)
        fill.play()
        toggle <== false
        gn_del_period.disable = true
      }
    }

  gn_tog_rec.onMouseClicked =(e:MouseEvent) => toggleSwitch()
  gn_tog_circle.onMouseClicked =(e:MouseEvent) => toggleSwitch()
  settingsInitialize
}
