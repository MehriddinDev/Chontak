package uz.gita.chontak

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.chontak.data.Natija
import uz.gita.chontak.data.Product
import uz.gita.chontak.repasitory.DBHelper
import uz.gita.chontak.views.MainActivity

class Adapter(private val main: MainActivity) :
    ListAdapter<Product, RecyclerView.ViewHolder>(myCallback) {
    private lateinit var map: HashMap<Int, Natija>

    fun setMap(map1: HashMap<Int, Natija>) {
        map = map1
    }

    object myCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }
    }

    inner class MyViewHolder1(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.productName)
        val miqdorSotdim: TextView = view.findViewById(R.id.sotdimMiqdor)
        val foyda: TextView = view.findViewById(R.id.foyda)
        var sotdimNarx: TextView = view.findViewById(R.id.sotdimNarx)

        init {
            val db = DBHelper.getInstance()

            view.setOnLongClickListener {
                val dialog = AlertDialog.Builder(view.context)
                dialog.setTitle("O'chirilsinmi ?")
                    .setPositiveButton("Ha") { d, k ->
                        db.deleteOneCell(getItem(adapterPosition).id)
                        //submitList(db.getProduct(main.mijozName))
                        main.setMapFromAdapter()
                        submitList(db.getProduct(main.mijozName))
                        notifyDataSetChanged()

                    }
                    .setNegativeButton("Yo'q"){d,i->}
                    .create()

                dialog.show()
                true
            }
        }

        fun bind() {
            val item = getItem(adapterPosition)
            val miqdor = item.miqdorSotdim.toString()
            val narx = item.sotdimNarx.toString()
            val foydam = item.foyda.toString()

            var m = ""
            var k = 0
            for (i in miqdor.length - 1 downTo 0) {
                if ((k) % 3 == 0 && k != 0) {
                    m += "."
                }
                m += miqdor[i]
                k++
            }
            m = m.reversed()
            k = 0

            var f = ""
            for (i in foydam.length - 1 downTo 0) {
                if ((k) % 3 == 0 && k != 0) {
                    f += "."
                }
                f += foydam[i]
                k++
            }
            f = f.reversed()
            k = 0

            var n = ""
            for (i in narx.length - 1 downTo 0) {
                if ((k) % 3 == 0 && k != 0) {
                    n += "."
                }
                n += narx[i]
                k++
            }
            n = n.reversed()

            val nom = item.name

            if (nom.length > 9) {
                name.setTextSize(16f)
            } else name.setTextSize(22f)

            name.text = nom
            miqdorSotdim.text = m
            sotdimNarx.text = n
            foyda.text = f
        }

    }

    inner class SecondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foyda: TextView = view.findViewById(R.id.jamiDaromad)
        val time: TextView = view.findViewById(R.id.time)

        init {
            val db = DBHelper.getInstance()
            view.setOnClickListener {
                submitList(db.getProduct(main.mijozName))
            }
            view.setOnLongClickListener {
                submitList(db.getSummaryProduct(main.mijozName))
                true
            }
        }

        fun bind() {
            val item = map.get(miqdor)
            val foydam = item?.foyda.toString()
            var k = 0
            var f = ""
            for (i in foydam.length - 1 downTo 0) {
                if ((k) % 3 == 0 && k != 0) {
                    f += "."
                }
                f += foydam[i]
                k++
            }
            f = f.reversed()

            foyda.text = f
            time.text = item?.time
        }
    }

    var miqdor = 0
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        miqdor = item.miqdorSotdim
        Log.d("nnn","${item.foyda}")

        if (miqdor < 0) {
            return 2
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) return MyViewHolder1(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
        else return SecondViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.finish_view, parent, false)
        )
    }


    lateinit var holder1: MyViewHolder1
    lateinit var holder2: SecondViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            1 -> {
                holder1 = holder as MyViewHolder1
                holder1.bind()
            }

            2 -> {
                holder2 = holder as SecondViewHolder
                holder2.bind()
            }
        }
    }


}