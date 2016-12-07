package test.realmsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import butterknife.bindView
import io.realm.Realm
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    private val realm = Realm.getDefaultInstance()
    private val items: RealmResults<Item> = realm.where(Item::class.java).findAll()
    private val listView: ListView by bindView(R.id.list_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.title = "将来なりたい職業"
        this.setUpView()

        // 一覧を取得する API をコールする
        // 結果が取得できたら Realm に保存する
        // 今回は説明を簡単にする為 API 経由で情報の取得が完了したとして、データをセットします。
        this.saveRealmData()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.realm.close()
    }

    private fun setUpView() {
        val adapter = ItemAdapter(context = this, realmResult = items)
        this.listView.adapter = adapter
        this.listView.setOnItemClickListener { adapterView, view, position, id ->
            val item = this.items[position]
            val intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("itemId", item.id)
            this.startActivity(intent)
        }
    }

    private fun saveRealmData() {
        val pilot = Item(id = 1, name = "パイロット", content = "空を飛びたい", likesCount = 7)
        val carpenter = Item(id = 2, name = "大工", content = "自分の家を建てたい", likesCount = 4)
        val programer = Item(id = 3, name = "プログラマー", content = "キーボードが好き", likesCount = 9999)

        this.realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(pilot)
            realm.copyToRealmOrUpdate(carpenter)
            realm.copyToRealmOrUpdate(programer)
        }
    }
}
