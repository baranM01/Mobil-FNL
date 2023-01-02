package com.example.uygulamafinalsinavidemo10menusql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class ActivityDiger : AppCompatActivity() {

    lateinit var buttonListele: Button
    lateinit var ListViewDiger: ListView
    lateinit var tvSonuc: TextView

    fun init(){
        buttonListele = findViewById(R.id.buttonListele)
        ListViewDiger = findViewById(R.id.ListViewDiger)
        tvSonuc = findViewById(R.id.tvSonuc)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diger)

        init()
        var vt = VeriTabaniBilgileri(this)

        buttonListele.setOnClickListener {

            var liste2 = vt.VerileriListele()
            var ListViewTasarim = ArrayAdapter(this@ActivityDiger, android.R.layout.simple_list_item_1, liste2)
            ListViewDiger.adapter = ListViewTasarim


        }

        ListViewDiger.setOnItemClickListener { parent, view, position, id ->
            var secilen = (ListViewDiger.getItemAtPosition(position).toString()).split(" - ")
            var basari = (secilen[2].toInt()*0.4)+(secilen[3].toInt()*0.6)
            tvSonuc.text = "Basari Notu: " + basari.toString()
        }

    }
}