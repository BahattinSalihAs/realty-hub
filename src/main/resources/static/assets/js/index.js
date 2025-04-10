const checkboxBuyed = document.getElementById("checkboxGroupSatilik");
const checkboxRented = document.getElementById("checkboxGroupKiralik");
const btn = document.getElementById("btn");

checkboxBuyed.classList.add("visible");
function satilikClick(){
    btn.style.left = "0";
    checkboxBuyed.classList.add("visible");
    checkboxRented.classList.remove("visible");
}
function kiralikClick(){
    btn.style.left = "145px";
    checkboxRented.classList.add("visible");
    checkboxBuyed.classList.remove("visible");
}

const ilData = {
    "denizli":{
        "ilceler":["Merkezefendi", "Pamukkale", "Acıpayam", "Babadağ", "Baklan", "Bekilli", "Beyağaç", "Bozkurt", "Buldan", "Çal", "Çameli", "Çardak", "Çivril", "Güney", "Honaz", "Kale", "Sarayköy", "Serinhisar", "Tavas"],
        "mahalleler":{
            "Merkezefendi":["1200 Evler","Adalet","Akçeşme","Akkonak","Alpaslan","Altındere","Altıntop","Aşağışamlı","Bahçelievler","Barbaros","Barutçular","Başkarcı","Bereketler","Bozburun","Çakmak","Çeltikçi","Değirmenönü","Eskihisar","Gerzele",
                "Göveçlik","Gültepe","Gümüşçay","Hacıeyüplü","Hallaçlar","Hisar","İlbade","Kadılar","Karahasanlı","Karaman","Kayalar","Kumkısık","M.Akif Ersoy","Merkezefendi","Muratdede","Salihağa","Saraylar","Saruhan","Selçukbey","Servergazi","Sevindik",
                "Sırakapılar", "Sümer", "Şemikler", "Şirinköy", "Üzerlik", "Yenimahalle", "Yenişafak", "Yenişehir", "Yeşilyayla", "Zafer"],
            "Pamukkale":["Akçapınar","Akdere","Akhan","Akköy","Aktepe","Anafartalar","Asmalıevler","Atalar","Bağbaşı","Belenardıç","Cankurtaran","Cumhuriyet","Çeşmebaşı","Deliktaş","Develi","Dokuzkavaklar","Eldenizli","Eymir","Fatih","Fesleğen","Goncalı",
                "Gökpınar","Gölemezli","Gözler","Güzelköy","Güzelpınar","Hacıkaplanlar","Haytabey","Hürriyet","Irlıganlı","İncilipınar","İstiklal","Kale","Karahayıt","Karakova","Karakurt","Karataş","Karşıyaka","Kavakbaşı","Kayıhan","Kervansaray",
                "Kınıklı","Kocadere","Korucuk","Kurtulca","Kuşpınar","Küçükdere","Mehmetçik","15 Mayıs","Pamukkale","Pelitlibağ","Pınarkent","Siteler","Tekke","Topraklık","Uzunpınar","Yeniköy","Yukarışamlı","Yunusemre","Zeytinköy","Zümrüt"],
            "Acıpayam":["Yukarı", "Aşağı", "Çamlık", "Yeni", "Gedikli", "Akalan", "Akşar", "Alaattin", "Benlik", "Bedirbey", "Boğazdere", "Corum", "Çakır", "Çiftlik", "Çubukçular", "Darıveren", "Dedebağı", "Dodurga", "Eskiköy", "Gölcük", "Gümüş", "Güney", "Hacıkurtlar",
                "Hisar", "Karahüyük", "Avşar", "Karaismailler", "Kelekçi", "Kırca", "Köke", "Kumafşarı", "Kurtlar", "Kuyucak", "Kuzören", "Mevlütler", "Oğuz", "Olukbaşı", "Ovayurt", "Ören", "Pınarbaşı", "Pınaryazı", "Sandalcık", "Sırçalık", "Suçatı",
                "Ucarı", "Yassıhüyük", "Yazır", "Yeniköy", "Yeşildere", "Yeşilyuva", "Yolçatı", "Yumrutaş"],
            "Babadağ":["Cumhuriyet", "Kelleci", "Gazi", "Mollaahmet", "Gündoğdu", "Bekirler", "Yeniköy", "Demirli", "Oğuzlar", "Kıranyer", "Ahıllı"],
            "Baklan":["Balca", "Beyelli", "Boğaziçi", "Çataloba", "Dağal", "Gökpınar", "Gürlük", "Hadim", "Hüsamettin Dede", "İcikli", "Kavaklar", "Kirazlı", "Konak", "Şenyayla"],
            "Bekilli":["Bükrüce", "Çamköy", "Çoğaşlı", "Deşdemir", "Ekizbaba", "Gömce", "Köselli", "Poyrazlı", "Sırıklı", "Üçkuyu", "Yeşiloba", "Kutlubey", "Bahçeli", "Yahyalar", "Yeni"],
            "Beyağaç":["Sazak", "Uzunoluk", "Kızılağaç", "Eşenler", "Kapuz", "Yeni", "Fatih", "Zafer", "Geriçam", "Cumhuriyet", "Çamlık", "Hürriyet", "Subaşı", "Pınarönü"],
            "Bozkurt":["Alikurt", "Armutalanı", "Avdan", "Baklankuyucak", "Barbaros", "Barçeşme", "Cumalı", "Çambaşı", "Fatih", "Hamdiye", "Hayrettinköy", "İnceler", "İncelertekkesi", "Mahmudiye", "Mecidiye", "Mehmetçik", "Mimar Sinan", "Sazköy", "Tutluca", "Yenibağlar"],
            "Buldan":["Aktaş", "Alacaoğlu", "Alandız", "Beyler", "Boğazçiftlik", "Bostanyeri", "Bozalan", "Bölmekaya", "Bursa", "Cumhuriyet", "Çamköy", "Çarşı", "Çatak", "Çaybaşı", "Derbent", "Dımbazlar", "Doğan", "4 Eylül", "Düzalan", "Girne", "Gölbaşı",
                "Gülalan", "Güroluk", "Hasanbeyler", "Helvacılar", "Kadıköy", "Karaköy", "Karşıyaka", "Kaşıkcı", "Kırandamı", "Kovanoluk", "Kurtuluş", "Kurudere", "Mahmutlu", "Oğuz", "Sarımahmutlu", "Süleymanlı", "Turan", "Türlübey", "Yalçınkaya", "Yayla",
                "Yeni", "Yenicekent", "Yeniçam", "Yeşildere"],
            "Çal":["Akkent", "Alfaklar", "Aşağıseyit", "Bahadınlar", "Baklançakırlar", "Bayıralan", "Belevi", "Çalçakırlar", "Çalkuyucak", "Dağmarmara", "Dayılar", "Denizler", "Develler", "Gelinören", "Hançalar", "Hüseyinler", "İsabey", "İsmailer",
                "Kabalar", "Kaplanlar", "Karakaya", "Karapınar", "Kocaköy", "Mahmutgazi", "Ortaköy", "Peynirciler", "Sakızcılar", "Sazak", "Selcen", "Süller", "Şapcılar", "Yazır", "Yeşilyurt", "Yukarıseyit"],
            "Çameli":["Akpınar", "Arıkaya", "Ayvacık", "Belevi", "Bıçakçı", "Cevizli", "Cumaalanı", "Çamlıbel", "Çiğdemli", "Elmalı", "Emecik", "Ericek", "Gökçekaya", "Gürsu", "Güzelyurt", "İmamlar", "Kalınkoz", "Karabayır", "Kınıkyeri", "Kızılkaya",
                "Kirazlıyayla", "Kocaova", "Kolak", "Sarıkavak", "Sofular", "Taşçılar", "Yaylapınar", "Yeni", "Yeşilyayla", "Yumrutaş", "Yunuspınarı"],
            "Çardak":["Ayvaz", "Bahçelievler", "Beylerli", "Cumhuriyet", "Çaltı", "Çınar", "Gemişli", "Gemişpınarı", "Gölcük", "Hayriye", "Hürriyet", "İstiklal", "Saray", "Söğütlü", "Söğütözü"],
            "Çivril":[
                "Akçaköy", "Akpınar", "Aktaş", "Aşağı", "Balçıkhisar", "Bayat", "Bekirli", "Belence", "Beydilli", "Bozdağ", "Bucak", "Bulgurlar", "Cabar", "Cumalar", "Çağlayan", "Çağlar", "Çakallar", "Çandır", "Çarşı", "Çatlar", "Çayır", "Çetimler",
                "Çıtak", "Düzbel", "Emircik", "Gökgöl", "Güntürsü", "Gürpınar", "Hamam", "Haydan", "Irgılı", "Işıklı", "Iğdır", "İmrali", "İnceköy", "İshaklı", "Karabedirler", "Karahacılı", "Karalar", "Karamanlı", "Karayaşıler", "Kavakalanı", "Kavakköy", "Kıralan", "Kızılcaşöğüt",
                "Kızılcayer", "Kocayaka", "Koçak", "Menteş", "Osmanköy", "Ömerli", "Özdemirci", "Reşadiye", "Saray", "Saribeyli", "Sarılar", "Savran", "Seraserli", "Somak", "Sökmen", "Stadyum", "Sundurlu", "Süngüllü", "Şehitler", "Şenköy", "Tekke", "Tokça", "Tuğlu", "Yahyalı", "Yakacık",
                "Yalınlı", "Yamanlar", "Yassıhüyük", "Yukarı", "Yukarı Çapak", "Yuvaköy"],
            "Güney":["Adıgüzeller", "Aşağıçeşme", "Aydoğdu", "Cindere", "Çamrak", "Çorbacılar", "Doğanlı", "Ertuğrul", "Eziler", "Fatih", "Hamidiye", "Haylamaz", "Karaağaçlı", "Karagözler", "Kerimler", "Koparan", "Orta", "Ortaçeşme",
                "Parmakcılar", "Tüklük", "Üç Eylül", "Yağcılar", "Yeni", "Yenikona"],
            "Honaz":["Afşin Bey", "Akbaş", "Aşağadağdere", "Aydınlar", "Cumhuriyet", "Dereçiftlik", "Emirazizli", "Gürleyik", "Haydar", "Hisar", "Hürriyet", "Kaklık", "Karaçay", "Karateke", "Kızılyer", "Kocabaş", "Menteşe", "Ovacık", "Sapaca", "Yeni",
                "Yokuşbaşı", "Yukarıdağdere"],
            "Kale":["Adamharmani", "Alanyurt", "Belenköy", "Cevherpaşa", "Cumhuriyet", "Çakırbağ", "Çamlarca", "Demirciler", "Doğanköy", "Esenkaya", "Gökköre", "Gölbaşı", "Habipler", "Hürriyet", "İnceğiz", "Karaköy", "Karayala", "Kayabaşı", "Kırkköy", "Koçarboğazı",
                "Köprübaşı", "Künar", "Musluğum", "Narlı", "Ortaköy", "Oratepe", "Özlükce", "Tokı", "Uluçam", "Yenidere", "Yeniköy"],
            "Sarayköy":["Acıdere", "Acısu", "Ada", "Ahmetli", "Altıntepe", "Aşağı", "Atatürk", "Bala", "Beylerbeyi", "Caber", "Cumhuriyet", "Ducalı", "Gerali", "Hasköy", "Hisar", "Kabağaç", "Karakıran", "Karataş", "Köprübaşı", "Kumluca",
                "Sakarya", "Sağlık", "Siğma", "Tekke", "Tepeliköy", "Tırkaz", "Tosunlar", "Trafo", "Turan", "Uyanık", "Yakakurt", "Yeşilyurt"],
            "Serinhisar":["Aşağı", "Ayaz", "Cumhuriyet", "Kaya", "Kocapınar", "Orta", "Pınarcık", "Şair Eşref", "Yatağan", "Yeniçe", "Yüreğil"],
            "Tavas":["Akıncılar", "Akyar", "Alpa", "Altınova", "Avdan", "Aydoğdu", "Baharlılar", "Bahçeköy", "Balkıca", "Çağırgan", "Çalıköy", "Çiftlikköy", "Damlacık", "Denizolugu", "Dereağzı", "Derinkuyu", "Ebecik", "Garipköy", "Gökçeler", "Gümüşdere",
                "Güzelyok", "Hırka", "Horasanlı", "Karahisar", "Kayaca", "Kayapınar", "Keçeciler", "Kızılcabölük", "Kozlar", "Medet", "Nikfer", "Orta", "Ovacık", "Pınarlar", "Pınarlık", "Samanlık", "Sarabat", "Seki", "Sofular", "Solmaz",
                "Tekke", "Ulukent", "Vakıf", "Yağışlar", "Yaka", "Yeni", "Yeşilköy", "Yorga", "Yukariboğaz"]
        }
    }
};

// İl seçimi yapıldığında ilçeleri yükleyen fonksiyon
function loadIlceler() {
    const il = document.getElementById("il").value;
    const ilceContainer = document.getElementById("ilceler-container");
    const mahalleContainer = document.getElementById("mahalleler-container");

    if (il === "il secin"){
        ilceContainer.innerHTML = `
        <label for="ilce"></label>
        <select name="ilce" id="ilce" disabled>
            <option value="" disabled selected>İlçe Seçin</option>
        </select>
        `;
        mahalleContainer.innerHTML = `
        <label for="mahalle"></label>
        <select name="mahalle" id="mahalle" disabled>
            <option value="" disabled selected>Semt/Mahalle Seçin</option>
        </select>
        `;
        return;
    }

    ilceContainer.innerHTML = ""; // Önceki ilçeleri temizle

    // İl seçildiğinde ilçeleri dropdown içinde listele
    if (il && ilData[il]) {
        const ilceSelect = document.createElement("select");
        ilceSelect.setAttribute("id", "ilce");
        ilceSelect.setAttribute("multiple", "multiple");
        ilceSelect.classList.add("select2");

        const defaultOption = document.createElement("option");
        ilceSelect.appendChild(defaultOption);

        ilData[il].ilceler.forEach(ilce => {
            const option = document.createElement("option");
            option.value = ilce;
            option.textContent = ilce;
            ilceSelect.appendChild(option);
        });

        ilceContainer.appendChild(ilceSelect);

        // Select2'yi başlatıyoruz
        $('#ilce').select2({
            placeholder: "İlçe/İlçeler Seçin"
        });
    }
    loadMahalleler();
}
// İlçe seçildiğinde mahalleleri yükleyen fonksiyon
function loadMahalleler() {
    const il = document.getElementById("il").value;
    const selectedIlce = [...document.querySelectorAll('select#ilce option:checked')].map(option => option.value);
    const mahalleContainer = document.getElementById("mahalleler-container");
    mahalleContainer.innerHTML = ""; // Önceki mahalleleri temizle

    if (!selectedIlce.length) return;

    const mahalleSelect = document.createElement("select");
    mahalleSelect.setAttribute("id", "mahalle");
    mahalleSelect.setAttribute("multiple", "multiple");
    mahalleSelect.classList.add("select2");

    selectedIlce.forEach(ilce => {
        if (il && ilData[il] && ilData[il].mahalleler[ilce]) {

            ilData[il].mahalleler[ilce].forEach(mahalle => {
                const option = document.createElement("option");
                option.value = `${ilce} - ${mahalle}`;
                option.textContent = `${ilce} - ${mahalle}`;
                mahalleSelect.appendChild(option);
            });

        }
    });
    mahalleContainer.appendChild(mahalleSelect);

    // Select2'yi başlatıyoruz
    $('#mahalle').select2({
        placeholder: "Mahalle/Mahalleler Seçin",
        width: '100%'
    });
}

document.addEventListener('click', function (e) {
    if (!e.target.closest('.select2-container')) {
        // Şu an açık olan select2'nin dropdown'ı varsa kapat
        const openDropdown = document.querySelector('.select2-container--open');

        if (openDropdown) {
            // Açık olan container’ın bağlı olduğu select’i bulalım
            const selectEl = $(openDropdown).prev('select');

            if (selectEl.length && selectEl.data('select2')) {
                selectEl.select2('close');
            }
        }
    }
});

function formatCurrency(input) {
    // Yalnızca sayıları kabul et (virgül, nokta ve 'e' harfini engelle)
    var formattedValue;
    var value = input.value.replace(/[^0-9]/g, ""); // Sadece sayıları alır

    // Sayıları 3'erli gruplara ayırarak formatla
    formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, ".");

    // Yine kullanıcı sayıları girmeyi bitirmeden hemen göstermek için
    input.value = formattedValue;
}

function validateAmount() {
    const leastAmount = document.getElementById("least-money").value.replace(/\./g, ""); // Noktaları kaldır
    const mostAmount = document.getElementById("most-money").value.replace(/\./g, ""); // Noktaları kaldır


    // Eğer "En Az" sayısı daha büyükse, kullanıcıyı uyar
    if (parseInt(mostAmount) <= parseInt(leastAmount) && mostAmount !== "") {
        alert("En çok ₺ miktarı, en az ₺ miktarından küçük veya eşit olamaz!");
        document.getElementById("most-money").value = ""; // "En Çok" kutusunu sıfırla
    }
}

function validateArea() {
    const minArea = document.getElementById("min-m2").value.replace(/\./g, ""); // Noktaları kaldır
    const maxArea = document.getElementById("max-m2").value.replace(/\./g, ""); // Noktaları kaldır

    // Eğer "En Az" sayısı daha büyükse, kullanıcıyı uyar
    if (parseInt(maxArea) <= parseInt(minArea) && maxArea !== "") {
        alert("En çok m² miktarı, en az m² miktarından küçük veya eşit olamaz!");
        document.getElementById("max-m2").value = ""; // "En Çok" kutusunu sıfırla
    }
}

// Date toggles
const checkboxes = document.querySelectorAll('.toggle-date-checkbox');

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        if (this.checked) {
            checkboxes.forEach(checkbox => {
                if (checkbox !== this) checkbox.checked = false;
            });
        }
        });
});

