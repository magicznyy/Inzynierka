function myFunction() {

const url = 'http://localhost:8080/test';


fetch(url)
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(data) {
        console.log(data.login);
        document.getElementById("jeden").value = data.login;
      });

})
}
