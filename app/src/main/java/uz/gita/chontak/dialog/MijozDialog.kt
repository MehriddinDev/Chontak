package uz.gita.chontak.dialog

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.gita.chontak.R
import uz.gita.chontak.data.Mijoz
import uz.gita.chontak.repasitory.MyPref
import uz.gita.chontak.views.ChooseActivity

class MijozDialog(context: Context, val main: ChooseActivity) : BottomSheetDialog(context) {
    lateinit var name: EditText
    lateinit var btnAdd: AppCompatButton
    var pref: MyPref

    init {
        pref = MyPref.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_mijoz_item)
        findView()
        clickEvent()

        pref = MyPref.getInstance()
        
    }

    private fun findView() {
        name = findViewById(R.id.editMijozMame)!!
        btnAdd = findViewById(R.id.btnAddb)!!

    }

    private fun clickEvent() {
        val names = pref.getMijozName()
        btnAdd.setOnClickListener {
            val name_ = name.text.toString()
            if (name_ == "") return@setOnClickListener

            names?.add(name_)
            pref.saveMijozName(names)

            var id = pref.getIdMijoz()

           // val list = pref.getMijozName()
          /*  if (list != null) {
                for (names in list) {
                    if (names == name_) return@setOnClickListener
                }
            }*/
            main.setMijozBtn(Mijoz(id, name_))

            id += 1
            pref.saveIdMijoz(id)
            dismiss()
        }
    }

}