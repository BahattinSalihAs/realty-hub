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
                return response.text();
            })
            .then(data => {
                alert("👌 " + data);
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});