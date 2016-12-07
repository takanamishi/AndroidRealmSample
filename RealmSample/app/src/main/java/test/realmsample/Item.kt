package test.realmsample

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Item(
        @PrimaryKey open var id: Int = 0,
        open var name: String = "",
        open var content: String = "",
        open var likesCount: Int = 0
) : RealmObject()
