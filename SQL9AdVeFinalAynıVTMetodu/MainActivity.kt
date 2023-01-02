package com.example.uygulamafinalsinavidemo9menusql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextAd: EditText
    lateinit var editTextVize: EditText
    lateinit var editTextFinal: EditText
    lateinit var editTextYas: EditText
    lateinit var editTextMezuniyetGuncelle: EditText
    lateinit var editTextKarakterGuncelle: EditText
    lateinit var RGMezuniyet: RadioGroup
    lateinit var radioButtonOrtaokul: RadioButton
    lateinit var radioButtonLise: RadioButton
    lateinit var radioButtonLisans: RadioButton
    lateinit var radioButtonMaster: RadioButton
    lateinit var spinnerKarekter: Spinner
    lateinit var buttonMenuAc: Button
    lateinit var textViewHesaplamaSonuclari: TextView
    lateinit var textViewEnKucukveBuyukSadeceYaslar: TextView
    lateinit var ListView: ListView

    var kisiId = 0
    var Sayac = 0
    var spinnerSecilenKarakter = ""
    var radioGroupSecilenDiploma = ""


    fun init (){
        editTextAd = findViewById(R.id.editTextAd)
        editTextVize = findViewById(R.id.editTextVize)
        editTextFinal = findViewById(R.id.editTextFinal)
        editTextYas = findViewById(R.id.editTextYas)
        editTextMezuniyetGuncelle = findViewById(R.id.editTextMezuniyetGuncelle)
        editTextKarakterGuncelle = findViewById(R.id.editTextKarakterGuncelle)
        RGMezuniyet = findViewById(R.id.RGMezuniyet)
        radioButtonOrtaokul = findViewById(R.id.radioButtonOrtaokul)
        radioButtonLise = findViewById(R.id.radioButtonLise)
        radioButtonLisans = findViewById(R.id.radioButtonLisans)
        radioButtonMaster = findViewById(R.id.radioButtonMaster)
        spinnerKarekter = findViewById(R.id.spinnerKarekter)
        buttonMenuAc = findViewById(R.id.buttonMenuAc)
        textViewHesaplamaSonuclari = findViewById(R.id.textViewHesaplamaSonuclari)
        textViewEnKucukveBuyukSadeceYaslar = findViewById(R.id.textViewEnKucukveBuyukSadeceYaslar)
        ListView = findViewById(R.id.ListView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        var vt = VeriTabaniAnket(this)

        buttonMenuAc.setOnClickListener {
            var popup = PopupMenu(this@MainActivity,buttonMenuAc)
            popup.menuInflater.inflate(R.menu.popup_menu_secenekleri,popup.menu)
            popup.setOnMenuItemClickListener { i ->
                when (i.itemId){
                    R.id.Item_Kayit -> {
                        var gelenAd = editTextAd.text.toString()
                        var gelenVize = editTextVize.text.toString().toInt()
                        var gelenFinal = editTextFinal.text.toString().toInt()
                        var gelenYas = editTextYas.text.toString().toInt()
                        var hesaplananBasari = (gelenVize*40/100)+(gelenFinal*60/100).toInt()
                        vt.VeriEkle(gelenAd,gelenVize,gelenFinal,gelenYas,radioGroupSecilenDiploma,spinnerSecilenKarakter,hesaplananBasari)
                        Toast.makeText(this@MainActivity,"Veri Kayıt edildi",Toast.LENGTH_SHORT).show()
                        Listeleme()
                        YazilariTemizle()
                        true}

                    R.id.Item_Listele -> {
                        Listeleme()
                        YazilariTemizle()
                        true}

                    R.id.Item_Guncelle -> {
                        var degisenAd = editTextAd.text.toString()
                        var degisenVize = editTextVize.text.toString().toInt()
                        var degisenFinal = editTextFinal.text.toString().toInt()
                        var degisenYas = editTextYas.text.toString().toInt()
                        var degisenBasari = (degisenVize*40/100)+(degisenFinal*60/100)
                        var degisenKarakter = editTextKarakterGuncelle.text.toString()
                        var degisenMezuniyet = editTextMezuniyetGuncelle.text.toString()
                        vt.VerileriGuncelle(kisiId, degisenAd,degisenVize,degisenFinal,degisenYas,degisenMezuniyet,degisenKarakter,degisenBasari)
                        Toast.makeText(this@MainActivity,"Veri Değiştirildi",Toast.LENGTH_SHORT).show()
                       YazilariTemizle()
                        true}

                    R.id.Item_Sil -> {
                        vt.VerileriSil(kisiId)
                        YazilariTemizle()
                        true}

                    R.id.Item_Hesapla -> {
                        var basariNotlariVeAdlar = EnBuyukVeKucukBasariNotlariVeSahipleri()
                        textViewHesaplamaSonuclari.text = "En büyük başarı notu ve sahibi :"+basariNotlariVeAdlar[0]+" "+basariNotlariVeAdlar[1]+"\n En küçük başarı notu ve sahibi :"+basariNotlariVeAdlar[2]+" "+basariNotlariVeAdlar[3]+"\n Genel Yaş Ortalaması ="+vt.GenelYasOrtalamasi()+"\n Liselilerin Yaş Ortalaması = "+LiselilerinYasOrtalamasi()+"\n En büyük ve küçük final notu = "+vt.EnBuyukFinalNotu() +" "+vt.EnKucukFinalNotu()+"\n Atatürk'ü seçenlerin vize ortalaması = "+AtaturkSecenlerinVizeOrtalamasi()
                        IsimsizOlarakBulunanEnBuyukVeKucukBasariNotlari()
                        true}

                    else -> false
                }
            }
            popup.show()
        }




        spinnerKarekter.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
             if(Sayac==0){ Sayac++ }
                else{
                    when(position){
                        0 -> { spinnerSecilenKarakter = "Seçilen karekter : Adolf Hitler" }
                        1 -> { spinnerSecilenKarakter = "Seçilen karekter : Ataturk" }
                        2 -> { spinnerSecilenKarakter = "Seçilen karekter : Fatih Sultan Mehmed" }
                        3 -> { spinnerSecilenKarakter = "Seçilen karekter : Kanuni Sultan Suleyman" }
                        4 -> { spinnerSecilenKarakter = "Seçilen karekter : Fidel Castro" }
                        5 -> { spinnerSecilenKarakter = "Seçilen karekter : Karl Marx" }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        RGMezuniyet.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.radioButtonOrtaokul){ radioGroupSecilenDiploma = "Mezuniyet : Orta Okul" }
            else if(checkedId==R.id.radioButtonLise){ radioGroupSecilenDiploma = "Mezuniyet : Lise" }
            else if(checkedId==R.id.radioButtonLisans){ radioGroupSecilenDiploma = "Mezuniyet : Lisans" }
            else if(checkedId==R.id.radioButtonMaster){ radioGroupSecilenDiploma = "Mezuniyet : Master" }
        }

    }// ana metod sonu


    fun Listeleme(){
        var vt = VeriTabaniAnket(this)
        var liste = vt.VerileriListele()
        var arrAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,liste)
        ListView.adapter = arrAdapter

        ListView.setOnItemClickListener { parent, view, position, id ->
            var secilenSatir = ListView.getItemAtPosition(position).toString()
            var bolunecekVeriler = secilenSatir.split(" - ")

            kisiId = bolunecekVeriler[0].toInt()
            editTextAd.setText(bolunecekVeriler[1])
            editTextVize.setText(bolunecekVeriler[2])
            editTextFinal.setText(bolunecekVeriler[3])
            editTextYas.setText(bolunecekVeriler[4])
            editTextMezuniyetGuncelle.setText(bolunecekVeriler[5])
            editTextKarakterGuncelle.setText(bolunecekVeriler[6])

        }
    }


    fun EnBuyukVeKucukBasariNotlariVeSahipleri():Array<String>{
        var vt = VeriTabaniAnket(this)
        var genelBasariNotlari = vt.GenelBasariNotuVerileri()
        var genelAdVerileri = vt.genelAdVerileri()

        var EnBBasariNotu = genelBasariNotlari[0]
        var EnBBasariad = genelAdVerileri[0]

        var EnKBasariNotu = genelBasariNotlari[0]
        var EnKucukBasariad = genelAdVerileri[0]

        for (i in 1..genelBasariNotlari.size-1){ //-> döngüyü 1'den başlattık çünkü zaten sıfır'a eşitlemiştik tekrar sıfırıncı elemanı kontrol etmesine gerek yoktur.
            if(EnBBasariNotu<genelBasariNotlari[i]){
                EnBBasariNotu = genelBasariNotlari[i]
                EnBBasariad = genelAdVerileri[i]
            }
        }
        for (i in 1..genelBasariNotlari.size-1){
            if(EnKBasariNotu>genelBasariNotlari[i]){
                EnKBasariNotu = genelBasariNotlari[i]
                EnKucukBasariad = genelAdVerileri[i]
            }
        }
        return arrayOf(EnBBasariNotu.toString(),EnBBasariad,EnKBasariNotu.toString(),EnKucukBasariad)
    }


    fun IsimsizOlarakBulunanEnBuyukVeKucukBasariNotlari(){ // -> isimsiz olarak en büyük ve küçük değerleri eski ve uzun yöntem ile bu şekilde yazdırabiliriz ya da "MAX()" ve "MIN()" leri kullanarak veri tabanında bu değerleri çok daha kısa bir şekilde bulup direkt o metot'ları çağırabiliriz.
        var vt = VeriTabaniAnket(this)
        var genelBasariNotuVerileri = vt.GenelBasariNotuVerileri()

        var enBuyukBasariNotu = genelBasariNotuVerileri[0]
        var enKucukBasariNotu = genelBasariNotuVerileri[0]

        for (i in 1..genelBasariNotuVerileri.size-1){
            if (enBuyukBasariNotu<genelBasariNotuVerileri[i]){
                enBuyukBasariNotu = genelBasariNotuVerileri[i]
            }
        }
        for (i in 1..genelBasariNotuVerileri.size-1){
            if (enKucukBasariNotu>genelBasariNotuVerileri[i]){
                enKucukBasariNotu = genelBasariNotuVerileri[i]
            }
        }
        textViewEnKucukveBuyukSadeceYaslar.text = "En büyük ve En küçük sadece başarı notları = $enBuyukBasariNotu ve $enKucukBasariNotu"
    }





    fun LiselilerinYasOrtalamasi():Int{
        var vt = VeriTabaniAnket(this)
        var liselilerinyasOrtalamasi = vt.LiselilerinYasOrtalamasi("Lise")
        return liselilerinyasOrtalamasi
    }



    fun AtaturkSecenlerinVizeOrtalamasi():Int{
        var vt = VeriTabaniAnket(this)
        var AtaturkSecenlerinVizeOrtalamasi = vt.AtaturkuSecenlerinVizeNotuOrtalamasi("Ataturk")
        return AtaturkSecenlerinVizeOrtalamasi
    }




    fun YazilariTemizle(){
        editTextAd.text.clear()
        editTextVize.text.clear()
        editTextFinal.text.clear()
        editTextYas.text.clear()
        editTextMezuniyetGuncelle.text.clear()
        editTextKarakterGuncelle.text.clear()
    }

}