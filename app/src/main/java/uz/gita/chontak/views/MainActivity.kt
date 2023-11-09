package uz.gita.chontak.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import uz.gita.chontak.Adapter
import uz.gita.chontak.R
import uz.gita.chontak.data.Natija
import uz.gita.chontak.data.Product
import uz.gita.chontak.dialog.Dialog
import uz.gita.chontak.repasitory.DBHelper
import uz.gita.chontak.repasitory.MyPref
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd: AppCompatButton
    private lateinit var btnTugat: AppCompatButton
    private lateinit var rvList: RecyclerView
    private lateinit var myAdapter: Adapter
    private lateinit var dbHelper: DBHelper
    private lateinit var a: AppCompatButton
    private lateinit var pref: MyPref
    var mijozName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_view)
        window.statusBarColor = Color.parseColor("#001A63")
        findView()
        myAdapter = Adapter(this)
        rvList.adapter = myAdapter
        dbHelper = DBHelper.getInstance()

        //intent
        val intent: Intent = getIntent()
        mijozName = intent.getStringExtra("ism")!!

        //intent
        myAdapter.submitList(dbHelper.getProduct(mijozName))

        pref = MyPref.getInstance()
        myAdapter.setMap(setHashMap())
    }

    fun saveProduct(product: Product) {
        dbHelper.addProduct(product)
        myAdapter.submitList(dbHelper.getProduct(mijozName))
    }

    private fun findView() {
        btnAdd = findViewById(R.id.btnAdd)
        btnTugat = findViewById(R.id.btnFinish)
        rvList = findViewById(R.id.rvList)
        setClickEvents()
    }


    private fun setClickEvents() {

        btnAdd.setOnClickListener {
            val dialog = Dialog(this, this)
            dialog.show()
        }

        btnTugat.setOnClickListener {

            if (dbHelper.getLastAmount() <= 0) return@setOnClickListener

            var negativeId = pref.getNegativeId()
            negativeId--
            pref.saveNegativeId(negativeId)

            val sdf = SimpleDateFormat("ddMMyyyy")
            val times = sdf.format(Date()).toInt()

            dbHelper.addProduct(
                Product(0, "", negativeId, times, 0, mijozName)
            )

            myAdapter.setMap(setHashMap())
            myAdapter.submitList(dbHelper.getProduct(mijozName))
        }

    }

    fun setMapFromAdapter() {
        myAdapter.setMap(setHashMap())
    }

    private fun setHashMap(): HashMap<Int, Natija> {
        var foyda = 0
        var time = ""
        var s = ""

        val map = HashMap<Int, Natija>()
        val data = dbHelper.getProduct(mijozName)
        for (i in 0 until data.size) {
            val k = data[i].miqdorSotdim
            foyda += data[i].foyda
            time = data[i].sotdimNarx.toString()

            if (k < 0) {
                if (time.length == 7) {
                    s = ""
                    for (i in 0 until time.length) {
                        if (i == 1) {
                            s += "-"
                        } else if (i == 3) {
                            s += "-"
                        }
                        s += time[i]
                    }
                } else {
                    s = ""
                    for (i in 0 until time.length) {
                        if (i == 2) {
                            s += "-"
                        } else if (i == 4) {
                            s += "-"
                        }
                        s += time[i]
                    }
                }

                map.put(k, Natija(foyda, s))

                foyda = 0
            }
        }
        return map
    }


}
