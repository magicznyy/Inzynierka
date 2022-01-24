window.onload = function () {
    
    
    const weatherUnits = ['°C','km/h','%','mm','','','','%'];
    const weatherNames = ['Temperatura','Wiatr','Wilgotność','Opady','Wschód','Zachód','Faza księżyca','Zachmurzenie'];
    //const weatherGeneralStatus = { 2:'Burza z piorunami', 3:'Mrzawka',5:'Deszcz',6:'Śnieg',7:'Zamglenia',8:'Zachmurzenie'};
    

    //stworzenie "pudełek" na dane
    const timeBubble = document.querySelector(".timeBubble");
    const timeInput = document.querySelector("#timePanelInput");
    const divsArray = document.querySelectorAll(".weatherFieldProperty");
    const tilesArray = document.querySelectorAll(".weatherTile");
    const rangeInput = document.querySelector("#timePanelInput");
    

    const preferenceBoxesArray = document.querySelectorAll(".preferenceBox");
    
    var currentTile=0;
    
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


    function getUserData(retryNum=0) {
        if(retryNum>4){
            alert("Błąd pobierania danych. Proszę spróbować później"); 
            return false;
        };

        fetch(`/sendWeatherPreferences`).then(response => {
            if(!response.ok)
                throw new Error('Network response was not OK');
            return response.json();

        }).catch(error => {
            console.log(error);
            setTimeout(getUserData(++retryNum), 1000);

        }).then(data => {
            console.log(data);
            var weatherPreferences = [0,0,0,0];
            tempData = data.preferences;

            let i=0;
            for(let tmpkey in tempData){
                weatherPreferences[i] = tempData[tmpkey];
                preferenceBoxesArray[i].querySelector(".preferenceInput").value = tempData[tmpkey];
                preferenceBoxesArray[i++].querySelector(".showValue").value = tempData[tmpkey];
            }

            useWeather(weatherPreferences,data,0);
        });
        //pobrano dane i można rysować resztę strony
        return true;
    }

    //przerywa działanie strony jeśli nie udało się wczytać danych
    if(!getUserData(0))
        return;


    function useWeather(weatherPreferences, dataPref, retryNum=0){
        if(retryNum>4){alert("Błąd w pobieraniu prognozy pogody"); return false;};
        
        const exluded = "current,minutely,alerts";  

        let tempData = dataPref.userDetails;
        
        const key = tempData.key;
        let longitude = tempData.longitude;
        let latitude = tempData.latitude;
        
        fetch(`https://api.openweathermap.org/data/2.5/onecall?units=metric&lat=${latitude}&lon=${longitude}&appid=${key}&lang=pl&exclude=${exluded}`, {
        	"method": "GET"
        })
        .then(response => {

            if(!response.ok)
                throw new Error('Network response was not OK');
            return response.json();
            
        })
        .catch(err => {
            //sprawdzenie czy nie skończyły się API calls na pogodę
            if(response.status != 429){
                console.error('PROBLEM WITH: ',err);
                setTimeout(useWeather(dataPref,++retryNum), 1000);
            }else{
                alert("Błąd w pobieraniu prognozy pogody. Proszę spróbować za kilka minut");
            }
        })
        .then(data  =>{
            var weatherPreferencesGrade = [0,0,0,0,0,0,0];

            //wypisanie danych z dzisiaj po załadowaniu strony
            showWeatherData(data.daily[0]);

            tilesArray.forEach(element => {
                let todayWeather =  data.daily[element.id];


                // wyświetlenie danych na kafelkach
                let dayName = new Date (todayWeather.dt * 1000).toLocaleDateString('pl-PL', {weekday: 'long'});
                let weatherIcon = getWeatherSummary(todayWeather);
                let weatherNameAccurate = todayWeather.weather[0].description;
                let tempMax = todayWeather.temp.max;
                let tempMin = todayWeather.temp.min;

                calculateWeatherGrade (element, todayWeather, weatherPreferences, weatherPreferencesGrade);

                element.innerHTML = `<div class="dayName">${dayName.charAt(0).toUpperCase()+dayName.slice(1)}</div>`;
                element.appendChild(weatherIcon);
                element.innerHTML+=`<div class="weatherNameAccurate">${weatherNameAccurate}</div> <div class="tempMinMax">${tempMax}${weatherUnits[0]} | ${tempMin}${weatherUnits[0]}</div>`;


                //zmiana koloru pojemnika kafelka informującego o jakości pogody danego dnia
                if(weatherPreferencesGrade[element.id]<=13)
                    element.querySelector('.dayName').style.background  = 'linear-gradient(180deg, rgba(8,215,35,0.6113795860140931) 0%, rgba(9,121,73,0.300455216266194) 100%)';
                else 
                    element.querySelector('.dayName').style.background  = 'linear-gradient(180deg, rgb(215 8 8 / 61%) 0%, rgb(121 9 9 / 30%) 100%)';



                //wyświetlenie dokładnych danych na dolnym panelu po kliknięciu na kafelek
                element.addEventListener('click', event => {

                    $(".arrow-down").remove();
                    var arrow= document.createElement("DIV");
                    arrow.setAttribute("class", "arrow-down");
                    element.appendChild(arrow);

                    currentTile = element.id;
                    showWeatherData(todayWeather);

                    //obsługa działania suwaka godziny
                    if(currentTile>1){
                        rangeInput.style.opacity=0.7;
                        rangeInput.disabled = true;
                    }
                        else{
                            rangeInput.style.opacity=1;
                            rangeInput.disabled = false;
                        }
                })
                var arrow= document.createElement("DIV");
                arrow.setAttribute("class", "arrow-down");
                tilesArray[0].appendChild(arrow);


                //listener suwaka
                rangeInput.addEventListener('change', event =>{
                    if(currentTile==0 || currentTile==1)
                        showHourWeatherData(data.hourly[ Number(rangeInput.value) + Number(24*currentTile)]);
                });
            });
        });
        //end of fetch

        //wyświetlanie którą godzinę zaznaczył użytkownik
        timeInput.addEventListener('input', event =>{
            timeBubble.innerHTML= `${timeInput.value}:00`;
        });
    };

    //liczenie czy pogoda jest dobra
    function calculateWeatherGrade (tile , todayWeather, weatherPreferences, weatherPreferencesGrade) {
        const compareParametres = ['temp','wind_speed','clouds','pop'];
        console.log(todayWeather);

        preferenceBoxesArray.forEach ( (box,index) => {

            console.log('tileid='+tile.id); 
            let i=index;
            let tempInput = box.querySelector(".preferenceInput");
            let scope = tempInput.max - tempInput.min;
            let difference = 0;
            let funcModifier =0;
            let returnGrade=0;

            if(index!=0)
                 difference = Math.abs(weatherPreferences[i] - todayWeather[compareParametres[i]]);
            else{
                 difference = Math.abs(weatherPreferences[i] - todayWeather.temp.max);
                 funcMmodifier = 0.3;
            }
            if(index===1 || index===2)
                funcMmodifier=0.13;
            console.log('temp='+todayWeather.temp.day + 'difference='+difference);

            returnGrade += getGradePart(difference,funcModifier);
            
            console.log('grade czesciowy=' + returnGrade);
            weatherPreferencesGrade[tile.id] += returnGrade;
            console.log('zmienna pogodowa=' +weatherPreferencesGrade[tile.id]);
        })
    }

    //funkcje wspierające
    function showWeatherData (todayWeather){    

        let dataArray = [todayWeather.temp.max,
            todayWeather.temp.min,
            todayWeather.wind_speed,
            todayWeather.humidity,
            precipValue(todayWeather),
            getTimeFromJS(todayWeather.sunrise),
            getTimeFromJS(todayWeather.sunset),
            todayWeather.moon_phase,
            todayWeather.clouds
            ]

        //wyświetlenie temperatury
        divsArray[0].innerHTML =`${dataArray[0]}${weatherUnits[0]} | ${dataArray[1]}${weatherUnits[0]}`;
        
        //wyświetlenie reszty danych
        for(let i=2; i<9;i++){
            divsArray[i-1].innerHTML = `${dataArray[i]}${weatherUnits[i-1]}`;
        }
    }

    function showHourWeatherData (todayWeather){
       let dataArray = [
           todayWeather.temp,
           todayWeather.wind_speed,
           todayWeather.humidity,
           precipValue(todayWeather),
           todayWeather.clouds
       ]
       //wyświetlenie danych z pierwszej kolumny
       for(let i=0; i<4; i++)
           divsArray[i].innerHTML = `${dataArray[i]}${weatherUnits[i]}`;
       
        //wyświetlenie wartości zachmurzenia
        divsArray[7].innerHTML = `${dataArray[4]}%`;
    }
     
    function getTimeFromJS (tempElement){
        let tempTime= new Date(tempElement * 1000);
        return `${tempTime.getHours()}:${tempTime.getMinutes()}`;
    }
    

    //wyświetlanie wartości suwaków preferencji pogodowych
    preferenceBoxesArray.forEach(box => {
        let preferenceInput = box.querySelector(".preferenceInput");
        let preferenceOutput = box.querySelector(".showValue");

        preferenceInput.addEventListener('input',() => {showRangeValue(preferenceInput,preferenceOutput) });

        showRangeValue(preferenceInput,preferenceOutput)
    });    

    function showRangeValue (input, output){
        output.innerHTML = `${input.value}`;
    }

    //zwrócenie wartości jak dobra jest pogoda dla danego parametru (im mniej tym lepiej)
    function getGradePart(diff, modifier=0){
        let tempSqr = modifier != 0 ? Math.pow(diff * modifier,3) : diff;
        let tempReturnVal = (-60.9375 / Math.pow(0.2 * tempSqr + 3.2, 2))+6;

        if (tempReturnVal>5)
            tempReturnVal=5;

        return tempReturnVal;
    }

    //pobranie wartości opadów
    function precipValue (temptodayWeather){
        let returnValue=0;

        if(temptodayWeather.hasOwnProperty('rain'))
            returnValue += temptodayWeather.rain;
        
        if(temptodayWeather.hasOwnProperty('snow'))
            returnValue += temptodayWeather.snow;

            return returnValue;
    }
    
    //wypisanie nazwy/ikony w zależności od ogólnej pogody danego dnia
    function getWeatherSummary(tempTodayWeather){
        let tempID = tempTodayWeather.weather[0].id;
        let returnValue;
        if(tempID===800)
            return imgArray[6];
        tempID = parseInt(tempID/100);
        return weatherGeneralStatus[tempID];
    }

    
}   

