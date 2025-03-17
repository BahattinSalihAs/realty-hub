## Proje Tanımı ve Hedefler:
### Proje adı: Emlak Yönetim Sistemi
### Proje amacı: Kullanıcıların kiralık ve satılık emlak ilanlarını görüntüleyebileceği ve yönetebileceği bir platform sağlamak.
## Model:
### Veri Yönetimi: Kullanıcılar, ilanlar, emlak türleri (daire, villa, ofis vb.) ve fiyat bilgilerini saklayacak.
### Veritabanı: PostgreSQL veritabanı kullanılacak. Emlak ilanları, kullanıcılar ve kullanıcı favorileri gibi bilgiler burada saklanacak.
## View:
### UI Bileşenleri: Emlak ilanlarının listelendiği bir ana ekran, ilan detay sayfası, kullanıcı hesap sayfası ve arama fonksiyonu.
### Etkileşimler: Kullanıcılar arama yapabilir, ilanları favorilere ekleyebilir. Emlakçılar herbirinin kendine ait ilanlarını CRUD edebilir.
## Presenter:
### Etkileşim Yönetimi: Kullanıcılar arama yaptığında, Presenter arama parametrelerini alacak ve Model'den uygun ilanları çekecek. Bu ilanlar, View'a gösterilecek.
### Veri Akışı: Presenter, View'dan gelen kullanıcının arama verilerini alacak ve Model'e gönderecek.
## Test Stratejisi:
### Model Testleri: Veritabanı erişimi ve iş mantığının test edilmesi.
### View Testleri: UI bileşenlerinin düzgün çalışıp çalışmadığının test edilmesi.
### Presenter Testleri: Presenter, Model ve View arasındaki veri akışını yönettiği için, Presenter'ın doğru veriyi almak ve iletmek için doğru şekilde çalıştığının test edilmesi.