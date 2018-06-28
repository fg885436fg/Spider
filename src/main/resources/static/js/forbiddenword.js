$(document).ready(function () {
    $("#replace").click(function (e) {
        var word = $("#input").val();
        replace(word);
    })

    $("#creatForbidden").click(function (e) {
        var word = $("#forbiddenWord").val();
        creat(word);
    })

});

function creat(word) {
    var forbiddenWord = $("#forbiddenWord");
    $.ajax({
        type: 'POST',
        url: "/forbidden/creat",
        data: {
            'word': word
        },
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            forbiddenWord.val("");
            alert(data.desc);
        },
        error: function (e) {

        }
    })
}

function replace(word) {
    var output = $("#output");
    $.ajax({
        type: 'POST',
        url: "/forbidden/replace",
        data: {
            'txt': word
        },
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var text =data.data;
            output.html(text.replace("\"","&nbsp").replace("\"","&nbsp"))
        },
        error: function (e) {

        }
    })
}