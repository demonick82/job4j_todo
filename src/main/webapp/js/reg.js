function validateReg() {
    const name = $('#username').val();
    const email = $('#email').val();
    const pass = $('#pass').val();

    let valid = true

    $(".invalid-feedback").remove();
    $("#username").removeClass("is-invalid");
    $("#email").removeClass("is-invalid");
    $("#pass").removeClass("is-invalid");

    $(".error").remove();

    if (name.length == 0) {
        $('#username').addClass("is-invalid").after('<div class="invalid-feedback">Введите имя</div>');
        valid = false;
    }
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

function addUser() {
    if (validateReg()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/register',
            data: 'userName=' + $('#username').val()
                + '&email=' + $('#email').val()
                + '&password=' + $('#pass').val(),
            dataType: 'text'
        }).done(function (data) {
            if (data == "201 user create") {
                window.location.href = "/todo/index.html";
            } else if (data == "409 email already exists") {
                $('#pass').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
                    'Пользователь уже существует </div>')
                window.location.href = "/todo/login.html";
            }
        }).fail(function (err) {
            alert(err);
        });
    }
}