package org.techtown.one.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.techtown.one.R
import org.techtown.one.auth.MemberModel

class MemberLVAdapter (val memberList : MutableList<MemberModel>) : BaseAdapter()  {

    override fun getCount(): Int {
        return memberList.size
    }

    override fun getItem(position: Int): Any {
        return memberList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        if(view == null) {

            view = LayoutInflater.from(parent?.context).inflate(R.layout.member_list_item, parent, false)
        }

        val studentId = view?.findViewById<TextView>(R.id.stduentId)
        val studentName = view?.findViewById<TextView>(R.id.studentName)

        studentId!!.text = memberList[position].studentId
        studentName!!.text = memberList[position].name

        return view!!
    }

}