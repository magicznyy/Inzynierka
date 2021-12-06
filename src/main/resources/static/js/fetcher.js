function fetcher() {
  fetch("/mainPage/data")
    .then((res) => {
      if (res.ok) console.log("OK");
      else console.log("Nope");
      return res.text();
    })
    .then((data) => {
      console.log(data);
      let end = JSON.parse(data);
      console.log(end);
      document.querySelector("#podmien").innerHTML = `<p>login właściciela: ${
        end.user.login}
        <br/>   
        ścieżka pliku: ${end.photo.path}</p>`;
    });
}

function JSONparser() {
  let content =
    '{"idPost":1,"tags":"#sranie #jebanie #testowanie","description":"Morze Dunaju które ja nie znaju","date":"2021-11-22T10:12:00","user":{"id":2,"login":"Tester","saldo":0.0,"aktywnosc":0,"prywatnosckonta":0,"czyzbanowany":0,"rola":0,"posts":[],"email":"test@gmail.com","hashhasla":"$2a$10$ivgE8K.IcpAkWFGhP1aVcuWDOgAFasTq0ZsOGATR3/LRaO3GSE.xW","imie":"Tak","nazwisko":"Tak","nrkontabankowego":null,"enabled":false,"username":null,"authorities":[{"authority":"USER"}],"accountNonLocked":false,"accountNonExpired":false,"credentialsNonExpired":false,"password":null},"photo":{"photoId":1,"path":"C:Users/x/IdeaProjects/Inzynierka/src/main/resources/static/images/user2/zdjecie1.png","isForSale":0}}';
  //var content = document.getElementById("xd").value;
  //console.log(content);
  let end = JSON.parse(content);
  console.log(end);
  let xd = end.idPost + 1;
  document.querySelector("#podmien").innerHTML = `<p>${xd.idPost}</p>
     <p>${end.tags} </p>`;
}

function photoFetcher() {
  fetch("/mainPage/data/0")
    .then((res) => res.blob())
    .then(imageBlob => {let url = URL.createObjectURL(imageBlob) 
    console.log(url)
    
    document.querySelector("#photoDiv").innerHTML = `<img src='${url}' alt='zdjecie użytkownika 1' style="    height: 200px; width: 100%;" />`;
    fetcher();
});
}



function fetchPosts() {
  fetch("/mainPage/data/getPosts")
    .then((res) => {
      if (res.ok) console.log("OK");
      else console.log("Nope");
      return res.text();
    })
    .then((data) => {

        console.log(data);
        let end = JSON.parse(data);
        console.log(end);
        if(end.length == 0){
          document.querySelector("#mainpageuserbox").innerHTML =`brak postów do wyświetlenia`;
          return;
        }

        let i=1;

        end.forEach(element => {
          document.querySelector("#mainpageuserbox").innerHTML += `<div id="post${i}" class="post"><p>login: ${element.user.login} </p>
          <p>opis: ${element.description} </p>
          <p>tagi: ${element.tags} </p>
          <p>ścieżka: ${element.photo.path} </p>`
        });

  });
}