


$('document').ready(function () {

    var password = document.getElementById("password");

    var repeatpassword = document.getElementById("repeatpassword");

    function check() {

        if(password.value !== repeatpassword.value)
        { repeatpassword.setCustomValidity("Hasło nie pasuje"); }
        else
            { repeatpassword.setCustomValidity(''); }
    }
    password.onchange = check;
    repeatpassword.onkeyup = check;
});