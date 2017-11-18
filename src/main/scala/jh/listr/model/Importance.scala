package jh.listr.model
/**
  * An object to represent the ''importance'' of a TodoItem.
  *
  * This is to make sure there is an exhaustive value of importance, which is:
  * `Low`, `Medium`, and `High`
  *
  * For usage, please refer to [[jh.listr.model.TodoItem]]
  *
  */
object Importance extends Enumeration {
	type Importance = Value
	val Low = Value(0)
	val Medium = Value(1)
	val High = Value
}
