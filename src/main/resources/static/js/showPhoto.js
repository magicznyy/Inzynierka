function showPhoto() {
  var chosenFile = document.getElementById("filebutton");
  var divForImage = document.getElementById("photoprevievbox");

  chosenFile.addEventListener("change", function () {
    let photo = chosenFile.files[0];
    if (photo) {
      let fileReader = new FileReader();
      fileReader.readAsDataURL(photo);
      fileReader.addEventListener("load", function () {

        let photoObject = document.querySelector("#photo");
        let img = new Image();
        img.src = this.result;
        img.id = "photo";
        if(photoObject)
          photoObject.remove();
        divForImage.appendChild(img);

        img.onload = function() {
          console.log(`w:${this.width} + h:${this.height}`);

          var tmpDiv = document.querySelectorAll(".photoInfo");  

          let w=this.naturalWidth;
          let h=this.naturalHeight;

          tmpDiv[0].innerHTML = `Rozmiar: ${(photo.size/1024/1024).toFixed(2)}MB`;
          tmpDiv[1].innerHTML = `Wymiary:${w}x${h}px`;
        }
        
        //divForImage.innerHTML = '<img src="' + this.result + '" id="photo"/>';
      });
    }

    getExif(photo);
  });
}


function getExif(varPhoto){
    EXIF.getData(varPhoto, function(){
        console.log("pobieram dane");
        console.log(EXIF.getAllTags(this));


        var allMetaData = [EXIF.getTag(this, "PixelXDimension"),
                            EXIF.getTag(this, "PixelYDimension"),
                            EXIF.getTag(this, "ExposureTime"),
                            EXIF.getTag(this, "FNumber"),
                            EXIF.getTag(this, "FocalLength"),
                            EXIF.getTag(this, "ISOSpeedRatings"),
                            EXIF.getTag(this, "Model")]
    

        var tmpDiv = document.querySelectorAll(".photoInfo");


        if(allMetaData[0] != undefined)
          tmpDiv[1].innerHTML += `Wymiary: ${allMetaData[0]}x${allMetaData[1]}px`;
        
        tmpDiv[2].innerHTML = `Ujęcie: `;
        if(allMetaData[2] != undefined && allMetaData[3] != undefined && allMetaData[4] != undefined){
          let Fnumber = allMetaData[3].numerator/allMetaData[3].denominator;
          let FocalLenght = allMetaData[4].numerator/allMetaData[4].denominator;
          tmpDiv[2].innerHTML += `${allMetaData[2].numerator}/${allMetaData[2].denominator} f/${Fnumber} ${FocalLenght}mm`;
        }

        tmpDiv[3].innerHTML = `ISO: `;
        if(allMetaData[5] != undefined )
          tmpDiv[3].innerHTML += `${allMetaData[5]}`;

        tmpDiv[4].innerHTML = `Urządzenie: `;
        if(allMetaData[6] != undefined)
          tmpDiv[4].innerHTML += `${allMetaData[6]}`;
    })
};