package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class DirectoryAdapter(val datas: MutableList<DirectoryModel>): BaseAdapter() {
    override fun getCount(): Int = datas.size

    override fun getItem(position: Int): Any = datas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.layout_directory_item, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }

        val data = datas[position]
        viewHolder.textDirectoryName.text = data.directoryName
        if (data.directoryType == "folder") viewHolder.icon.setImageResource(R.drawable.baseline_folder_24)
        else viewHolder.icon.setImageResource(R.drawable.baseline_insert_drive_file_24)

        return itemView
    }

    class ViewHolder(itemView: View) {
        val textDirectoryName: TextView
        val icon: ImageView

        init {
            textDirectoryName = itemView.findViewById(R.id.directory_name)
            icon = itemView.findViewById(R.id.icon)
        }
    }
}