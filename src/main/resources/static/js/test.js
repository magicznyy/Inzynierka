function myFunction() {

const url = 'http://localhost:8080/checkingForNotifications';

timer = setInterval(function() {

                fetch(url)
                .then(response => response.json())
                  .then(data => {
                     if(window.sessionStorage.getItem('notifications') == null){
                        window.sessionStorage.setItem('notifications',JSON.stringify(data));
                        window.sessionStorage.setItem('notificationsCounter',0)
                     }


                     var strNotifications = window.sessionStorage.getItem('notifications');
                     var notifications = JSON.parse(strNotifications);

                     data.forEach( element => {
                         notifications.push(element);
                      } )
                     window.sessionStorage.setItem('notifications',JSON.stringify(notifications));
                  })
                  .catch(console.error);

                displayNotifications();
            }, 5000);

}


function displayNotifications(){
     var strNotifications = window.sessionStorage.getItem('notifications');
     var notifications = JSON.parse(strNotifications);
        console.log("dl listy: " + notifications.length);
         console.log("dl counter: " + window.sessionStorage.getItem('notificationsCounter'));
        if(notifications.length != window.sessionStorage.getItem('notificationsCounter')){
                document.getElementById("notification").style.filter =  "invert(82%) sepia(42%) saturate(479%) hue-rotate(298deg) brightness(99%) contrast(104%)";
                window.sessionStorage.setItem('notificationsCounter',notifications.length)
        }



      dropDownBox = document.getElementById("dropDownBox");
      dropDownBox.innerHTML = "<h1> Nowe powiadomienia </h1>";

       notifications.reverse();

      notifications.forEach( element => {
           dropDownBox.innerHTML += `<li class="row" role='presentation'> <img class="listProfPic" src = `+ element.senderUser.profilePicPath + `> <a role='menuitem' tabindex='-1' href= `+ element.contentLink + `> `+ element.senderUser.login + ' ' + element.notificationContent + ` </a></li> <li role="presentation" class="divider"></li>`;

                           } )

}

function setNotificationColor(){
        document.getElementById("notification").style.filter="invert(91%) sepia(69%) saturate(547%) hue-rotate(169deg) brightness(100%) contrast(87%)";
}
