package com.afgdevlab.expirydate.extensions

import android.content.Context
import android.content.Context.MODE_PRIVATE

//default olarak kullanmak istediğin değerleri buraya atamalısın
const val PREF_KEY_CURRENT_LANGUAGE = "PREF_KEY_CURRENT_LANGUAGE"

var defaultPrefFileName = PREF_KEY_CURRENT_LANGUAGE
var defaultMode: Int = MODE_PRIVATE

fun Context.setInt(
    intValue: Int, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putInt(tag, intValue).apply()
}

fun Context.getInt(
    tag: String, default: Int = 0,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): Int {
    return this.getSharedPreferences(prefFileName, mode).getInt(tag, default)
}

fun Context.setBool(
    boolValue: Boolean, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putBoolean(tag, boolValue).apply()
}

fun Context.getBool(
    tag: String,
    default: Boolean = false,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): Boolean {
    return this.getSharedPreferences(prefFileName, mode).getBoolean(tag, default)
}

fun Context.setString(
    string: String?, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putString(tag, string).apply()
}

fun Context.getString(
    tag: String, default: String? = null,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): String? {
    return this.getSharedPreferences(prefFileName, mode).getString(tag, default)
}

fun Context.setLong(
    longValue: Long, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putLong(tag, longValue).apply()
}

fun Context.getLong(
    tag: String, default: Long = 0L,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): Long {
    return this.getSharedPreferences(prefFileName, mode).getLong(tag, default)
}

fun Context.setFloat(
    floatValue: Float, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putFloat(tag, floatValue).apply()
}

fun Context.getFloat(
    tag: String, default: Float = 0F,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): Float {
    return this.getSharedPreferences(prefFileName, mode).getFloat(tag, default)
}

fun Context.setStringSet(
    strSet: Set<String?>?, tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
) {
    val sp = this.getSharedPreferences(prefFileName, mode)
    sp.edit().putStringSet(tag, strSet).apply()
}

fun Context.getStringSet(
    tag: String,
    prefFileName: String = defaultPrefFileName,
    mode: Int = defaultMode
): Set<String?>? {
    return this.getSharedPreferences(prefFileName, mode).getStringSet(tag, null)
}