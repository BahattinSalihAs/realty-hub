document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("emailForm");
    console.log("js yüklendimi")
    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        console.log(email)

        fetch("/api/realty-management/admin/v1/admins-verification", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Sunucu hatası");
                }
                console.log("response ok deyiz")
                return response.text();
            })
            .then(data => {
                alert("👌 " + data);
                console.log("hadi yönlendir")
                window.location.href = "/api/realty-management/admin/v1/admins";
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});