package com.ddowney.speedrunbrowser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.ddowney.speedrunbrowser.R

/**
 * Expandable list adapter
 */
class ExpandingCategoryListAdapter(private val context : Context, private val listDataHeader : List<String>,
                                   private val listChildData : Map<String, List<String>>) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return listDataHeader[groupPosition]
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
        listHeader?.text = getGroup(groupPosition) as String

        return groupView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listChildData[listDataHeader[groupPosition]]?.size ?: 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return listChildData[listDataHeader[groupPosition]]?.get(childPosition)
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

        val recordPosition : TextView? = childView?.findViewById(R.id.record_position)
        recordPosition?.text = (childPosition+1).toString()

        val recordRunner : TextView? = childView?.findViewById(R.id.record_runner)
        recordRunner?.text = getChild(groupPosition, childPosition)

        val recordTime : TextView? = childView?.findViewById(R.id.record_time)
        recordTime?.text = getChild(groupPosition, childPosition)

        return childView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }



}