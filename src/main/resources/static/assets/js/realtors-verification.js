document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("realtorsForm");
    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("email").value;

        fetch("/api/realty-management/realtor/v1/realtors-verification", {
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
                window.location.href = "/api/realty-management/realtor/v1/realtors";
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});