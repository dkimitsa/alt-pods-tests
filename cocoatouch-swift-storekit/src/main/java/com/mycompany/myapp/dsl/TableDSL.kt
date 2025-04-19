package com.mycompany.myapp.dsl

import org.robovm.apple.uikit.NSTextAlignment

/**
 * Simple DSL to build list of items for UITableView
 * Allows to define table content in form of code:
 * {  // this: TableBuilder
 *    Section("Section1") { // this: SectionBuilder
 *       Text("Cell1")
 *       Text("Cell2").fontSize(13.0)
 *       Text("Cell3")
 *       Action("hit me") { println("done!") }
 *    }
 * }
 */
object TableDSL {
    sealed interface TableItem {

        // UITableViewCellStyle.Default cell with one line text
        interface Text : TableItem {
            fun fontSize(value: Double): Text
            fun alignment(value: NSTextAlignment): Text
            fun onClick(action: Text.() -> Unit): Text
        }

        // UITableViewCellStyle.Value1 cell with detail text on right (as action)
        interface SubtitleText : TableItem {
            fun fontSize(value: Double): SubtitleText
            fun detailFontSize(value: Double): SubtitleText
            fun onClick(action: SubtitleText.() -> Unit): SubtitleText
        }

        // UITableViewCellStyle.Default cell with one text, default text color is blue
        interface Action : TableItem {
            fun fontSize(value: Double): Action
            fun alignment(value: NSTextAlignment): Action
        }
    }

    interface SectionBuilder {
        fun Text(title: String): TableItem.Text
        fun SubtitleText(title: String, subtitle: String): TableItem.SubtitleText
        fun Action(title: String, action: TableItem.Action.() -> Unit): TableItem.Action
    }

    interface TableBuilder : SectionBuilder {
        fun Section(title: String = "", action: SectionBuilder.() -> Unit)
    }
}

fun TableDSL.TableItem.Text.center() = alignment(NSTextAlignment.Center)
fun TableDSL.TableItem.Text.left() = alignment(NSTextAlignment.Left)
fun TableDSL.TableItem.Text.right() = alignment(NSTextAlignment.Right)

fun TableDSL.TableItem.Action.center() = alignment(NSTextAlignment.Center)
fun TableDSL.TableItem.Action.left() = alignment(NSTextAlignment.Left)
fun TableDSL.TableItem.Action.right() = alignment(NSTextAlignment.Right)


