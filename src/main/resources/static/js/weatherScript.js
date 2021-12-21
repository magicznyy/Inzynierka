window.onload = function () {
    const key = "07e09c6a0dc3a225cd5db4e457e4cf2b";
    const exluded = "current,minutely,alerts";
    let longitude = 21.017532;
    let latitude = 52.237049;
    
    var weatherUnits = ['°C','km/h','%','mm','','','','%'];

   /* "stworzenie" ikon i do nich przypisanie ścieżek*/
    var imgArray= [];
    imgArray[0]= new Image();
    imgArray[0].src='/icons/Weather_Tiles/iconThunderstormWeather.svg';
    imgArray[1]= new Image();
    imgArray[1].src='/icons/Weather_Tiles/iconDrizzleWeather.svg';

    imgArray[2]= new Image();
    imgArray[2].src='/icons/Weather_Tiles/iconRainWeather.svg';

    imgArray[3]= new Image();
    imgArray[3].src='/icons/Weather_Tiles/iconSnowWeather.svg';

    imgArray[4]= new Image();
    imgArray[4].src='/icons/Weather_Tiles/iconFogWeather.svg';

    imgArray[5]= new Image();
    imgArray[5].src='/icons/Weather_Tiles/iconCloudyWeather.svg';

    imgArray[6]= new Image();
    imgArray[6].src='/icons/Weather_Tiles/iconSunnyWeather.svg';

    for(let i=0; i<6; i++) imgArray[i].setAttribute("class", "imgTile");


    var weatherGeneralStatus = { 2:imgArray[0], 3:imgArray[1],5:imgArray[2],6:imgArray[3],7:imgArray[4],8:imgArray[5]};
    /*słownie weatherGeneralStatus = { 2:'Burza z piorunami', 3:'Mrzawka',5:'Deszcz',6:'Śnieg',7:'Zamglenia',8:'Zachmurzenie'};*/

    //stworzenie "pudełek" na dane
    const timeBubble = document.querySelector(".timeBubble");
    const timeInput = document.querySelector("#timePanelInput");
    const divsArray = document.querySelectorAll(".weatherFieldProperty");
    const tilesArray = document.querySelectorAll(".weatherTile");

    /*dodanie strzałki nad obecnym dniem*/
    var dataArray = [];

    fetch(`https://api.openweathermap.org/data/2.5/onecall?units=metric&lat=${latitude}&lon=${longitude}&appid=${key}&units=metric&lang=pl&exclude=${exluded}`, {
    	"method": "GET"
    })
    .then(response => {
    	//console.log(response);
        if(!response.ok)
            throw new Error('Network response was not OK');
        return response.json();
    })
    .catch(err => {
    	console.error('PROBLEM WITH: ',err);
    })
    .then(data  =>{
        console.log(data.daily);
    
    

        showWeatherData(data.daily[0]);

        tilesArray.forEach(element => {
            let todayWeather =  data.daily[element.id];
            
            // wyświetlenie danych na kafelkach
            let dayName = new Date (todayWeather.dt * 1000).toLocaleDateString('pl-PL', {weekday: 'long'});
            let weatherName = getWeatherSummary(todayWeather);
            console.log(weatherName);
            let weatherNameAccurate = todayWeather.weather[0].description;
            let tempMax = todayWeather.temp.max;
            let tempMin = todayWeather.temp.min;

            element.innerHTML = `<div class="dayName">${dayName.charAt(0).toUpperCase()+dayName.slice(1)}</div>`;
            element.appendChild(weatherName);
            element.innerHTML+=`<div class="weatherNameAccurate">${weatherNameAccurate}</div> <div class="tempMinMax">${tempMax}${weatherUnits[0]} | ${tempMin}${weatherUnits[0]}</div>`;
        
            //wyświetlenie dokładnych danych na dolnym panelu po kliknięciu na kafelek
            element.addEventListener('click', event => {


                $(".arrow-down").remove();
                var arrow= document.createElement("DIV");
                arrow.setAttribute("class", "arrow-down");
                element.appendChild(arrow);
                showWeatherData(todayWeather);
            })
        });
        var arrow= document.createElement("DIV");
        arrow.setAttribute("class", "arrow-down");
        tilesArray[0].appendChild(arrow);
    });
    //end of fetch

    
    

    //funkcje wspierające
    function showWeatherData (todayWeather){
        dataArray = [todayWeather.temp.max,
            todayWeather.temp.min,
            todayWeather.wind_speed,
            todayWeather.humidity,
            precipValue(todayWeather),
            getTimeFromJS(todayWeather.sunrise),
            getTimeFromJS(todayWeather.sunset),
            todayWeather.moon_phase,
            todayWeather.clouds
            ];

        //wyświetlenie temperatury
        divsArray[0].innerHTML =`${dataArray[0]}${weatherUnits[0]} | ${dataArray[1]}${weatherUnits[0]}`;
        
        //wyświetlenie reszty danych
        for(let i=2; i<9;i++){
            divsArray[i-1].innerHTML = `${dataArray[i]}${weatherUnits[i-1]}`;
        }
    }
     
    function getTimeFromJS (tempElement){
        let tempTime= new Date(tempElement *1000);
        return `${tempTime.getHours()}:${tempTime.getMinutes()}`;
    }
    
    


    function precipValue (temptodayWeather){
        let returnValue=0;

        if(temptodayWeather.hasOwnProperty('rain'))
            returnValue += temptodayWeather.rain;
        
        if(temptodayWeather.hasOwnProperty('snow'))
            returnValue += temptodayWeather.snow;

            return returnValue;
        }
    
    
        function getWeatherSummary(tempTodayWeather){
            let tempID = tempTodayWeather.weather[0].id;
            console.log('Odczyt ID='+tempID);
            let returnValue;
            if(tempID===800)
                return  imgArray[6];

            tempID = parseInt(tempID/100);
            console.log('ParseINT='+tempID);
            console.log('Odczyt z tablicy='+ weatherGeneralStatus[tempID]);
            return weatherGeneralStatus[tempID];
        }


    // Update the current slider value (each time you drag the slider handle)
    timeInput.oninput = function() {
        timeBubble.innerHTML = this.value +':00';
    }




};

