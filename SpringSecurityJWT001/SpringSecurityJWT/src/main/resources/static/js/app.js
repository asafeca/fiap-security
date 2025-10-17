document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    const tokenBox = document.getElementById("token");
    const responseBox = document.getElementById("responseBox");
    const callBtn = document.getElementById("callProtected");

    let currentToken = "";

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const resp = await fetch("/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            if (!resp.ok) {
                const txt = await resp.text();
                responseBox.innerText = `Erro ${resp.status}\n${txt}`;
                return;
            }

            const data = await resp.json();
            currentToken = data.accessToken;
            tokenBox.value = `${data.tokenType} ${data.accessToken}`;
            responseBox.innerText = `Token gerado com sucesso!\nExpira em ${data.expiresIn} ms`;
        } catch (err) {
            responseBox.innerText = `Erro na requisição: ${err}`;
        }
    });

    callBtn.addEventListener("click", async () => {
        if (!currentToken) {
            responseBox.innerText = "Nenhum token disponível. Faça login primeiro.";
            return;
        }

        try {
            const response = await fetch("/seguro/hello", {
                method: "GET",
                headers: { "Authorization": `Bearer ${currentToken}` }
            });

            if (response.ok) {
                const text = await response.text();
                responseBox.innerText = `HTTP 200\n${text}`;
            } else {
                responseBox.innerText = `Erro ${response.status}: acesso negado`;
            }
        } catch (err) {
            responseBox.innerText = `Erro na chamada: ${err}`;
        }
    });
});
