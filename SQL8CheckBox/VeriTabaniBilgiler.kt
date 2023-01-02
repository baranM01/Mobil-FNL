package com.example.uygulamafinalsinavidemo8menusql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val VT_ADI = "bilgiler"
private val VT_VERSIYONU = 1
private val TABLO_ADI = "kisiler"
private val BIRINCI_KOLON_ID = "id"
private val IKINCI_KOLON_AD = "ad"
private val UCUNCU_KOLON_VIZE = "vize"
private val DORDUNCU_KOLON_FINAL = "final"
private val BESINCI_KOLON_YAS = "yas"
private val ALTINCI_KOLON_MEZUNIYET = "mezuniyet"
private val YEDINCI_KOLON_SEHIR = "sehir"
private val SEKIZINCI_KOLON_BASARI_NOTU = "basari"
private val DOKUZUNCU_KOLON_YAZILIM_DILLERI = "dil"



class VeriTabaniBilgiler(context: Context):SQLiteOpenHelper(context, VT_ADI,null, VT_VERSIYONU) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLO_ADI($BIRINCI_KOLON_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$IKINCI_KOLON_AD TEXT NOT NULL, " +
                "$UCUNCU_KOLON_VIZE INTEGER NOT NULL, " +
                "$DORDUNCU_KOLON_FINAL INTEGER NOT NULL, " +
                "$BESINCI_KOLON_YAS INTEGER NOT NULL, " +
                "$ALTINCI_KOLON_MEZUNIYET TEXT NOT NULL, " +
                "$YEDINCI_KOLON_SEHIR TEXT NOT NULL, " +
                "$SEKIZINCI_KOLON_BASARI_NOTU INTEGER NOT NULL, " +
                "$DOKUZUNCU_KOLON_YAZILIM_DILLERI TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLO_ADI")
    }

    // Veri Tabanı Metotları ->


    fun VeriEkle(ad:String, vize:Int, final:Int, yas:Int, mezuniyet:String, sehir:String, basari:Int, dil:String){
        var db = this.writableDatabase

        try {
            var cv = ContentValues()
            cv.put(IKINCI_KOLON_AD,ad)
            cv.put(UCUNCU_KOLON_VIZE,vize)
            cv.put(DORDUNCU_KOLON_FINAL,final)
            cv.put(BESINCI_KOLON_YAS,yas)
            cv.put(ALTINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(YEDINCI_KOLON_SEHIR,sehir)
            cv.put(SEKIZINCI_KOLON_BASARI_NOTU,basari)
            cv.put(DOKUZUNCU_KOLON_YAZILIM_DILLERI,dil)

            db.insert(TABLO_ADI,null,cv)

        }catch (e:Exception){ }
        db.close()
    }



    fun VerileriListele():List<String>{ //-> Liste'ler ArrayList 'leri kapsar.
        var db = this.readableDatabase
        var geriyeDonecekDegerler = ArrayList<String>()
        //-> Geriye döndüreceğimiz verileri oluşturduğumuz string veri tipindeki koleksiyon'a aktardık bu yüzden
        //   kolonlar dizisi'nden aktaracağımız eleman'ların veri tipi "ınteger" olanları ".toString()" metodu ile
        //   string 'e dönüştürerek bu koleksiyon'a aktarırız ve daha sonra bu koleksiyon'a aktardığımız verileri
        //   Main Activity'de kullanabilmek için koleksiyon'u "return" ile geriye döndürürüz ve Main Activity de
        //   bu verileri ListView üzerin'de yazdırmak için bu metod'u oluşturduğumuz bir nesne'ye aktarırız ve bu
        //   nesneyi'de ListView için ayarladığımız tasarım'da kullanırız.

        try {
            var kolonlar = arrayOf(BIRINCI_KOLON_ID, IKINCI_KOLON_AD, UCUNCU_KOLON_VIZE,
                DORDUNCU_KOLON_FINAL, BESINCI_KOLON_YAS, ALTINCI_KOLON_MEZUNIYET,
                YEDINCI_KOLON_SEHIR, SEKIZINCI_KOLON_BASARI_NOTU, DOKUZUNCU_KOLON_YAZILIM_DILLERI)

            var cursor = db.query(TABLO_ADI,kolonlar,null,null,null,null,null)

            while (cursor.moveToNext()){
                geriyeDonecekDegerler.add(
                    cursor.getInt(0).toString() + " - " +
                    cursor.getString(1) + " - " +
                    cursor.getInt(2).toString() + " - " +
                    cursor.getInt(3).toString() + " - " +
                    cursor.getInt(4).toString() + " - " +
                    cursor.getString(5) + " - " +
                    cursor.getString(6) + " - " +
                    cursor.getInt(7).toString() + " - " +
                    cursor.getString(8)
                )
            }

        }catch (e:Exception){  }
        db.close()
        return geriyeDonecekDegerler
    }


    fun VerileriGuncelle(kisiId:Int, ad:String, vize:Int, final:Int, yas:Int, mezuniyet:String, sehir:String, basari:Int, dil:String){
        var db = this.writableDatabase
        try {
            var cv = ContentValues()

            cv.put(IKINCI_KOLON_AD,ad)
            cv.put(UCUNCU_KOLON_VIZE,vize)
            cv.put(DORDUNCU_KOLON_FINAL,final)
            cv.put(BESINCI_KOLON_YAS,yas)
            cv.put(ALTINCI_KOLON_MEZUNIYET,mezuniyet)
            cv.put(YEDINCI_KOLON_SEHIR,sehir)
            cv.put(SEKIZINCI_KOLON_BASARI_NOTU,basari)
            cv.put(DOKUZUNCU_KOLON_YAZILIM_DILLERI,dil)

            var belirleyiciKolon = "$BIRINCI_KOLON_ID=$kisiId"

            db.update(TABLO_ADI,cv,belirleyiciKolon,null)


        }catch (e:Exception){  }
        db.close()
    }


    fun VeriSil(kisiId: Int){
        var db = this.writableDatabase

        try {
            var belirleyiciKolon = "$BIRINCI_KOLON_ID=$kisiId"
            db.delete(TABLO_ADI,belirleyiciKolon,null)

        }catch (e:Exception){  }
        db.close()
    }






    // En büyük ve en küçük yaşları ve bu yaşların KİME AİT OLDUKLARININ ADI ile ekrana yazdırmak istersek eğer;
    // burada "yas" ve "ad" kolonundaki verilerin tümünü bir diziye aktararak o diziyi metod'ta geriye döndürürüz.
    // daha sonra bun metod'ları MainActivity'de en büyük/küçük yaşlar ve aynı zamanda adları bulmak için kullanırız
    // eğer sadece en büyük/küçük yaş 'ları bulacak olsaydık o zaman burada direkt metod'u ":List<>" yerine ":Int"
    // yapardık ve "MAX/MIN" komutları ile direkt olarak bu değerleri bulur ve MainActivity'de sadece bu metod'ları
    // çağırarak bu değerleri yazdırmış olurduk ->

    fun GenelYasVerileri():List<Int>{
        var db = this.readableDatabase
        var donecekYasVerileri = ArrayList<Int>()
        try {
            var kolonlar = arrayOf(BESINCI_KOLON_YAS)
            var cursor = db.query(TABLO_ADI,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekYasVerileri.add(
                    cursor.getInt(0)
                )
            }

        }catch (e:Exception){  }
        db.close()
        return donecekYasVerileri
    } //-> Burada sayısal koleksiyon'a yas kolonundaki tüm sayısal verileri main avtivity de kıyaslatıp en büyüğünü ve küçüğünü bulmak üzere aktardık.

    fun GenelAdVerileri():List<String>{ //-> "ad" kolonun'daki tüm verileri çekeceği(döneceği) için ve 1'den fazla veri olduğu için ":List<String>" türü'nde metot oluşturduk ve bu verileri "ArrayList<String>" e aktardık.
        var db = this.readableDatabase
        var donecekAdVerileri = ArrayList<String>()

        try {
            var kolonlar = arrayOf(IKINCI_KOLON_AD)
            var cursor = db.query(TABLO_ADI,kolonlar,null,null,null,null,null)
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
        var donecekYasOrtalamasi = 0
        try {
            var kolonlar = arrayOf("AVG("+BESINCI_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekYasOrtalamasi = cursor.getInt(0)
            }
        }catch (e:Exception){}
        db.close()
        return donecekYasOrtalamasi
    }


    fun GenelBasariNotOrtalamasi():Int{ //-> Direkt tek bir integer değer hesaplayıp onu döneceği için metod'u ":Int" yaptık ve ınt veri tipi'ndeki başlangıç değeri sıfır olan "donecekBasariOrtalamasi" değişkenine aktardık.
        var db = this.readableDatabase
        var donecekBasariOrtalamasi = 0
        try {
            var kolonlar = arrayOf("AVG("+SEKIZINCI_KOLON_BASARI_NOTU+")")
            var cursor = db.query(TABLO_ADI,kolonlar,null,null,null,null,null)
            while (cursor.moveToNext()){
                donecekBasariOrtalamasi = cursor.getInt(0)
            }
        }catch (e:Exception){}

        return donecekBasariOrtalamasi
    }


    fun LisansEnBuyukBasariNotu(lisansMezunu:String):Int{
        var db = this.readableDatabase
        var donecek_Lisans_En_Buyuk_Basari_Notu = 0
        try {
            var kolonlar = arrayOf("MAX("+SEKIZINCI_KOLON_BASARI_NOTU+")")
            var cursor = db.query(TABLO_ADI,kolonlar,"$ALTINCI_KOLON_MEZUNIYET LIKE '%$lisansMezunu%'",null,null,null,null)
            while (cursor.moveToNext()){
                donecek_Lisans_En_Buyuk_Basari_Notu = cursor.getInt(0)
            }
        }catch (e:Exception){  }
        db.close()
        return donecek_Lisans_En_Buyuk_Basari_Notu
    }


    fun MasterEnKucukYas(master_Mezunu:String):Int{
        var db = this.readableDatabase
        var geriye_Donecek_Master_En_Kucuk_Yas = 0
        try {
            var kolonlar = arrayOf("MIN("+BESINCI_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI,kolonlar,"$ALTINCI_KOLON_MEZUNIYET LIKE '%$master_Mezunu%'",null,null,null,null)
            while (cursor.moveToNext()){
                geriye_Donecek_Master_En_Kucuk_Yas = cursor.getInt(0)
            }

        }catch (e:Exception){  }
        db.close()
        return geriye_Donecek_Master_En_Kucuk_Yas
    }



    fun SQLDilBilenlerinYasToplamlari(dil_SQL:String):Int{
        var db = this.readableDatabase
        var donecekSQLSecenlerinYasToplamlari = 0
        try {
            var kolonlar = arrayOf("SUM("+BESINCI_KOLON_YAS+")")
            var cursor = db.query(TABLO_ADI,kolonlar,"$DOKUZUNCU_KOLON_YAZILIM_DILLERI LIKE '%$dil_SQL%'",null,null,null,null)
            while (cursor.moveToNext()){
                donecekSQLSecenlerinYasToplamlari = cursor.getInt(0)
            }

        }catch (e:Exception){  }

        return donecekSQLSecenlerinYasToplamlari
    }



}





