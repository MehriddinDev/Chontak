package uz.gita.chontak.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.gita.chontak.R
import uz.gita.chontak.data.Mijoz
import uz.gita.chontak.dialog.MijozDialog
import uz.gita.chontak.repasitory.DBHelper
import uz.gita.chontak.repasitory.MyPref

class ChooseActivity : AppCompatActivity() {
    lateinit var btnAdd: FloatingActionButton
    lateinit var btn1: AppCompatButton
    lateinit var btn2: AppCompatButton
    lateinit var btn3: AppCompatButton
    lateinit var btn4: AppCompatButton
    lateinit var btn5: AppCompatButton
    lateinit var btn6: AppCompatButton
    lateinit var btn7: AppCompatButton
    lateinit var txtQodirjon: TextView
    lateinit var txtMijozlar: TextView
    val pref: MyPref
    lateinit var dbhelper: DBHelper

    init {
        pref = MyPref.getInstance()
    }

    val listBtn = ArrayList<AppCompatButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        findView()
        clickEvents()

        dbhelper = DBHelper.getInstance()
        setVisibilityToBtn()
    }

    /*fun saveMijoz(mijoz: Mijoz) {
        dbhelper.AddMijoz(mijoz)
        myAdapter.submitList(dbhelper.getMijoz())
    }*/

    private fun findView() {
        btnAdd = findViewById(R.id.btnAdd)
        btn1 = findViewById(R.id.btnMijoz1)
        btn2 = findViewById(R.id.btnMijoz2)
        btn3 = findViewById(R.id.btnMijoz3)
        btn4 = findViewById(R.id.btnMijoz4)
        btn5 = findViewById(R.id.btnMijoz5)
        btn6 = findViewById(R.id.btnMijoz6)
        btn7 = findViewById(R.id.btnMijoz7)

        txtQodirjon = findViewById(R.id.qodirjon)
        txtMijozlar = findViewById(R.id.txtMijozlar)

        listBtn.add(btn1)
        listBtn.add(btn2)
        listBtn.add(btn3)
        listBtn.add(btn4)
        listBtn.add(btn5)
        listBtn.add(btn6)
        listBtn.add(btn7)
    }


    fun setMijozBtn(mijoz: Mijoz) {
        for (btnTxt in listBtn){
            if(btnTxt.text.toString() == mijoz.name){
                Toast.makeText(this,"Bunday mijoz allaqachon kiritilgan", Toast.LENGTH_SHORT).show()
                break
            }

            if (btnTxt.text.toString() == ""){
                btnTxt.visibility = View.VISIBLE
                if(mijoz.name.length > 10){
                    btnTxt.setTextSize(18f)
                }else btnTxt.setTextSize(24f)

                btnTxt.text = mijoz.name
                break
            }
        }

    }

    private fun clickEvents() {

        btnAdd.setOnLongClickListener {
            txtQodirjon.visibility = View.GONE
            true
        }

        txtMijozlar.setOnClickListener {
            txtQodirjon.visibility = View.VISIBLE
        }

        btnAdd.setOnClickListener {
            val id = pref.getIdMijoz()
            if (id > 6) {
                Toast.makeText(this, "Shuncha mijoz yetar Qodirboy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dialog = MijozDialog(this, this)
            dialog.show()
        }

        //OnClick
        for (i in 0..6) {
            listBtn[i].setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("ism", listBtn[i].text.toString())
                startActivity(intent)
            }
        }

        //LongClick
        for (i in 0..6) {
            listBtn[i].setOnLongClickListener {
                val ism = listBtn[i].text.toString()
                val dialog = AlertDialog.Builder(this)
                    .setTitle("O'chirish")
                    .setMessage("Rostdan ushbu mijozni o'chirishni hohlaysizmi ?")
                    .setPositiveButton("Ha") { d, t ->
                        val listt = pref.getMijozName()
                        var number = pref.getIdMijoz()
                        if (listt != null) {
                            for (k in listt) {
                                if (k == ism) {
                                    number--
                                    pref.saveIdMijoz(number)
                                    val logic = listt.remove(ism)
                                    Log.d("OOO","$logic")
                                    pref.saveMijozName(listt)
                                    for (namm in pref.getMijozName()!!){
                                        Log.d("OOO","$namm")
                                    }
                                    break
                                }
                            }
                        }
                        dbhelper.deleteMijoz(ism)
                        listBtn[i].visibility = View.GONE
                        listBtn[i].text = ""
                    }
                    .setNegativeButton("Yo'q") { d, i ->

                    }
                    .create()

                dialog.show()
                true
            }
        }
    }

    private fun setVisibilityToBtn() {
        val list = pref.getMijozName()
        val index = pref.getIdMijoz()

        if (index == 0) return
        if (list != null) {
            var k = 0
            for (i in list) {
                if(i.length > 10){
                    listBtn[k].setTextSize(18f)
                }else listBtn[k].setTextSize(24f)
                listBtn[k].visibility = View.VISIBLE
                listBtn[k++].text = i
            }
        }
    }

}