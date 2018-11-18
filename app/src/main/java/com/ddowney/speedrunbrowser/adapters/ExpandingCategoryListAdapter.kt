package com.ddowney.speedrunbrowser.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.models.Categories
import com.ddowney.speedrunbrowser.models.Leaderboard
import com.ddowney.speedrunbrowser.utils.FormattingTools

/**
 * Expandable list adapter
 */
class ExpandingCategoryListAdapter(private val context : Context, private val groupHeadings: List<Categories>,
                                   private val childData: Map<String, List<Leaderboard.RunPosition>>) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return groupHeadings[groupPosition]
    }


    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, view: View?, parent: ViewGroup?): View? {

        var groupView = view

        if (groupView == null) {
            val inflater : LayoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            groupView = inflater.inflate(R.layout.category_list_group, null)
        }

        val listHeader : TextView? = groupView?.findViewById(R.id.category_list_header)
        listHeader?.text = (getGroup(groupPosition) as Categories).name

        val subtextView : TextView? = groupView?.findViewById(R.id.category_list_subtext)
        val group : Categories = getGroup(groupPosition) as Categories
        subtextView?.text = if (group.players.value == 1) {
            "Run has ${group.players.type} ${group.players.value} player"
        } else {
            "Run has ${group.players.type} ${group.players.value} players"
        }

        return groupView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childData[groupHeadings[groupPosition].name]?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return childData[groupHeadings[groupPosition].name]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean,
                              view: View?, parent: ViewGroup?): View? {

        var childView = view
        if (childView == null) {
            val inflater : LayoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            childView = inflater.inflate(R.layout.category_list_item, null)
        }

        val child : Leaderboard.RunPosition = (getChild(groupPosition, childPosition) as Leaderboard.RunPosition)

        val recordPosition : TextView? = childView?.findViewById(R.id.record_position)
        @SuppressLint("SetTextI18n")
        when(child.place) {
            1 -> recordPosition?.text = "${child.place}st"
            2 -> recordPosition?.text = "${child.place}nd"
            3 -> recordPosition?.text = "${child.place}rd"
            else -> recordPosition?.text = "${child.place}th"
        }

//        val recordRunner : TextView? = childView?.findViewById(R.id.record_runner)
//        if (child.run.players[0].name != "") {
//            recordRunner?.text = child.run.players[0].name
//        } else {
//            recordRunner?.text = child.run.players[0].id
//        }

        val recordTime : TextView? = childView?.findViewById(R.id.record_time)
        val formatter = FormattingTools()
        recordTime?.text = formatter.getReadableTime(child.run.times.primary_t)

        return childView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groupHeadings.size
    }

}