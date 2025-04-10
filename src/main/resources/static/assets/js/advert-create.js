document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("advertForm");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = new FormData();

        // 🔹 Address bilgisi (nested object)
        const addressEntity = {
            city: document.getElementById("city").value,
            district: document.getElementById("district").value,
            neighborhood: document.getElementById("neighborhood").value
        };

        // 🔹 Özellik checkbox’ları (List<Enum>)
        const selectedFeatures = [];
        document.querySelectorAll('input[name="features"]:checked').forEach(el => {
            selectedFeatures.push(el.value);
        });

        // 🔹 Advert JSON objesi
        const advert = {
            title: document.getElementById("title").value,
            advertPrice: document.getElementById("advertPrice").value,
            advertCurrencyCode: document.getElementById("advertCurrencyCode").value,
            addressEntity: addressEntity,
            grossArea: document.getElementById("grossArea").value,
            netArea: document.getElementById("netArea").value,
            roomType: document.getElementById("roomType").value,
            buildAge: document.getElementById("buildAge").value,
            floorNumber: document.getElementById("floorNumber").value,
            totalFloors: document.getElementById("totalFloors").value,
            heatType: document.getElementById("heatType").value,
            totalBathNumber: document.getElementById("totalBathNumber").value,
            isBalcony: document.getElementById("isBalcony").checked,
            isWithFurniture: document.getElementById("isWithFurniture").checked,
            useCase: document.getElementById("useCase").value,
            isSideInside: document.getElementById("isSideInside").checked,
            advertDescription: document.getElementById("advertDescription").value,
            features: selectedFeatures
        };

        // 🔹 JSON'ı ekle
        formData.append("advert", JSON.stringify(advert));

        // 🔹 Fotoğrafları ekle
        const files = document.getElementById("photos").files;
        for (let i = 0; i < files.length; i++) {
            formData.append("photos", files[i]);
        }

        // 🔹 Fetch isteği
        fetch("/api/realty-management/advert/v1/adverts", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("İlan gönderilemedi");
                }
                return response.text();
            })
            .then(data => {
                alert("✔️ İlan oluşturuldu:\n" + data);
                window.location.href = "/api/realty-management/realtor/systems"; // İstersen liste sayfasına yönlendir
            })
            .catch(error => {
                alert("❌ Hata: " + error.message);
            });
    });
});
