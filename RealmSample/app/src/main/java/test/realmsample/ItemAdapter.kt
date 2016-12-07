package test.realmsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import io.realm.RealmBaseAdapter
import io.realm.RealmResults

class ItemAdapter(context: Context, realmResult: RealmResults<Item>): RealmBaseAdapter<Item>(context, realmResult), ListAdapter {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false).apply {
            this.tag = ViewHolder(this)
        }
        val holder = view.tag as ViewHolder
        val data = this.adapterData ?: return view
        val item = data[position]
        holder.setUp(item = item)

        return view
    }

    private class ViewHolder(view: View) {
        private val nameTextView = view.findViewById(R.id.name) as TextView
        private val likesCountTextView = view.findViewById(R.id.likesCount) as TextView

        fun setUp(item: Item) {
            this.nameTextView.text = item.name
            this.likesCountTextView.text = item.likesCount.toString()
        }
    }
}