package com.example.uygulamafinalsinavidemo8menusql

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
    lateinit var editTextSehirGuncelle: EditText
    lateinit var editTextDilGuncelle: EditText
    lateinit var RGMezuniyet: RadioGroup
    lateinit var radioButtonOrtaokul: RadioButton
    lateinit var radioButtonLise: RadioButton
    lateinit var radioButtonLisans: RadioButton
    lateinit var radioButtonMaster: RadioButton
    lateinit var checkBoxJava: CheckBox
    lateinit var checkBoxKotlin: CheckBox
    lateinit var checkBoxPhyton: CheckBox
    lateinit var checkBoxSQL: CheckBox
    lateinit var spinnerSehir: Spinner
    lateinit var buttonMenuAc: Button
    lateinit var textViewHesaplamaSonuclari: TextView
    lateinit var ListView: ListView

    var kisiId = 0
    var spinnerSecilenSehir = ""
    var sayac = 0
    var radioGroupSecilenMezuniyet = ""
    var CBJava = false; var CBKotlin = false; var CBPhyton = false; var CBSQL = false;
    var checkBoxKayitOlacakDeger = ""

    fun init (){
        editTextAd = findViewById(R.id.editTextAd)
        editTextVize = findViewById(R.id.editTextVize)
        editTextFinal = findViewById(R.id.editTextFinal)
        editTextYas = findViewById(R.id.editTextYas)
        editTextMezuniyetGuncelle = findViewById(R.id.editTextMezuniyetGuncelle)
        editTextSehirGuncelle = findViewById(R.id.editTextSehirGuncelle)
        editTextDilGuncelle = findViewById(R.id.editTextDilGuncelle)
        RGMezuniyet = findViewById(R.id.RGMezuniyet)
        radioButtonOrtaokul = findViewById(R.id.radioButtonOrtaokul)
        radioButtonLise = findViewById(R.id.radioButtonLise)
        radioButtonLisans = findViewById(R.id.radioButtonLisans)
        radioButtonMaster = findViewById(R.id.radioButtonMaster)
        checkBoxJava = findViewById(R.id.checkBoxJava)
        checkBoxKotlin = findViewById(R.id.checkBoxKotlin)
        checkBoxPhyton = findViewById(R.id.checkBoxPhyton)
        checkBoxSQL = findViewById(R.id.checkBoxSQL)
        spinnerSehir = findViewById(R.id.spinnerSehir)
        buttonMenuAc = findViewById(R.id.buttonMenuAc)
        textViewHesaplamaSonuclari = findViewById(R.id.textViewHesaplamaSonuclari)
        ListView = findViewById(R.id.ListView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        var vt = VeriTabaniBilgiler(this)

        buttonMenuAc.setOnClickListener {
            var popup = PopupMenu(this@MainActivity,buttonMenuAc)
            popup.menuInflater.inflate(R.menu.popup_menu_secenekler,popup.menu)
            popup.setOnMenuItemClickListener { i ->
                when(i.itemId){

                    R.id.Item_Kaydet ->{
                        if(CBJava){ checkBoxKayitOlacakDeger = "Java" }
                        if(CBKotlin){ checkBoxKayitOlacakDeger = "Kotlin" }
                        if(CBPhyton){ checkBoxKayitOlacakDeger = "Phyton" }
                        if(CBSQL){ checkBoxKayitOlacakDeger = "SQL" }
                        if(CBJava&&CBKotlin&&CBPhyton&&CBSQL){ checkBoxKayitOlacakDeger = "Java,Kotlin,Phyton,SQL" }
                        var gelenAd = editTextAd.text.toString()
                        var gelenVize = editTextVize.text.toString().toInt()
                        var gelenFinal = editTextFinal.text.toString().toInt()
                        var gelenYas = editTextYas.text.toString().toInt()
                        var basariNotu = (gelenVize*40/100)+(gelenFinal*60/100)
                        vt.VeriEkle(gelenAd,gelenVize,gelenFinal,gelenYas,radioGroupSecilenMezuniyet,spinnerSecilenSehir,basariNotu,checkBoxKayitOlacakDeger)
                        Toast.makeText(this@MainActivity,"Veriler Kayıt Edildi",Toast.LENGTH_SHORT).show()
                        Listeleme()
                        YaziTemizle()
                        true}

                    R.id.Item_Listele ->{
                        Listeleme()
                        YaziTemizle()
                        true}

                    R.id.Item_Guncelle ->{
                        var degistirilenAd = editTextAd.text.toString()
                        var degistirilenVize = editTextVize.text.toString().toInt()
                        var degistirilenFinal = editTextFinal.text.toString().toInt()
                        var degistirilenYas = editTextYas.text.toString().toInt()
                        var degisenbasariNotu = (degistirilenVize*40/100)+(degistirilenFinal*60/100)
                        var degistirilenMezuniyet = editTextMezuniyetGuncelle.text.toString()
                        var degistirilenSehir = editTextSehirGuncelle.text.toString()
                        var degistirilenDil = editTextDilGuncelle.text.toString()
                        vt.VerileriGuncelle(kisiId,degistirilenAd,degistirilenVize,degistirilenFinal,degistirilenYas,degistirilenMezuniyet,degistirilenSehir,degisenbasariNotu,degistirilenDil)
                        Toast.makeText(this@MainActivity,"Veriler Değiştirildi",Toast.LENGTH_SHORT).show()
                        Listeleme()
                        YaziTemizle()
                        true}

                    R.id.Item_Sil ->{
                        vt.VeriSil(kisiId)
                        Listeleme()
                        true}

                    R.id.Item_Hesaplamalar ->{
                        var EnbveEnkYas = EnBuyukveKucukYasVeAdi()
                        textViewHesaplamaSonuclari.text = "En Büyük Yaş ve Sahibi = "+EnbveEnkYas[0]+" "+EnbveEnkYas[1] +"\n En Küçük Yaş ve Sahibi = "+EnbveEnkYas[2]+" "+EnbveEnkYas[3]+"\n Genel yaş ortalaması = "+vt.GenelYasOrtalamasi()+"\n Genel başarı not ortalaması = "+vt.GenelBasariNotOrtalamasi()+"\n Lisans mezunu en büyük başarı notu = "+EnBuyukLisansBasariNotu()+"\n Master mezunu en küçük yaş = "+EnKucukMasterliYasi()+"\n SQL bilenlerin yaşları toplamı = "+SQLDilSecenlerinYasToplami()
                        true}

                    else -> false
                }
            }
            popup.show()
        }



        // Spinner Ayarlamaları ->

        spinnerSehir.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (sayac==0){ sayac++ }
               else{
                   when(position){
                       0 ->{ spinnerSecilenSehir = " Yaşadığınız Şehir : Kars "}
                       1 ->{ spinnerSecilenSehir = " Yaşadığınız Şehir : Mardin "}
                       2 ->{ spinnerSecilenSehir = " Yaşadığınız Şehir : Antalya "}
                   }
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        // radioGroup Ayarlamaları ->

        RGMezuniyet.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.radioButtonOrtaokul){  radioGroupSecilenMezuniyet = " Mezuniyet : Orta Okul "  }
            else if (checkedId==R.id.radioButtonLise){  radioGroupSecilenMezuniyet = " Mezuniyet : Lise "  }
            else if (checkedId==R.id.radioButtonLisans){  radioGroupSecilenMezuniyet = " Mezuniyet : Lisans "  }
            else if (checkedId==R.id.radioButtonMaster){  radioGroupSecilenMezuniyet = " Mezuniyet : Master "  }
        }



        // CheckBox Ayarlamaları ->

        checkBoxJava.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){ CBJava = true }
        }
        checkBoxKotlin.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){ CBKotlin = true }
        }
        checkBoxPhyton.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){ CBPhyton = true }
        }
        checkBoxSQL.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){ CBSQL = true }
        }



    }// Ana metod sonu


    fun Listeleme(){
        var vt = VeriTabaniBilgiler(this)
        var liste = vt.VerileriListele()
        var arrAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,liste)
        ListView.adapter = arrAdapter

        ListView.setOnItemClickListener { parent, view, position, id ->
            var secilenSatir = ListView.getItemAtPosition(position).toString()
            var bolunenVeriler = secilenSatir.split(" - ")

            kisiId = bolunenVeriler[0].toInt()
            editTextAd.setText(bolunenVeriler[1])
            editTextVize.setText(bolunenVeriler[2])
            editTextFinal.setText(bolunenVeriler[3])
            editTextYas.setText(bolunenVeriler[4])
            editTextMezuniyetGuncelle.setText(bolunenVeriler[5])
            editTextSehirGuncelle.setText(bolunenVeriler[6])
            editTextDilGuncelle.setText(bolunenVeriler[8])

        }
    }



   fun EnBuyukveKucukYasVeAdi():Array<String>{
       var vt = VeriTabaniBilgiler(this)
       var genelYasVerileri = vt.GenelYasVerileri()
       var genelAdVerileri = vt.GenelAdVerileri()

       var EnbYas = genelYasVerileri[0]
       var EnbYasAdi = genelAdVerileri[0]

       var EnkYas = genelYasVerileri[0]
       var EnkYasAdi = genelAdVerileri[0]

       for (i in 0..genelYasVerileri.size-1){
           if(EnbYas<genelYasVerileri[i]){
               EnbYas = genelYasVerileri[i]
               EnbYasAdi = genelAdVerileri[i]
           }
       }
       for (i in 0..genelYasVerileri.size-1){
           if(EnkYas>genelYasVerileri[i]){
               EnkYas = genelYasVerileri[i]
               EnkYasAdi = genelAdVerileri[i]
           }
       }
       var donecekDegerler = arrayOf(EnbYas.toString(),EnbYasAdi,EnkYas.toString(),EnkYasAdi)
       return donecekDegerler // Doğru Yöntem İle Çalışıyor mu Kontrol Et ! -> Evet Çalışıyor !
   }


    fun EnBuyukLisansBasariNotu():Int{
        var vt = VeriTabaniBilgiler(this)
        var EnBuyukLisansBasarisi = vt.LisansEnBuyukBasariNotu("Lisans")
        return EnBuyukLisansBasarisi
    }


    fun EnKucukMasterliYasi():Int{
        var vt = VeriTabaniBilgiler(this)
        var MasterlininEnKucukYasi = vt.MasterEnKucukYas("Master")
        return MasterlininEnKucukYasi
    }



    fun SQLDilSecenlerinYasToplami():Int{
        var vt = VeriTabaniBilgiler(this)
        var gelenSonuc = vt.SQLDilBilenlerinYasToplamlari("SQL")
        return gelenSonuc
    }








    fun YaziTemizle(){
        editTextAd.text.clear()
        editTextVize.text.clear()
        editTextFinal.text.clear()
        editTextYas.text.clear()
        editTextMezuniyetGuncelle.text.clear()
        editTextSehirGuncelle.text.clear()
        editTextDilGuncelle.text.clear()
    }

}