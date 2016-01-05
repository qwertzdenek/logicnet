$(document).ready(function() {
    var messagesBox = $("#messages");

    messagesBox.hide();

    $("#post-button").click(function () {
        var inputString = $("#post-input").val();

        $.post("http://"+window.location.host+"/api/post", inputString,
            function() {
                var postInput = $('#post-input')
                var post = postInput.val();
                postInput.val("");

                messagesBox.html("<strong>You have posted a post:</strong><p>"+post+"</p>");
                messagesBox.show();
            })
    });
});
