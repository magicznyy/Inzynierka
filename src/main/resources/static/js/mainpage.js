//const mainBody = document.querySelector("#mainpageuserbox");

    var lastPostID=-1;
    var postDataArray; 
    
    //nie wiem czemu to działa ale działa
    //albo i nie działa
    getPosts(lastPostID);


    function getPosts(lastPostID, tryNumber=5){
        if(tryNumber===0){alert("błąd wczytywania postów"); return;};
        
        let url = `/mainPage/data/getPosts/${lastPostID}`;

        fetch(url)
        .then(response => {
            console.log(response);
            if(!response.ok){
                throw new Error(response.error);
            }
            return response.json();
        }).then(data => {
            //odebrano dane
            let mainBody = document.querySelector("#mainpageuserbox");
            let numReturnedPosts = Object.keys(data).length;
            if(numReturnedPosts===0){
                alert(` Brak kolejnych postów do wyświetlenia 
                        Spróbuj dodać nowych użytkowników do obserwowanych lub odwświerz stronę później`);
                return;
            }

            
            drawPost(data[0],0);
            let remainedPosts = numReturnedPosts-1;

            window.onscroll = async function(ev) {
                if ((window.innerHeight  + window.scrollY) >= document.body.offsetHeight) {

                    if(remainedPosts===0){
                        getPosts(data[numReturnedPosts-1].photo.photoId);
                        
                    }
                    else
                        await drawPost(data[numReturnedPosts-remainedPosts],0);
                    //document.querySelectorAll(".post").forEach(addCommentListener);

                    
                    remainedPosts--;
                }
            };

            console.log(data);
            console.log('liczba obiektów odebranych=' + Object.keys(data).length);

        }).catch(
            error => {
                console.log(error);
                setTimeout(getPosts(lastPostID, --tryNumber), 1000);
            });
    }




function parseJSONtoArray(data){
    let retObject = {};
    for( key in data){
        if(data[key] instanceof Object)
            retObject[key] = (parseJSONtoArray(data[key]));
        else{
            retObject[key] = data[key];
        }
    }
    return retObject;
}



function drawPost(postData, tryNumber){
    if(tryNumber>4){
        console.log("błąd wczytywania Zdjęć posta"); 
        return;
    };
    console.log(postData);

    let fetchUrlAvatar= `/mainPage/data/avatar/${postData.user.login}`;

    //get Avatar
    fetch(fetchUrlAvatar).then(response => {
        console.log(response);
        if(!response.ok){
            throw new Error(response.error);
        }
        return response.blob();
    }).then( avatarBlob => {
        let avatarUrl = URL.createObjectURL(avatarBlob);


        getPhoto(postData,avatarUrl,0);
        return;
        

    }).catch(error => {
        console.log(error);
        setTimeout(drawPost(postData, tryNumber+1), 1000);
    });
}

function getPhoto (postData,avatarUrl,tryNumber) {
    if(tryNumber>4){console.log("błąd wczytywania zdjęcia"); return;};
    
    let fetchUrlPhoto = `/mainPage/data/photo/${postData.photo.photoId}`;

    fetch(fetchUrlPhoto).then(response => {
        console.log(response);
        if(!response.ok){
            throw new Error(response.error);
        }
        return response.blob();
    }).then(photoBlob =>{
        let photoUrl = URL.createObjectURL(photoBlob);
        

        drawObject(postData,photoUrl,avatarUrl);

        return;


    }).catch(error => {
        console.log(error);
        setTimeout(getPhoto(postData, avatarUrl, tryNumber+1), 1000);
    });
}


function drawObject(postData,photo,avatar){
    let mainBody = document.querySelector("#mainpageuserbox");

    let postBody =`<div class="post"  id="post${postData.idPost}">
                    <div id="postheader">
                        <div id="leftheader">
                            <div id="profpicture"><img src="${avatar}" alt="zdjęcie profilowe" class="opacityonhover"></div>
                            <div id="nick" class="opacityonhover"> ${postData.user.login} </div>
                        </div>
                        <div id="rightheader">
                            <div>
                                <!--
                                <form id="followForm"   method="post">
                                    <input type="hidden" id="login" name="followedUserLogin" value="obs">
                                    <input type="submit" name="follow" value="Obserwuj" id="followbutton" class="opacityonhover"/>
                                </form>
                                -->
                            </div>
                            <div>
                                <button id="settingsbutton">
                                    <img src="/icons/MainPage/iconPostSettingsMainpage.svg" alt="post settings icon" class="opacityonhover">
                                </button>
                            </div>
                        </div>

                    </div>
                    <div id="postphoto">
                        <img  src="${photo}" alt="zdjecie nr ${postData.photo.photoId}" id="image${postData.photo.photoId}" class="opacityonhover">
                    </div>

                    <div id="postinfobox">
                        <div id="topinfobox">
                            <div id="descriptionbox">
                                ${postData.description}
                            </div>
                        </div>
                        <div id="reactionbox">

                            <div class="reactionform">
                                <form action="/addReaction"  method="post" >
                                    <label for="sendreaction${postData.idPost}">
                                        <img src="/icons/Post/iconLikePost.svg" alt="reaction icon" class="opacityonhover">
                                    </label>
                                    <input type="hidden" name="idPostReakcja" id="reaction${postData.idPost}" value="${postData.idPost}"/>
                                    <input type="submit" value="Lubie to!" name="reaction" id="sendreaction${postData.idPost}" class="reaction"  />
                                </form>
                                <div id="reactioncount">${postData.reactionsNumber}</div>
                            </div>

                            <div id="commentsform">
                                <form>
                                    <label for="showComment">
                                        <img src="/icons/Post/iconCommentPost.svg" alt="comments icon" class="opacityonhover">
                                    </label>
                                    <input  value="Wyślij" name="showComment" id="showComment" />
                                </form>
                                <div id="commentscount">${postData.commentsNumber}</div>
                            </div>
                        </div>
                    </div>
                </div>`;

    mainBody.insertAdjacentHTML("beforeend", postBody);

    let buttonCommentsObject = document.querySelector(`#post${postData.idPost} #commentsform img`);

    buttonCommentsObject.addEventListener("click", addCommentListener.bind(null,postData.idPost));
    //buttonCommentsObject.click();
    buttonCommentsObject.addEventListener("click", showAllComments.bind(null,postData));

}

function showAllComments (postData){
    console.log("pokazuje komentarze");


    let object = document.querySelector(`#post${postData.idPost}`);
    if(object.querySelector(".commentsbox") && object.querySelector("#addComment")){
        let commBox = object.querySelector(".commentsbox");
        
        postData.comments.forEach(element => {
            let date = new Date(element.data); 
            console.log("wszedłem do onclicka");
            console.log(element);
            let objectText = `<div id="'Commentnr'+${element.idComment}" class="comment">
                                     <div class="commenteravatar">
                                         <img src="${element.user.profilePicPath}" alt="zdjęcie profilowe" class="opacityonhover">
                                     </div>
                                     <div class="commentbody">
                                         <div class="commentnavbar">
                                             <div class="commenternickname">
                                                ${element.user.login}
                                             </div>
                                             <div class="commentdate" >
                                                ${date.toLocaleDateString()} ${date.toLocaleTimeString()}
                                             </div>
                                         </div>
                                         <div class="commenttext" >
                                            ${element.content}
                                         </div>
                                     </div>
                                 </div>`;
            commBox.insertAdjacentHTML("beforeend", objectText);

            let fetchUrlAvatar= `/mainPage/data/avatar/${element.user.login}`;
            fetch(fetchUrlAvatar).then(response => {
                console.log(response);
                if(!response.ok){
                    throw new Error(response.error);
                }
                return response.blob();
            }).then( avatarBlob => {
                let avatarUrl = URL.createObjectURL(avatarBlob);
                object.querySelector(`Commentnr+${element.idComment} .commenteravatar img`).src = avatarUrl;
        
            }).catch(error => {
                console.log(error);
            });
        });
    }


    
}


function addCommentListener(postID){
    console.log(postID);
    console.log("pokazuje panel komentarzy");
    //let postID = object.id.substring(4);
    

    let commentsElement = `<div class="commentsbox"></div>`;

    let form = `<form action="/addComment" method="post" id="addComment" class="comment" >
                    <label>Dodaj komentarz:</label>
                    <textarea name="comment" id="commentinput" maxlength="2000"></textarea>
                    <input type="hidden" name="idPost"  id="${postID}" value="${postID}"/>
                    <input type="submit" value="Wyślij" name="sendComment" id="sendCommentfor${postID}" id="sendComment"/>
                </form>`;

    let buttonCommentsObject = document.querySelector(`#post${postID} #commentsform img`);
    let object = document.querySelector(`#post${postID}`);

    //buttonCommentsObject.addEventListener("click", function() {
    //buttonCommentsObject.onclick = element => {
        console.log(buttonCommentsObject);
        if(!object.querySelector(".commentsbox") && !object.querySelector("#addComment")){
            object.insertAdjacentHTML("beforeend", form);
            object.insertAdjacentHTML("beforeend", commentsElement);
        }
    //})
}

function sendRight(id){
    alert(`wysłano id ${id}`);
}
