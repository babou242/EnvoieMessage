package com.example.envoiemessage.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Contact : RealmObject {
    @PrimaryKey var _id :ObjectId = ObjectId()
    var firstName :String = ""
    var lastName :String = ""
    var phoneNumber :String = ""
}
