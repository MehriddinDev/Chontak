package uz.gita.chontak

import android.app.Application
import android.content.Context
import uz.gita.chontak.repasitory.DBHelper

class App:Application() {
    companion object{
        lateinit var context :Context
    }
    override fun onCreate() {
        super.onCreate()
        DBHelper.init(this)
        context = this
    }
}