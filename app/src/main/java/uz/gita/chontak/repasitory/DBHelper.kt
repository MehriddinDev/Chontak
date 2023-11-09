package uz.gita.chontak.repasitory

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.gita.chontak.data.Product

class DBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, "Chontak", null, 1) {
    private val tableName = "product"
    private val id = "id"
    private val name = "name"
    private var mijoz = "mijozName"
    private val miqdorSotdim = "miqdorSotdim"
    private val sotdimNarx = "sotdimNarx"
    private val foyda = "foyda"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $tableName( $id INTEGER PRIMARY KEY AUTOINCREMENT, $name TEXT,$miqdorSotdim INTEGER, $sotdimNarx INTEGER, $foyda INTEGER, $mijoz TEXT);")
    }

    fun addProduct(product: Product) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("$name", product.name)
        contentValues.put("$miqdorSotdim", product.miqdorSotdim)
        contentValues.put("$sotdimNarx", product.sotdimNarx)
        contentValues.put("$foyda", product.foyda)
        contentValues.put("$mijoz", product.mijozName)

        db.insert("$tableName", null, contentValues)
    }

    fun getProduct(name_: String): List<Product> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName WHERE $mijoz = '$name_'", null)
        val data = ArrayList<Product>(cursor.count)
        if (cursor.moveToFirst()) {
            do {
                data.add(
                    Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }

        return data
    }

    fun getSummaryProduct(nname:String): List<Product> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName WHERE $miqdorSotdim < 0 AND $mijoz = '$nname'", null)

        val data = ArrayList<Product>(cursor.count)

        if (cursor.moveToFirst()) {
            do {
                data.add(
                    Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)

                    )
                )
            } while (cursor.moveToNext())
        }
        return data
    }

    fun deleteMijoz(name_:String){
       val db = this.writableDatabase
        db.delete("$tableName","$mijoz = '$name_'",null)
    }

    fun deleteOneCell(id_:Int){
        val db = this.writableDatabase
        db.delete("$tableName","$id = '$id_'",null)
    }

    @SuppressLint("Range")
    fun getLastAmount(): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName", null)
        val n = 0
        if (cursor.moveToLast()) return cursor.getInt(cursor.getColumnIndex("$miqdorSotdim"))
        return cursor.count
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    companion object {
        var dbHelper: DBHelper? = null
        fun init(context: Context) {
            if (dbHelper == null) {
                dbHelper = DBHelper(context)
            }
        }

        fun getInstance() = dbHelper!!
    }
}