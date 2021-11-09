function loading() {
    let check = $('#done').prop('checked');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/todo?done=' + check,
        dataType: 'json'
    }).done(function (data) {
        $('#descTable tbody').empty()
        for (let desc of data) {
            $('#descTable tbody:first').append('<tr>'
                + '<td>'
                + ' <input class="form-check" type="checkbox" id=' + desc.id
                + ' onclick="update(id)">'
                + '</td>'
                + '<td >' + desc.description + '</td>'
                + '<td>' + desc.created.day + ':' + desc.created.month
                + ':' + desc.created.year + '</td>'
                + '</tr>');
            $('#' + desc.id).attr('checked', desc.done);
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function addDesc() {
    if (validate()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/todo',
            data: 'desc=' + $('#descForm').val(),
            dataType: 'text'
        }).done(function (data) {
            $('#descForm').val("");
            loading();
        }).fail(function (err) {
            alert(err);
        });
    }
}

function update(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/update',
        data: 'id=' + id,
        dataType: 'text'
    }).done(function (data) {
        loading();
    }).fail(function (err) {
        alert(err);
    });
}

function validate() {
    const desc = $('#descForm').val();
    $(".invalid-feedback").remove();
    $("#descForm").removeClass("is-invalid");
    if (desc.length == 0) {
        $('#descForm').addClass("is-invalid").after('<div class="invalid-feedback">Введите задание</div>');
        return false;
    }
    return true;
}