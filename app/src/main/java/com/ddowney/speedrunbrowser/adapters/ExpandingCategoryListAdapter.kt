package com.ddowney.speedrunbrowser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.models.CategoriesModel
import com.ddowney.speedrunbrowser.models.LeaderboardModel

/**
 * Expandable list adapter
 */
class ExpandingCategoryListAdapter(private val context : Context, private val groupHeadings: List<CategoriesModel>,
                                   private val childData: Map<String, List<LeaderboardModel.RunPosition>>) : BaseExpandableListAdapter() {

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
        listHeader?.text = (getGroup(groupPosition) as CategoriesModel).name

        val subtextView : TextView? = groupView?.findViewById(R.id.category_list_subtext)
        val group : CategoriesModel = getGroup(groupPosition) as CategoriesModel
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

        val child : LeaderboardModel.RunPosition = (getChild(groupPosition, childPosition) as LeaderboardModel.RunPosition)

        val recordPosition : TextView? = childView?.findViewById(R.id.record_position)
        recordPosition?.text = child.place.toString()

        val recordRunner : TextView? = childView?.findViewById(R.id.record_runner)
        if (child.run.players[0].name != "") {
            recordRunner?.text = child.run.players[0].name
        } else {
            recordRunner?.text = child.run.players[0].id
        }

        val recordTime : TextView? = childView?.findViewById(R.id.record_time)
        recordTime?.text = child.run.times.primary

        return childView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groupHeadings.size
    }

}