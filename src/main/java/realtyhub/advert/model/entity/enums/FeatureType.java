package realtyhub.advert.model.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureType {
    CEPHE_BATI("Cephe", "Batı"),
    CEPHE_DOGU("Cephe", "Doğu"),
    CEPHE_GUNEY("Cephe", "Güney"),
    CEPHE_KUZEY("Cephe", "Kuzey"),

    // İç Özellikler
    IC_ADSL("İç Özellikler", "ADSL"),
    IC_CAMASIR_KURUTMA("İç Özellikler", "Çamaşır Kurutma Makinesi"),
    IC_YUZ_TANIMA("İç Özellikler", "Yüz Tanıma ve Parmak İzi"),
    IC_MUTFANK("İç Özellikler", "Mutfak (Ankastre)"),
    IC_CELIK_KAPI("İç Özellikler", "Çelik Kapı"),
    IC_DUSAKABIN("İç Özellikler", "Duşakabin"),
    IC_ISICAM("İç Özellikler", "Isıcam"),
    IC_PVC_DOGRAMA("İç Özellikler", "PVC Doğrama"),
    IC_BALKON("İç Özellikler", "Balkon"),

    // Dış Özellikler
    DIS_ASANSOR("Dış Özellikler", "Asansör"),
    DIS_BUHAR_ODASI("Dış Özellikler", "Buhar Odası"),
    DIS_YUZME_HAVUZU("Dış Özellikler", "Yüzme Havuzu (Kapalı)"),
    DIS_HIDROFOR("Dış Özellikler", "Hidrofor"),
    DIS_GUVENLIK("Dış Özellikler", "Güvenlik"),

    // Muhit
    MUHIT_ALISVERIS_MERKEZI("Muhit", "Alışveriş Merkezi"),
    MUHIT_UNIVERSITE("Muhit", "Üniversite"),
    MUHIT_PARK("Muhit", "Park"),
    MUHIT_DENIZE_SIFIR("Muhit", "Denize Sıfır"),
    MUHIT_MARKET("Muhit", "Market"),
    MUHIT_HASTANE("Muhit", "Hastane"),
    MUHIT_ECZANE("Muhit", "Eczane"),
    MUHIT_BELEDİYE("Muhit", "Belediye"),
    MUHIT_SEHIR_MERKEZI("Muhit", "Şehir Merkezi"),
    MUHIT_SAGLIK_OCAGI("Muhit", "Sağlık Ocağı"),

    // Ulaşım
    ULASIM_MINIBUS("Ulaşım", "Minibüs"),
    ULASIM_DOLMUS("Ulaşım", "Otobüs"),
    ULASIM_METRO("Ulaşım", "Metro"),
    ULASIM_TRAMVAY("Ulaşım", "Tramvay"),
    ULASIM_HAVAALANI("Ulaşım", "Havaalanı"),
    ULASIM_SAHIL("Ulaşım", "Sahil"),
    ULASIM_CADDE("Ulaşım", "Cadde"),

    // Manzara
    MANZARA_BOGAZ("Manzara", "Boğaz"),
    MANZARA_DENIZ("Manzara", "Deniz"),
    MANZARA_PARK("Manzara", "Park & Yeşil Alan"),
    MANZARA_GOL("Manzara", "Göl"),
    MANZARA_DOGA("Manzara", "Doğa"),

    // Konut Tipi
    KONUT_ARA_KAT("Konut Tipi", "Ara Kat"),
    KONUT_TRIBLEKS("Konut Tipi", "Tripleks"),
    KONUT_GIRIS_KATI("Konut Tipi", "Giriş Katı"),
    KONUT_BAHCELI("Konut Tipi", "Bahçeli"),
    KONUT_EN_UST_KAT("Konut Tipi", "En Üst Kat"),
    KONUT_ARA_KAT_DUBLEKS("Konut Tipi", "Ara Kat Dubleks"),

    // Engelliye Uygun
    ENGELLI_ARAC_PARK("Engelliye Uygun", "Araç Park Yeri"),
    ENGELLI_ASANSOR("Engelliye Uygun", "Asansör"),
    ENGELLI_GIRIS_RAMPA("Engelliye Uygun", "Giriş / Rampa");

    private final String category;
    private final String name;

}
