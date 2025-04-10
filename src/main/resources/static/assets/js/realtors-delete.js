document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("realtorsDeleteForm");
    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        fetch("/api/realty-management/realtor/v1/realtors/delete", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
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
                window.location.href = "/api/realty-management/admin/v1/login-success";
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});