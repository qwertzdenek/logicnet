$(document).ready(function() {
    var messagesBox = $("#messages");

    $("#post-button").click(function () {
        var postInput = $('#post-input');
        var post = $("#post-input").val();

        $.post("http://"+window.location.host+"/api/post", post,
            function () {
                postInput.val("");

                messagesBox.append("<strong>You have posted a post:</strong>");
                messagesBox.append($("<p>").text(post).html());
                messagesBox.show();
            })
    });

    $("button.like-button").click(function (e) {
        $.post("http://"+window.location.host+"/api/account/friend/"+ e.data, null,
            function () {
                messagesBox.text("<strong>You have requested friendship with "+ e.data+"</strong>");
                messagesBox.show();
            })
    })
});
