document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registerForm");

    form.addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const name = document.getElementById("name").value;
        const surname = document.getElementById("surname").value;
        const age = document.getElementById("age").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const code = document.getElementById("code").value;


        fetch("/api/realty-management/admin/v1/admins", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password,
                name: name,
                surname: surname,
                age: age,
                phoneNumber: phoneNumber,
                code: code
            })
        })
            .then(response => response.text())
            .then(token => {
                alert("kayıt başarılı");
            })
            .catch(err => {
                alert("❌ Hata: " + err.message);
            });
    });
});