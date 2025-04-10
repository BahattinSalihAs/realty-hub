document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("loginForm");

    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        fetch("/api/realty-management/admin/v1/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        })
            .then(response => {
                return alert(response.text());
            })
            .then(data => {
                console.log("login başarılı!\n" + data);
                alert("kayıt başarılı!\n" + data);
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});