function validateReg() {
    const name = $('#username').val();
    const email = $('#email').val();
    const pass = $('#password').val();

    let valid = true
    $(".error").remove();

    if (name.length == 0) {

        $('#username').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
            'Введите имя пользователя</div>');
        valid = false;
    }
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

function addUser() {
    if (validateReg()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/register',
            data: 'userName=' + $('#username').val()
                + '&email=' + $('#email').val()
                + '&password=' + $('#password').val(),
            dataType: 'text'
        }).done(function (data) {
            if (data == "201 user create") {
                window.location.href = "index.html";
            } else if (data == "409 email already exists") {
                $('#password').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
                    'Пользователь уже существует </div>')
                window.location.href = "login.html";
            }
        }).fail(function (err) {
            alert(err);
        });
    }
}