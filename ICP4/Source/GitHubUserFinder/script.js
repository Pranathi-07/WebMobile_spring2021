function getGithubInfo(user,callback) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
    var xrequest = new XMLHttpRequest();
    var url = "https://api.github.com/users/"+user;
    xrequest.open("GET", url, true);
    xrequest.onload = function (e) {
      if (xrequest.readyState === 4) {
        if (xrequest.status === 200) {
          callback(xrequest)
        } else {
            callback(xrequest)
        }
      }
    };
    xrequest.send(null);
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    document.getElementById("profile").style.visibility = "visible";
    $('#profile h5.gituser-name').text(user.login)
    $('#profile img.gituser-picture').attr('src', user.avatar_url)
    $('#profile p.gituser-Id').text('Name : ' + user.name)
    $('#profile a').attr('href', user.html_url)
}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    document.getElementById("profile").style.visibility = "hidden";
    $('#error').text("There is no such username.Please enter a valid username")
}

$(document).ready(function () {
    document.getElementById("profile").style.visibility = "hidden";
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            if (username) {
                $('#error').text("")
                getGithubInfo(username, function(response) {
                    //if the response is successful show the user's details
                        if (response && response.status == 200) {
                            showUser(JSON.parse(response.responseText));
                            //else display suitable message
                        } else {
                            noSuchUser(username);
                        }
                    });
            } else {
                $('#profile').hide();
                $('#error').text("Please enter a username.")
            }
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
        }
    })
});
