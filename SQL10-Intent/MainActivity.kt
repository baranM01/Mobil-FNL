package com.example.uygulamafinalsinavidemo10menusql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var editTextAd: EditText
    lateinit var editTextVize: EditText
    lateinit var editTextFinal: EditText
    lateinit var editTextYas: EditText
    lateinit var editTextMezuniyetGuncelle: EditText
    lateinit var editTextSehirGuncelle: EditText
    lateinit var RGMezuniyet: RadioGroup
    lateinit var radioButtonLisans: RadioButton
    lateinit var radioButtonMaster: RadioButton
    lateinit var spinnerSehirler: Spinner
    lateinit var buttonMenuAc: Button
    lateinit var textViewHesaplamaSonuclari: TextView
    lateinit var listView: ListView

    var kisiID = 0
    var sayac = 0
    var spinnerSecilen = ""
    var RGSecilenMezuniyet = ""


    fun init(){
        editTextAd = findViewById(R.id.editTextAd)
        editTextVize = findViewById(R.id.editTextVize)
        editTextFinal = findViewById(R.id.editTextFinal)
        editTextYas = findViewById(R.id.editTextYas)
        editTextMezuniyetGuncelle = findViewById(R.id.editTextMezuniyetGuncelle)
        editTextSehirGuncelle = findViewById(R.id.editTextSehirGuncelle)
        RGMezuniyet = findViewById(R.id.RGMezuniyet)
        radioButtonLisans = findViewById(R.id.radioButtonLisans)
        radioButtonMaster = findViewById(R.id.radioButtonMaster)
        spinnerSehirler = findViewById(R.id.spinnerSehirler)
        buttonMenuAc = findViewById(R.id.buttonMenuAc)
        textViewHesaplamaSonuclari = findViewById(R.id.textViewHesaplamaSonuclari)
        listView = findViewById(R.id.ListView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        var vt = VeriTabaniBilgileri(this)

        buttonMenuAc.setOnClickListener {
            var popup = PopupMenu(this@MainActivity,buttonMenuAc)
            popup.menuInflater.inflate(R.menu.popup_menu_secenekleri,popup.menu)
            popup.setOnMenuItemClickListener { i ->
                when(i.itemId){

                    R.id.Item_Kaydet ->{
                        var gelenAd = editTextAd.text.toString()
                        var gelenVize = editTextVize.text.toString().toInt()
                        var gelenFinal = editTextFinal.text.toString().toInt()
                        var gelenYas = editTextYas.text.toString().toInt()
                        var hesaplananBasariNotu = (gelenVize*40/100)+(gelenFinal*60/100)
                        vt.VeriEkle(gelenAd,gelenVize,gelenFinal,gelenYas,RGSecilenMezuniyet,spinnerSecilen,hesaplananBasariNotu)
                        Toast.makeText(this@MainActivity,"Veriler Kayıt Edildi",Toast.LENGTH_SHORT).show()
                        Listele()
                        true}

                    R.id.Item_Listele ->{
                        Listele()
                        true}

                    R.id.Item_Guncelle ->{
                        var degistirilenAd = editTextAd.text.toString()
                        var degistirilenVize = editTextVize.text.toString().toInt()
                        var degistirilenFinal = editTextFinal.text.toString().toInt()
                        var degistirilenYas = editTextYas.text.toString().toInt()
                        var yenidenHesaplananBasariNotu = (degistirilenVize*40/100)+(degistirilenFinal*60/100)
                        var degistirilenMezuniyet = editTextMezuniyetGuncelle.text.toString()
                        var degistirilenSehir = editTextSehirGuncelle.text.toString()
                        vt.VeriGuncelle(kisiID,degistirilenAd,degistirilenVize,degistirilenFinal,degistirilenYas,degistirilenMezuniyet,degistirilenSehir,yenidenHesaplananBasariNotu)
                        Toast.makeText(this@MainActivity,"Veriler değiştirildi",Toast.LENGTH_SHORT).show()
                        Listele()
                        true}

                    R.id.Item_Sil ->{
                        vt.VeriSil(kisiID)
                        true}

                    R.id.Item_Hesaplamalar ->{
                        var BasariAdlari = EnBuyukVeKucukBasariNotlariVeSahiplerininAdlari()
                        textViewHesaplamaSonuclari.text = "Lisans Yaşları Toplamı = "+LisansMezunlariYasToplami()+"\n En büyük başarı notu ve sahibi = "+BasariAdlari[0]+" ve "+BasariAdlari[1]+"\n En küçük başarı notu ve sahibi = " +BasariAdlari[2]+" ve "+BasariAdlari[3]
                        true}

                    R.id.Item_Diger_Activity ->{
                        intent = Intent(this@MainActivity,ActivityDiger::class.java)
                        startActivity(intent)
                        true}


                    else -> false
                }
            }
            popup.show()
        }


       spinnerSehirler.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (sayac == 0){ sayac++ }
               else{
                   when(position){
                       0 ->{ spinnerSecilen = "Seçtiğiniz şehir : Ankara" }
                       1 ->{ spinnerSecilen = "Seçtiğiniz şehir : istanbul" }
                       2 ->{ spinnerSecilen = "Seçtiğiniz şehir : Antalya" }
                   }
               }
           }

           override fun onNothingSelected(parent: AdapterView<*>?) {
               TODO("Not yet implemented")
           }
       }


        RGMezuniyet.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.radioButtonLisans){ RGSecilenMezuniyet = "Diploma : Lisans" }
           else if (checkedId==R.id.radioButtonMaster){ RGSecilenMezuniyet = "Diploma : Master" }
        }




    } // Ana metot sonu



    fun Listele(){
        var vt = VeriTabaniBilgileri(this)
        var liste = vt.VerileriListele()
        var arrAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,liste)
        listView.adapter = arrAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            var secilenSatir = listView.getItemAtPosition(position).toString()
            var bolunenSatirVerileri = secilenSatir.split(" - ")

            kisiID = bolunenSatirVerileri[0].toInt()
            editTextAd.setText(bolunenSatirVerileri[1])
            editTextVize.setText(bolunenSatirVerileri[2])
            editTextFinal.setText(bolunenSatirVerileri[3])
            editTextYas.setText(bolunenSatirVerileri[4])
            editTextMezuniyetGuncelle.setText(bolunenSatirVerileri[5])
            editTextSehirGuncelle.setText(bolunenSatirVerileri[6])


        }
    }


    fun LisansMezunlariYasToplami():Int{
        var vt = VeriTabaniBilgileri(this)
        var lisansMezunlariYasToplami = vt.LisansMezunuYaslariToplamı("Lisans")
        return lisansMezunlariYasToplami

    }


    fun EnBuyukVeKucukBasariNotlariVeSahiplerininAdlari():Array<String>{
        var vt = VeriTabaniBilgileri(this)
        var genelBasariNotlari = vt.genelBasariNotuVerileri()
        var genelAdVerileri = vt.genelAdVerileri()

        var EnBuyukBnotu = genelBasariNotlari[0]
        var EnBuyukBnotuAdi = genelAdVerileri[0]

        var EnKucukBnotu = genelBasariNotlari[0]
        var EnKucukBnotuAdi = genelAdVerileri[0]

        for (i in 1..genelBasariNotlari.size-1){
            if(EnBuyukBnotu<genelBasariNotlari[i]){
                EnBuyukBnotu = genelBasariNotlari[i]
                EnBuyukBnotuAdi = genelAdVerileri[i]
            }
        }
        for (i in 1..genelBasariNotlari.size-1){
            if (EnKucukBnotu>genelBasariNotlari[i]){
                EnKucukBnotu = genelBasariNotlari[i]
                EnKucukBnotuAdi = genelAdVerileri[i]
            }
        }

        return arrayOf(EnBuyukBnotu.toString(),EnBuyukBnotuAdi,EnKucukBnotu.toString(),EnKucukBnotuAdi)
    }





}