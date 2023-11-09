package uz.gita.chontak.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.gita.chontak.R
import uz.gita.chontak.data.Product
import uz.gita.chontak.repasitory.MyPref
import uz.gita.chontak.views.MainActivity


class Dialog(context: Context, val main: MainActivity) : BottomSheetDialog(context) {
    private lateinit var btnAdd: AppCompatButton
    private lateinit var sotganMiqdor: EditText
    private lateinit var sotganNarx: EditText
    private lateinit var ayzberk: TextView
    private lateinit var rukola: TextView
    private lateinit var balgar: TextView
    private lateinit var myata: TextView
    private lateinit var ukrop: TextView
    private lateinit var chinsey: TextView
    private lateinit var lolarosa: TextView
    private lateinit var brokli: TextView
    private lateinit var daykon: TextView
    private lateinit var baqlajon: TextView
    private lateinit var rediska: TextView
    private lateinit var boshqa: TextView
    private lateinit var btnCancel:ImageView

    private val list = ArrayList<TextView>()
    private val dataList = ArrayList<Product>()
    var name = ""
    lateinit var myPref: MyPref
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_view)
        setCancelable(false)
        findView()
        clickEvents()
        btnAdd.isClickable = false
        myPref = MyPref.getInstance()
    }

    private fun clickEvents() {

        boshqa.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (v.text.toString() != "") {
                btnAdd.isClickable = true
                false
            } else false
        })

        sotganMiqdor.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (boshqa.text.toString() != "") {
                btnAdd.isClickable = true
                false
            } else false
        })

        sotganNarx.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (boshqa.text.toString() != "") {
                btnAdd.isClickable = true
                false
            } else false
        })

        btnCancel.setOnClickListener{
            dismiss()
        }

        for (i in 0 until list.size) {
            list[i].setOnClickListener {
                setColour()
                list[i].setTextColor(Color.parseColor("#1CC623"))
                btnAdd.isClickable = true
                name = list[i].text.toString()
            }
        }

        btnAdd.setOnClickListener {
            if (sotganMiqdor.text.toString() == "" || sotganNarx.text.toString() == "") return@setOnClickListener
            val txt = boshqa.text.toString()
            if(txt != "") name = txt

            var sotganMiqdor = sotganMiqdor.text.toString().toInt()
            var sotganNarx = sotganNarx.text.toString().toInt()
            var foyda = sotganNarx * sotganMiqdor
            id = myPref.getId()
            id += 1
            myPref.saveId(id)

            val product = Product(
                id = id,
                name = name,
                miqdorSotdim = sotganMiqdor,
                sotdimNarx = sotganNarx,
                foyda = foyda,
                mijozName = main.mijozName
            )

            main.saveProduct(product)
            dismiss()
        }
    }

    private fun findView() {
        btnAdd = findViewById(R.id.btnAddd)!!
        rukola = findViewById(R.id.chRukola)!!
        chinsey = findViewById(R.id.chChinsey)!!
        ukrop = findViewById(R.id.chUkrop)!!
        balgar = findViewById(R.id.chBalgar)!!
        brokli = findViewById(R.id.chBrokli)!!
        myata = findViewById(R.id.chMyata)!!
        ayzberk = findViewById(R.id.chAzyberk)!!
        lolarosa = findViewById(R.id.chLolarosa)!!
        sotganMiqdor = findViewById(R.id.editSotganMiqdor)!!
        sotganNarx = findViewById(R.id.editSotganNarx)!!
        daykon = findViewById(R.id.chDaykon)!!
        rediska = findViewById(R.id.chRediska)!!
        baqlajon = findViewById(R.id.chBaqlajon)!!
        boshqa = findViewById(R.id.chBoshqa)!!
        btnCancel = findViewById(R.id.btnCancel)!!

        list.add(rukola)
        list.add(chinsey)
        list.add(ukrop)
        list.add(balgar)
        list.add(brokli)
        list.add(myata)
        list.add(ayzberk)
        list.add(lolarosa)
        list.add(daykon)
        list.add(rediska)
        list.add(baqlajon)
        list.add(boshqa)
    }

    private fun setColour() {
        for (i in 0 until list.size) {
            list[i].setTextColor(Color.parseColor("#FF000000"))
        }
    }


}