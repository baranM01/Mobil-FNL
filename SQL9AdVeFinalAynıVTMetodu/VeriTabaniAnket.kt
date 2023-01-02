package com.example.uygulamafinalsinavidemo9menusql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val VT_ADI = "anket"
private val VT_VERSIYONU = 1
private val TABLO_ADI_BILGILER = "bilgiler"

private val SIFIRINCI_KOLON_ID = "id"
private val BIRINCI_KOLON_AD = "ad"
private val IKINCI_KOLON_VIZE = "vize"
private val UCUNCU_KOLON_FINAL = "final"
private val DORDUNCU_KOLON_YAS = "yas"
private val BESINCI_KOLON_MEZUNIYET = "mezuniyet"
private val ALTINCI_KOLON_KAREKTER = "karekter"
private val YEDINCI_KOLON_BASARI_NOTU = "basari"

class VeriTabaniAnket(context: Context):SQLiteOpenHelper(context, VT_ADI,null, VT_VERSIYONU) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLO_ADI_BILGILER($SIFIRINCI_KOLON_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$BIRINCI_KOLON_AD TEXT NOT NULL, " +
                "$IKINCI_KOLON_VIZE INTEGER NOT NULL, " +
                "$UCUNCU_KOLON_FINAL INTEGER NOT NULL, " +
                "$DORDUNCU_KOLON_YAS INTEGER NOT NULL, " +
                "$BESINCI_KOLON_MEZUNIYET TEXT NOT NULL, " +
                "$ALTINCI_KOLON_KAREKTER TEXT NOT NULL, " +
                "$YEDINCI_KOLON_BASARI_NOTU INTEGER NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLO_ADI_BILGILER")
    }


    // İŞLEM METOD'LARI ->


    fun VeriEkle(ad:String, vize:Int, final:Int, yas:Int, mezuniyet:String, karakter:String, basari:Int){
        var db = this.writableDatabase
        try {
            var cv = ContentValues()
            cv.put(BIRINCI_KOLON_AD,ad)
            cv.put(IKINCI_KOLON_VIZE,vize)
            cv.put(UCUNCU_KOLON_FINAL,final)
            cv.put(DORDUNCU_KOLON_YAS,yas)
            cv.put(BESINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(ALTINCI_KOLON_KAREKTER,karakter)
            cv.put(YEDINCI_KOLON_BASARI_NOTU,basari)
            db.insert(TABLO_ADI_BILGILER,null,cv)
        }catch (e:Exception){ }
        db.close()
    }


    fun VerileriListele():List<String>{
        var db = this.readableDatabase
        var donecekVeriler = ArrayList<String>()
        try {
            var kolonlar = arrayOf(SIFIRINCI_KOLON_ID, BIRINCI_KOLON_AD, IKINCI_KOLON_VIZE,
                UCUNCU_KOLON_FINAL, DORDUNCU_KOLON_YAS, BESINCI_KOLON_MEZUNIYET,
                ALTINCI_KOLON_KAREKTER, YEDINCI_KOLON_BASARI_NOTU)
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekVeriler.add(
                    cursor.getInt(0).toString() + " - " +
                    cursor.getString(1) + " - " +
                    cursor.getInt(2).toString() + " - " +
                    cursor.getInt(3).toString() + " - " +
                    cursor.getInt(4).toString() + " - " +
                    cursor.getString(5) + " - " +
                    cursor.getString(6) + " - " +
                    cursor.getInt(7).toString()
                )
            }
        }catch (e:Exception){  }
        db.close()
        return donecekVeriler
    }

    fun VerileriGuncelle(kisiID:Int,ad: String,vize: Int,final: Int,yas: Int,mezuniyet: String,karakter: String,basari: Int){
        var db = this.writableDatabase

        try {
            var cv = ContentValues()
            cv.put(BIRINCI_KOLON_AD,ad)
            cv.put(IKINCI_KOLON_VIZE,vize)
            cv.put(UCUNCU_KOLON_FINAL,final)
            cv.put(DORDUNCU_KOLON_YAS,yas)
            cv.put(BESINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(ALTINCI_KOLON_KAREKTER,karakter)
            cv.put(YEDINCI_KOLON_BASARI_NOTU,basari)

            var belirleyiciKolon = "$SIFIRINCI_KOLON_ID=$kisiID"
            db.update(TABLO_ADI_BILGILER,cv,belirleyiciKolon,null)

        }catch (e:Exception){  }
        db.close()
    }


    fun VerileriSil(kisiID: Int){
        var db = this.writableDatabase

        try {
            var belirleyiciKolon = "$SIFIRINCI_KOLON_ID=$kisiID"

            db.delete(TABLO_ADI_BILGILER,belirleyiciKolon,null)

        }catch (e:Exception){  }
        db.close()
    }

    fun GenelBasariNotuVerileri():List<Int>{
        var db = this.readableDatabase
        var donecekBasariNotuVerileri = ArrayList<Int>()

        try {
            var kolonlar = arrayOf(YEDINCI_KOLON_BASARI_NOTU)
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekBasariNotuVerileri.add(
                    cursor.getInt(0)
                )
            }
        }catch (e:Exception){  }
        db.close()
        return donecekBasariNotuVerileri
    }

    fun genelAdVerileri():List<String>{
        var db = this.readableDatabase
        var donecekAdVerileri = ArrayList<String>()
        try {
            var kolonlar = arrayOf(BIRINCI_KOLON_AD)
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekAdVerileri.add(
                    cursor.getString(0)
                )
            }
        }catch (e:Exception){  }
        db.close()
        return donecekAdVerileri
    }


    fun GenelYasOrtalamasi():Int{
        var db = this.readableDatabase
        var donecekYasOrtalamaVerisi = 0
        try {
            var kolonlar = arrayOf("AVG("+DORDUNCU_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekYasOrtalamaVerisi = cursor.getInt(0)
            }
        }catch (e:Exception){  }
        db.close()
        return donecekYasOrtalamaVerisi
    }


    fun LiselilerinYasOrtalamasi(lise_mezunu:String):Int{
        var db = this.readableDatabase
        var donecekLiseliYasOrtalamasi = 0
        try {
            var kolonlar = arrayOf("AVG("+DORDUNCU_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,"$BESINCI_KOLON_MEZUNIYET LIKE '%$lise_mezunu%'",null,null,null,null)
            while (cursor.moveToNext()){
                donecekLiseliYasOrtalamasi = cursor.getInt(0)
            }
        }catch (e:Exception){  }
        db.close()
        return donecekLiseliYasOrtalamasi
    }



   fun EnBuyukFinalNotu():String{
       var db = this.readableDatabase
       var donecekEnBuyukFinalNotu = ""
       try {
           var kolonlar = arrayOf("$BIRINCI_KOLON_AD, MAX($UCUNCU_KOLON_FINAL)")
           var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,null,null,null,null,null)
           while (cursor.moveToNext()){
               donecekEnBuyukFinalNotu = "En Buyuk Final: " + cursor.getString(0) + " Sahibi: " + cursor.getInt(1).toString()
           }
       }catch (e:Exception){  }
       db.close()
       return donecekEnBuyukFinalNotu
   }


    fun EnKucukFinalNotu():String{
        var db = this.readableDatabase
        var donecekEnKucukFinalNotu = ""
        try {
            var kolonlar = arrayOf("$BIRINCI_KOLON_AD,MIN($UCUNCU_KOLON_FINAL)")
            var konum = "$BESINCI_KOLON_MEZUNIYET = Lise"
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,konum,null,null,null,null)
            while (cursor.moveToNext()){
                donecekEnKucukFinalNotu = "Adı : "+cursor.getString(0)+" Final Notu : "+cursor.getInt(1).toString()
            }
        }catch (e:Exception){  }
        db.close()
        return donecekEnKucukFinalNotu
    }


    fun AtaturkuSecenlerinVizeNotuOrtalamasi(secilen_Ataturk:String):Int {
        var db = this.readableDatabase
        var donecekVizeNotOrtalamasi = 0
        try {
            var kolonlar = arrayOf("AVG("+IKINCI_KOLON_VIZE+")")
            var cursor = db.query(TABLO_ADI_BILGILER,kolonlar,"$ALTINCI_KOLON_KAREKTER LIKE '%$secilen_Ataturk%'",null,null,null,null)
        }catch (e:Exception){  }
        db.close()
        return donecekVizeNotOrtalamasi
    }





}