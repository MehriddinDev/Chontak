package uz.gita.chontak.repasitory

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import uz.gita.chontak.App
import java.util.*
import kotlin.collections.HashSet

class MyPref private constructor() {
    val pref: SharedPreferences = App.context.getSharedPreferences("ID", Context.MODE_PRIVATE)
    val edit: SharedPreferences.Editor = pref.edit()

    companion object {
        private lateinit var pref: MyPref

        fun getInstance(): MyPref {
            if (!Companion::pref.isInitialized) {
                pref = MyPref()
            }
            return pref
        }
    }

    fun saveId(n: Int) {
        edit.putInt("Id", n).apply()
    }

    fun getId(): Int {
        return pref.getInt("Id", 0)
    }

    fun saveIdMijoz(n: Int) {
        edit.putInt("IdM", n).apply()
    }

    fun getIdMijoz(): Int {
        return pref.getInt("IdM", 0)
    }

    fun saveNegativeId(n: Int) {
        edit.putInt("X", n).apply()
    }

    fun getNegativeId(): Int {
        return pref.getInt("X", 0)
    }

    fun saveMijozName(names: MutableSet<String>?) {
        edit.putStringSet("NAMES", names).apply()
    }

    fun getMijozName(): MutableSet<String>? {
        val a : MutableSet<String>
        a = HashSet()
        return pref.getStringSet("NAMES", a)
    }

}