function loading() {
    let check = $('#done').prop('checked');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/index?done=' + check,
        dataType: 'json'
    }).done(function (data) {
        addTable(data);
    }).fail(function (err) {
        console.log(err);
    });
}

function addCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/categories',
        dataType: 'json'
    }).done(function (data) {
        for (let category of data) {
            $('select').append(`<option value="${category.id}">${category.name}</option>`)
        }
        $('select').formSelect();
    }).fail(function (err) {
        console.log(err);
    });
}

function addDesc() {
    if (validate()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/index',
            data: 'desc=' + $('#descForm').val()
                + '&ids=' + $('#category').val(),
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
    const category = $('#category').val();
    let valid = true
    $(".error").remove();
    if (desc.length == 0) {
        $('#descForm').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
            'Введите задачу</div>');
        valid = false;
    }
    if (category == "") {
        $(".error").remove();
        $('#category').after('<div class="error" style="color:#ff0000; font-weight: bold">' +
            'Выберете хотя бы одну категорию</div>');
        valid = false
    }
    return valid;
}

function parseCategories(data) {
    let arr = [];
    let i = 0;
    for (let category of data) {
        arr[i++] = category.name
    }
    return arr.join(", ");
}

function addTable(data) {
    $('#descTable tbody').empty()
    for (let item of data) {
        $('#descTable tbody:first').append('<tr>'
             + '<td >' + item.description + '</td>'
            + '<td >' + item.user.name + '</td>'
            + '<td >' + parseCategories(item.categories) + '</td>'
            + '<td>' + item.created.day + ':' + item.created.month
            + ':' + item.created.year + '</td>'
            + '<td>'
            + ' <p>'
            + ' <label>'
            + ' <input type="checkbox" id='+item.id
            +' onclick="update(id)"/>'
            + ' <span></span>'
            + ' </label>'
            + '</p>'
            + '</td>'
            + '</tr>');
        $('#' + item.id).attr('checked', item.done);
    }
}
