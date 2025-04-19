package com.mycompany.myapp.dsl

import org.robovm.apple.foundation.NSIndexPath
import org.robovm.apple.uikit.NSLineBreakMode
import org.robovm.apple.uikit.NSTextAlignment
import org.robovm.apple.uikit.UIColor
import org.robovm.apple.uikit.UITableView
import org.robovm.apple.uikit.UITableViewCell
import org.robovm.apple.uikit.UITableViewCellAccessoryType
import org.robovm.apple.uikit.UITableViewCellSelectionStyle
import org.robovm.apple.uikit.UITableViewCellSeparatorStyle
import org.robovm.apple.uikit.UITableViewCellStyle
import org.robovm.apple.uikit.UITableViewDataSourceAdapter
import org.robovm.apple.uikit.UITableViewDelegateAdapter
import kotlin.collections.getOrNull

/**
 * DSL implementation that will produce list of Section/Cell and provide
 * UITableViewSource/Delegate to present it in tableView using basic UITableViewCell
 *
 * Attaches to table, calls content to build initial presentation
 */
class UITableViewContentDSL<T>(
    private val tableView: UITableView,
    initialData: T? = null,
    private val content: TableDSL.TableBuilder.(data: T?) -> Unit
) {
    private var sections: List<DslImpl.TableSection>

    init {
        sections = DslImpl.TableBuilder.build { content(initialData) }
        tableView.setDataSource(dataSource())
        tableView.setDelegate(delegate())
        tableView.separatorStyle = UITableViewCellSeparatorStyle.None
    }

    fun submitData(data: T?) {
        sections = DslImpl.TableBuilder.build { content(data) }
        tableView.reloadData()
    }


    ///
    /// internals
    ///

    private fun dataSource() = object: UITableViewDataSourceAdapter() {
        override fun getNumberOfSections(table: UITableView) = sections.size.toLong()
        override fun getNumberOfRowsInSection(table: UITableView, index: Long) =
            sections[index.toInt()].items.size.toLong()

        override fun getTitleForHeader(table: UITableView, index: Long) = sections[index.toInt()].title

        private fun UITableView.dequeue(
            id: String,
            style: UITableViewCellStyle, init: (UITableViewCell) -> Unit = {}
        ): UITableViewCell {
            return dequeueReusableCell(id) ?: UITableViewCell(style, id).also(init)
        }

        override fun getCellForRow(table: UITableView, indexPath: NSIndexPath): UITableViewCell {
            val cell = sections[indexPath.section].items[indexPath.row]
            return when(cell) {
                is DslImpl.Text -> table.dequeue("TextItem", UITableViewCellStyle.Default)
                    .also {
                        it.textLabel.text = cell.title
                        val pendingSize = cell.fontSize ?: 17.0
                        if (it.textLabel.font.pointSize != pendingSize)
                            it.textLabel.font = it.textLabel.font.newWithSize(pendingSize)
                        if (it.textLabel.textAlignment != cell.alignment)
                            it.textLabel.textAlignment = cell.alignment
                        it.textLabel.textAlignment
                    }

                is DslImpl.SubtitleText -> table.dequeue("SubtitleItem", UITableViewCellStyle.Value1, init = {
                    it.detailTextLabel.lineBreakMode = NSLineBreakMode.WordWrapping
                    it.detailTextLabel.numberOfLines = 0
                }).also {
                    it.textLabel.text = cell.title
                    it.detailTextLabel.text = cell.subtitle
                    it.textLabel.text = cell.title
                    val pendingSize = cell.fontSize ?: 17.0
                    if (it.textLabel.font.pointSize != pendingSize)
                        it.textLabel.font = it.textLabel.font.newWithSize(pendingSize)
                    val detailPendingSize = cell.fontSize ?: 14.0
                    if (it.detailTextLabel.font.pointSize != detailPendingSize)
                        it.detailTextLabel.font = it.detailTextLabel.font.newWithSize(detailPendingSize)
                }

                is DslImpl.Action -> table.dequeue("ActionItem", UITableViewCellStyle.Default, init = {
                    it.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
                    it.selectionStyle = UITableViewCellSelectionStyle.Blue
                }).also {
                    it.textLabel.textColor = UIColor.blue()
                    it.textLabel.text = cell.title
                    val pendingSize = cell.fontSize ?: 17.0
                    if (it.textLabel.font.pointSize != pendingSize)
                        it.textLabel.font = it.textLabel.font.newWithSize(pendingSize)
                    if (it.textLabel.textAlignment != cell.alignment)
                        it.textLabel.textAlignment = cell.alignment
                }
                else -> throw IllegalArgumentException("Unsupported data")
            }
        }
    }

    private fun delegate() = object: UITableViewDelegateAdapter() {
        override fun didSelectRow(table: UITableView, indexPath: NSIndexPath) {
            val cell = sections.getOrNull(indexPath.section)?.items?.getOrNull(indexPath.row)
            when (cell) {
                is DslImpl.Text -> cell.action?.invoke(cell)
                is DslImpl.SubtitleText -> cell.action?.invoke(cell)
                is DslImpl.Action -> cell.action(cell)
                else -> {}
            }
        }
    }

    ///
    /// implementations of DSL
    ///
    private object DslImpl {
        data class Text(
            val title: String,
            var fontSize: Double? = null,
            var alignment: NSTextAlignment = NSTextAlignment.Left,
            var action: (Text.() -> Unit)? = null
        ) : TableDSL.TableItem.Text {
            override fun fontSize(value: Double) = apply { fontSize = value }
            override fun alignment(value: NSTextAlignment) = apply { alignment = value }
            override fun onClick(action: TableDSL.TableItem.Text.() -> Unit) = apply { this.action = action }
        }

        data class SubtitleText(
            val title: String,
            val subtitle: String,
            var fontSize: Double? = null,
            var detailFontSize: Double? = null,
            var action: (SubtitleText.() -> Unit)? = null
        ) : TableDSL.TableItem.SubtitleText {
            override fun fontSize(value: Double) = apply { fontSize = value }
            override fun detailFontSize(value: Double) = apply { detailFontSize = value }
            override fun onClick(action: TableDSL.TableItem.SubtitleText.() -> Unit) = apply { this.action = action }
        }

        data class Action(
            val title: String,
            var fontSize: Double? = null,
            var alignment: NSTextAlignment = NSTextAlignment.Center,
            val action: Action.() -> Unit
        ) : TableDSL.TableItem.Action {
            override fun fontSize(value: Double) = apply { fontSize = value }
            override fun alignment(value: NSTextAlignment) = apply { alignment = value }
        }

        /// table section, will capture items
        data class TableSection(
            var title: String = "",
            val items: MutableList<TableDSL.TableItem> = mutableListOf()
        ) : TableDSL.SectionBuilder {
            override fun Text(title: String) = DslImpl.Text(title = title).also(items::add)
            override fun SubtitleText(title: String, subtitle: String) =
                DslImpl.SubtitleText(title, subtitle).also(items::add)
            override fun Action(title: String, action: TableDSL.TableItem.Action.() -> Unit) =
                DslImpl.Action(title, action = action).also(items::add)
        }

        /// table -- will capture sections/items
        class TableBuilder : TableDSL.TableBuilder {
            val sections = mutableListOf<TableSection>()

            companion object {
                fun build(builder: TableBuilder.() -> Unit): List<TableSection> {
                    val context = TableBuilder()
                    builder(context)
                    context.closeOpenedSection()
                    return context.sections.toList()
                }
            }

            private var openedSection: TableSection? = null
            private fun requireOpenedSection(): TableSection {
                return openedSection ?: TableSection().also { openedSection = it }
            }

            private fun closeOpenedSection() {
                openedSection?.let { sections.add(it) }
                openedSection = null
            }

            override fun Text(title: String) = DslImpl.Text(title).also { requireOpenedSection().items.add(it) }
            override fun SubtitleText(title: String, subtitle: String) =
                DslImpl.SubtitleText(title, subtitle).also { requireOpenedSection().items.add(it) }
            override fun Action(title: String, action: TableDSL.TableItem.Action.() -> Unit) =
                DslImpl.Action(title, action = action).also { requireOpenedSection().items.add(it) }
            override fun Section(title: String, action: TableDSL.SectionBuilder.() -> Unit) {
                closeOpenedSection()
                TableSection(title).let {
                    action(it)
                    sections.add(it)
                }
            }
        }
    }
}