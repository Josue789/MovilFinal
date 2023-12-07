package com.example.mynotes
import android.net.Uri
import androidx.room.TypeConverter

@TypeConverter
fun fromStringList(stringList: List<Uri>?): String? {
    return stringList?.joinToString(separator = ",") { it.toString() }
}
@TypeConverter
fun toStringList(stringListString: String?): List<Uri>? {
    return stringListString?.split(",")?.map { Uri.parse(it)}
}