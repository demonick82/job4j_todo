function switchToReg() {
    window.location.href = "/todo/registr.html"
}

function ValidateLogin() {
    const email = $('#email').val();
    const pass = $('#pass').val();
    let valid = true

    $(".invalid-feedback").remove();
    $("#email").removeClass("is-invalid");
    $("#pass").removeClass("is-invalid");
    $(".error").remove();

    if (email.length == 0) {
        $('#email').addClass("is-invalid").after('<div class="invalid-feedback">Введите email</div>');
        valid = false;
    }
    if (pass.length == 0) {
        $('#pass').addClass("is-invalid").after('<div class="invalid-feedback">Введите пароль</div>');
        valid = false;
    }
    return valid;
}

function loginUser() {
    if (ValidateLogin()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/log',
            data: '&email=' + $('#email').val()
                + '&password=' + $('#pass').val(),
            dataType: 'text'
        }).done(function (data) {
            console.log(data)
            if (data == "202 password accepted") {
                window.location.href = "/todo/index.html";
            } else if (data == "401 unauthorized") {
                $(".error").remove();
                $('#pass').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
                    'Неверный логин или пароль</div>')
            }
        }).fail(function (err) {
            alert(err);
        });
    }
}