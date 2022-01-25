	var textFields = document.querySelectorAll(".blockInput");
  var pins = document.querySelectorAll(".pincolor");
  var items = document.querySelectorAll(".makeRequired");
  
  var pinStateArray = [];
  var stateArray = [];
  
  for(let i=0; i<pins.length;i++)
  	pinStateArray[i]=false;
  for(let i=0; i<items.length;i++)
  	stateArray[i]=false;
    

  textFields.forEach( (element,index) => 
  	{element.addEventListener("change", function(){      
      if(element.value!="")
      	stateArray[index]=true;
      else
      		stateArray[index]=false;	
      checkIfFull();
     	}		
  	)}
  );
  
  pins.forEach((element,index) => {
  	element.addEventListener("click", function(){
        //check if pin is already selected
        if(pinStateArray[index]){
        	element.checked=false;
          pinStateArray[index]=false;
          stateArray[index+2]=false;
        }
        else{
        	pinStateArray.forEach(item =>{ item=false;});
        	pinStateArray[index]=true;
          element.checked=true;
          stateArray[index+2]=true;
        }
        checkIfFull();
    });
  });
  
  
  function checkIfFull(){
  console.log(stateArray);
  //check if input [i] have data, if only one yes, then set required attr 
  //else clear them
		for(let i=0; i<items.length;i++){
  		if(stateArray[i]===true){
    			setAttribute(true);
        	return;
      }
    }
   setAttribute(false);
	}

	function setAttribute(state){
  console.log("state"+ state);
		for(let i=0; i<items.length;i++)
      	items[i].required = state;
	}




