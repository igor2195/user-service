async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Очищаем предыдущие ошибки
    const errorDiv = document.getElementById("login-error");
    if (errorDiv) errorDiv.remove();

    try {
        const res = await fetch("/user-service/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!res.ok) {
            let errorMessage = "Ошибка авторизации";

            try {
                const errorData = await res.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                } else if (errorData.errors) {
                    errorMessage = Object.values(errorData.errors).join(", ");
                }
            } catch (e) {
                // Если ответ не JSON
                errorMessage = await res.text() || errorMessage;
            }

            showLoginError(errorMessage);
            return;
        }

        const data = await res.json();
        localStorage.setItem("token", data.token);
        window.location.href = "/user-service/";
    } catch (error) {
        showLoginError("Ошибка соединения с сервером");
    }
}

function showLoginError(message) {
    // Удаляем старую ошибку, если есть
    const oldError = document.getElementById("login-error");
    if (oldError) oldError.remove();

    // Создаем новую
    const errorDiv = document.createElement("div");
    errorDiv.id = "login-error";
    errorDiv.className = "login-error-message";
    errorDiv.innerText = message;

    const form = document.querySelector(".login-form");
    form.insertBefore(errorDiv, form.firstChild);
}