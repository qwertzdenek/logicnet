$(document).ready(function () {
    var messagesBox = $("#messages");

    $("#post-button").click(function () {
        var postInput = $('#post-input');
        var post = $("#post-input").val();

        $.post("http://" + window.location.host + "/api/post", post,
            function () {
                postInput.val("");

                messagesBox.append("<strong>You have posted a post:</strong>");
                messagesBox.append($("<p>").text(post).html());
                messagesBox.show("slow");
            })
    });

    $("button.like-button").click(function (e) {
        var post = this.dataset.target;
        $.ajax({
            url: "http://" + window.location.host + "/api/post/like/" + post, type: "PUT", dataType: "json",
            success: function (json) {
                $(".counter-" + post).text(json.likes);
            }
        })
    });

    $("button.hide-button").click(function (e) {
        var post = this.dataset.target;
        $.ajax({url: "http://" + window.location.host + "/api/post/hide/" + post, type: "PUT"});
    });

    $("button.friend-button").click(function (e) {
        var account = this.dataset.target;
        $.ajax({
            url: "http://" + window.location.host + "/api/account/friend/" + account, type: "POST",
            success: function () {
                messagesBox.text("You have requested friendship with " + account);
                messagesBox.show("slow");
            },
            error: function (xhr, status, errorThrown) {
                messagesBox.text("Friend action status " + status);
                messagesBox.show();
            }
        })
    });

    $("a.accept-friend").click(function (e) {
        var account = this.dataset.target;
        $.ajax({
            url: "http://" + window.location.host + "/api/account/friend/" + account, type: "PUT",
            success: function () {
                e.target.parent.hide('slow', function () {
                    e.target.parent.remove();
                });
                messagesBox.text("Friendship accepted");
                messagesBox.show("slow");
            },
            error: function (xhr, status, errorThrown) {
                messagesBox.text("Friend action status " + status);
                messagesBox.show();
            }
        });
    });
});
