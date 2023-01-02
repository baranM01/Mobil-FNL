package com.example.uygulamafinalsinavidemo10menusql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val VT_ADI = "bilgiler"
private val VT_VERSIYONU = 1
private val TABLO_ADI_KGM = "kgm"

private val SIFIRINCI_KOLON_ID = "id"
private val BIRINCI_KOLON_AD = "ad"
private val IKINCI_KOLON_VIZE = "vize"
private val UCUNCU_KOLON_FINAL = "final"
private val DORDUNCU_KOLON_YAS = "yas"
private val BESINCI_KOLON_MEZUNIYET = "mezuniyet"
private val ALTINCI_KOLON_SEHIR = "sehir"
private val YEDINCI_KOLON_BASARI = "basari"

class VeriTabaniBilgileri(context: Context):SQLiteOpenHelper(context, VT_ADI,null, VT_VERSIYONU) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLO_ADI_KGM($SIFIRINCI_KOLON_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$BIRINCI_KOLON_AD TEXT NOT NULL, " +
                "$IKINCI_KOLON_VIZE INTEGER NOT NULL, " +
                "$UCUNCU_KOLON_FINAL INTEGER NOT NULL, " +
                "$DORDUNCU_KOLON_YAS INTEGER NOT NULL, " +
                "$BESINCI_KOLON_MEZUNIYET TEXT NOT NULL, " +
                "$ALTINCI_KOLON_SEHIR TEXT NOT NULL," +
                "$YEDINCI_KOLON_BASARI INTEGER NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLO_ADI_KGM")
        onCreate(db)
    }


    // Veri Tabanı Metodları ->


    fun VeriEkle(ad:String, vize:Int, final:Int, yas:Int, mezuniyet:String, sehir:String, basari:Int){
        var db = this.writableDatabase

        try {
            var cv = ContentValues()

            cv.put(BIRINCI_KOLON_AD,ad)
            cv.put(IKINCI_KOLON_VIZE,vize)
            cv.put(UCUNCU_KOLON_FINAL,final)
            cv.put(DORDUNCU_KOLON_YAS,yas)
            cv.put(BESINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(ALTINCI_KOLON_SEHIR,sehir)
            cv.put(YEDINCI_KOLON_BASARI,basari)

            db.insert(TABLO_ADI_KGM,null,cv)

        }catch (e:Exception){  }
        db.close()
    }


    fun VerileriListele():List<String>{
        var db = this.readableDatabase
        var geriyeDonecekVeriler = ArrayList<String>()

        try {
            var kolonlar = arrayOf(SIFIRINCI_KOLON_ID, BIRINCI_KOLON_AD, IKINCI_KOLON_VIZE,
                UCUNCU_KOLON_FINAL, DORDUNCU_KOLON_YAS, BESINCI_KOLON_MEZUNIYET, ALTINCI_KOLON_SEHIR)
            var cursor = db.query(TABLO_ADI_KGM,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                geriyeDonecekVeriler.add(
                    cursor.getInt(0).toString() + " - " +
                    cursor.getString(1) + " - " +
                            cursor.getInt(2).toString() + " - " +
                            cursor.getInt(3).toString() + " - " +
                            cursor.getInt(4).toString() + " - " +
                            cursor.getString(5) + " - " +
                            cursor.getString(6)
                )
            }
        }catch (e:Exception){  }
        db.close()
        return geriyeDonecekVeriler
    }


    fun VeriGuncelle(kisiId:Int, ad:String, vize:Int, final:Int, yas:Int, mezuniyet:String, sehir:String, basari: Int){
        var db = this.writableDatabase

        try {
            var cv = ContentValues()

            cv.put(BIRINCI_KOLON_AD,ad)
            cv.put(IKINCI_KOLON_VIZE,vize)
            cv.put(UCUNCU_KOLON_FINAL,final)
            cv.put(DORDUNCU_KOLON_YAS,yas)
            cv.put(BESINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(ALTINCI_KOLON_SEHIR,sehir)
            cv.put(YEDINCI_KOLON_BASARI,basari)

            var belirleyici = "$SIFIRINCI_KOLON_ID=$kisiId"
            db.update(TABLO_ADI_KGM,cv,belirleyici,null)

        }catch (e:Exception){  }
        db.close()
    }



    fun VeriSil(kisiId:Int){
        var db = this.writableDatabase
        try {
            var belirleyici = "$SIFIRINCI_KOLON_ID = $kisiId"
            db.delete(TABLO_ADI_KGM,belirleyici,null)
        }catch (e:Exception){  }
        db.close()
    }


    fun LisansMezunuYaslariToplamı(lisanYasToplam:String):Int{
        var db = this.readableDatabase
        var donecekIslemSonucu = 0
        try {
            var kolonlar = arrayOf("SUM("+DORDUNCU_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI_KGM,kolonlar,"$BESINCI_KOLON_MEZUNIYET LIKE '%$lisanYasToplam%'",null,null,null,null)
            while (cursor.moveToNext()){
                donecekIslemSonucu = cursor.getInt(0)
            }
        }catch (e:Exception){  }
        db.close()
        return donecekIslemSonucu
    }


    fun genelBasariNotuVerileri():List<Int>{
        var db = this.readableDatabase
        var donecekBasariNotVerileri = ArrayList<Int>()
        try {
            var kolonlar = arrayOf(YEDINCI_KOLON_BASARI)
            var cursor = db.query(TABLO_ADI_KGM,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekBasariNotVerileri.add(
                    cursor.getInt(0)
                )
            }
        }catch (e:Exception){  }
        db.close()
        return donecekBasariNotVerileri
    }

    fun genelAdVerileri():List<String>{
        var db = this.readableDatabase
        var donecekAdVerileri = ArrayList<String>()
        try {
            var kolonlar = arrayOf(BIRINCI_KOLON_AD)
            var cursor = db.query(TABLO_ADI_KGM,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekAdVerileri.add(
                    cursor.getString(0)
                )
            }
        }catch (e:Exception){ }
        db.close()
        return donecekAdVerileri
    }


}






