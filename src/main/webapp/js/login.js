function switchToReg() {
    window.location.href = "/todo/registr.html"
}

function ValidateLogin() {
    const email = $('#email').val();
    const pass = $('#password').val();
    let valid = true

    $(".error").remove();

    if (email.length == 0) {
        $('#email').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
            'Введите Email</div>');
        valid = false;
    }
    if (pass.length == 0) {
        $('#password').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
            'Введите пароль</div>');
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
                + '&password=' + $('#password').val(),
            dataType: 'text'
        }).done(function (data) {
            console.log(data)
            if (data == "202 password accepted") {
                window.location.href = "index.html";
            } else if (data == "401 unauthorized") {
                $(".error").remove();
                $('#password').parent().after('<div class="error" style="color:#ff0000; font-weight: bold">' +
                    'Неверный логин или пароль</div>')
            }
        }).fail(function (err) {
            alert(err);
        });
    }
}