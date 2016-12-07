package test.realmsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import butterknife.bindView

class ItemActivity : AppCompatActivity() {
    val contentTextView: TextView by bindView(R.id.content)
    val likesCountTextView: TextView by bindView(R.id.likes_count)
    val likeButton: Button by bindView(R.id.like)
    val deleteButton: Button by bindView(R.id.delete)
    lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val itemId = this.intent.getIntExtra("itemId", 0)
        val realm = RealmSampleApplication.instance.inMemoryRealm
        this.item = realm.where(Item::class.java).equalTo("id", itemId).findFirst() ?: return

        item.addChangeListener<Item> {
            this.likesCountTextView.text = item.likesCount.toString()
        }

        this.setUpViews(item = item)

        this.likeButton.setOnClickListener {
            realm.executeTransaction {
                item.likesCount += 1
            }
        }

        this.deleteButton.setOnClickListener {
            realm.executeTransaction {
                item.deleteFromRealm()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.item.removeChangeListeners()
    }

    private fun setUpViews(item: Item) {
        this.contentTextView.text = item.name
        this.likesCountTextView.text = item.likesCount.toString()
    }
}