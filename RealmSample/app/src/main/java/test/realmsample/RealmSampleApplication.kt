package test.realmsample

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmSampleApplication : Application() {
    /**
     * キャッシュデータ用のRealmオブジェクト
     * インスタンスを保持しつづけていないとRealmの仕様でキャッシュデータが消えてしまうため
     * アプリケーションクラスで保持する
     */
    lateinit var inMemoryRealm: Realm

    companion object {
        lateinit var instance: RealmSampleApplication
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // InMemory な Realm の config を設定する
        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
                .name("inMemory.realm")
                .inMemory()
                .build()
        Realm.setDefaultConfiguration(config)
        this.inMemoryRealm = Realm.getInstance(config)
    }
}
