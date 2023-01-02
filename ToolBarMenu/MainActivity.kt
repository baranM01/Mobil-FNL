package com.example.toolbarmenuekleme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar

// Toolbar 'a Menu Ekleme :
// Toolbar'a eklenen menü'ye "Options Menu" denir.

// HATIRLATMA :
/*
ToolBar;
  - Android sayfasının en üstünde bulunan mor bir görsel nesnedir.
  - Varsayılan olarak her sayfada gelmektedir.
  - Üzerine icon, başlık, alt başlık veya menü ekleyerek üzerinde
    değişiklikler yapabiliriz.
 */

/* Options Menü Oluşturma :

 - Menü davranışları ' app:showAsAction="" ' ile belirlenir, menu için
   oluşturduğumuz ".xml" uzantılı menu tasarımını yaptığımız dosyanın
   code veya split kısmından ilgili menu item'ın kod bloğunun içerisine
   "showAsAction" ile birlikte "showAsAction" 'daki tırnağın içerisine
   şunlardan; "ifRoom","always","never" veya "with text" tek bir tanesini
   yazarak veya bu şekilde kod yazmadan direkt ilgili menu item'ın "arttibutes" yani
   özelliklerindeki "showAsAction" kısmından bunu ilgili seçeneklerden birini seçerek ayarlayabiliriz.
 - ifRoom: Toolbar'da yer varsa görünür yer kalmaz ise ikinci önceliğe düşer
 - always: Toolbar'da ne olursa olsun yer alır
 -  never: Varsayılan olarak toolbar'da görünmez ama menü içerisinde görünür
 - with text: Aynı anda hem resim hem yazıyı göstermek için kullandığımız yapı'dır.

 Önemli Not : Options menü yani toolbar'a eklenen menü 'de, menü tasarımına eklediğimiz
              menü item 'ların üzerine resim koyabilmek önemli'dir (sınavda istenilebilir)

Options Menü uygulaması için öncelikle menü tasarımı yapılacak olan gerekli klasör ve dosyaları "res"
klasörünün altında oluştururuz ve oluşturduğumuz dosyanın içerisinde menü'nün tasarımını yaparız
(Menu item'lara "id" ve "title" verip "drawable" dosyasında "vector asset" üzerinden "icon" yani resim
oluşturup bu icon'ları uygun menu item'lara verip ardından bu menu item'ları "toolbar" a "show As Action"
kısmından ekleriz)

İstersek uygun yöntemleri ile varsayılan Toolbar 'ı silerek kendimiz projeye Toolbar görsel nesnesi
ekleriz ve ardından kodlamada "Options Menu" yapısını Toolbar üzerine ekleriz ( Yeni bir Toolbar
oluşturmadan da var olan üzerinden gitmek daha pratik olabilir)

Toolbar 'a ekleyeceğimiz her bir "Menu ıtem" için; eğer ilgili Menu ıtem'a "icon" yani resim eklediysek
Toolbar 'da ilgili Menu item, icon(eklediğimiz resim) olarak gözükür eğer resim eklemediysek, ilgili
Menu ıtem 'ın "title" kısmına yazdığımız yazı gözükür.


Kodlamada Sırası :

1- Öncelikle Toolbar 'ı aktif hale getiririz (Programa yeni Toolbar görsel nesnesi eklediysek eğer)

2- Oluşturulan Options Menu yapısı Toolbar üzerine eklenir.

3- Menu ıtem 'lar ile ilgili "click" olayları tıklandığında çalışacak olan kodlar oluşturulur.
   (Menu ıtem = Menü seçeneği)

Not : "menu" klasörü'nün içerisin'deki ".xml" uzantılı oluşturduğumuz menü'nün tasarım dosyasına
      "menuInflater" ile erişiriz, erişim için "Options Menu" 'de geçerli olan kalıp kodlama ->
      "menuInflater.inflate(R.menu.oluşturduğumuz menü tasarım dosyası'nın adı,menu)

Not : "Popup Menu" İle "Options Menu" farkları :

- PopUp Menu 'de "showAsAction" kısımına hiç dokunmayız (kullanmayız) !
- PopUp Menu'deki menu ıtem'lar bir görsel nesne'nin (buton gibi) üzerine TIKLANILINCA gözükür.
- Options Menu 'deki menu ıtem yani menü seçenekleri ekranın en üst yerinde (Toolbar) kısımında görünür olur.
- Bağlantı kodlamaları çok farklıdır.
- PopUp Menu -> Button ile, Options Menu -> Toolbar ile.
 */

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar // -> Yeni bir Toolbar oluşturursak bu şekilde tanımlamamız gerekir.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Oluşturduğumuz yeni Toolbar 'ı bağlama ve düzenleme kodları :
        // (Yeni oluşturmasaydık gerek olmazdı)

        toolbar = findViewById(R.id.toolbar)

        toolbar.title ="Toolbar Menu Yani Options Menu"
        setSupportActionBar(toolbar)


    } // "override fun onCreate" metot sonu



    // Options Menu 'yü Toolbar Üzerine Ekleme (Bağlama) Kodları :

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // -> Options Menu Bağlantısı için Kullanılan Metot.
        // Bu aralıklta kodlamalar yaparız :

        menuInflater.inflate(R.menu.toolbar_options_menu,menu) //-> Oluşturduğumuz ".xml" uzantılı menü'nün tasarımını yaptığımız dosya'ya eriştik.

        return true // return 'den sonra otomatik çıkan "super.onCreateOptionsMenu(menu)" kısmını silip yerine "true" yazarız.
    }




    // Toolbar Üzerine Eklediğimiz Menu Item 'lara Tıklanılınca İşlem Yapılmasını Sağlayan Metot Ve Kodlar :

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //-> Boolean olduğu için "when" 'deki her bir kod bloğu sonuna "return true" yazarız !
        // Bu aralıkta kodlamalar yaparız :

        when(item.itemId){

            R.id.action_bilgi -> { Toast.makeText(this@MainActivity,"'Bilgi' 'e tıkladınız Android Studio 'dasınız.",Toast.LENGTH_SHORT).show()
                                 return true }

            R.id.action_ayarlar -> { Toast.makeText(this@MainActivity,"Ayarlar sembolü' 'ne tıkladınız",Toast.LENGTH_SHORT).show()
                                    return true }

            R.id.action_cikis-> { Toast.makeText(this@MainActivity,"'çıkış' 'a tıkladınız ",Toast.LENGTH_SHORT).show()
                                 return true }

            R.id.action_ekle -> { Toast.makeText(this@MainActivity,"'Ekle''ye tıkladınız",Toast.LENGTH_SHORT).show()
                                  return true } // -> Buraya kadar olanlarda "showAsAction=always" olarak kullanıldı.

            R.id.action_yukle -> { Toast.makeText(this@MainActivity,"'Yükle''ye tıkladınız",Toast.LENGTH_SHORT).show()
                return true }

            R.id.action_guncelle -> { Toast.makeText(this@MainActivity,"'Güncelle''ye tıkladınız",Toast.LENGTH_SHORT).show()
                return true } // -> Son iki tanesinde ise "showAsAction=never" olarak kullanıldı.(Arka plan renkleri beyaz olduğu için gözükmez, değiştirilirse gözükür)

            else -> return super.onOptionsItemSelected(item)

        }
        /*
        "Options Menu" 'deki menü seçeneklerine tıklanılınca işlem yapılmasını sağlayan "override fun onOptionsItemSelected"
        metod'unu oluşturunca otomatik olarak bu noktada oluşan "return super.onOptionsItemSelected(item)" kodu'nu buradan
        silip "when" in içerisin'deki "else" 'in yanına yukarıda olduğu gibi yapıştırırız !
         */
    }

}












