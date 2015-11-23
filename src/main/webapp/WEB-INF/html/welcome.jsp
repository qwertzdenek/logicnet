<div class="jumbotron">
    <div class="container">
        <h1>Logic Net</h1>

        <p>Social network based on your interests without spam.</p>

        <p>
            <a class="btn btn-primary btn-lg" href="#register" role="button" data-toggle="tab">Register Now</a>
            <a class="btn btn-success btn-lg" href="login" role="button">Sign In</a>
        </p>
    </div>
</div>

<div class="container">
    <div class="tab-content">
        <div id="spotlight" class="tab-pane fade in active">

            <h1>Trending Now</h1>

            <div class="panel panel-default">
                <div class="panel-body">
                    Lorem ipsum dolor sit amet.
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-body">
                    Bootstrap is downloadable in two forms, within which
                    you'll find the following directories and files,
                    logically grouping common resources and providing both
                    compiled and minified variations.
                </div>
            </div>

        </div>

        <div id="register" class="tab-pane fade">
            <form method="post" action="register" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="nickname">Nickname</label>
                    <input type="text" class="form-control" id="nickname" placeholder="Nickname" required autofocus>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" placeholder="Password" required>
                    <input type="password" class="form-control" id="password-repeat" placeholder="Repeat" required>
                </div>

                <div class="form-group">
                    <label for="pickBirthdate">Birthdate</label>
                    <input type="date" class="form-control" id="pickBirthdate" placeholder="Birthdate">
                </div>

                <div class="form-group">
                    <label for="profilePicture">Profile picture</label>
                    <input type="file" id="profilePicture" required>

                    <p class="help-block">Upload your profile picture.</p>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" required>Agree with privacy conditions.
                    </label>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>

        </div>
    </div>

</div>
