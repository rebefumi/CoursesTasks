		var Game =(function (){
			var numberOfFaces = undefined;
			var theLeftSide = document.getElementById("leftSide");
			
			var  generateFaces = function (){
				var i=0;
				var img;
				for (i; i<numberOfFaces; i++){
					img = document.createElement("img");
					img.src="smile.png";
					img.style.top = randomNumber (400) + "px";
					img.style.left = randomNumber (400) + "px";
					theLeftSide.appendChild(img);
				}
			}

			var randomNumber = function (max){
				return Math.floor(Math.random() * max);
			}

			return {
				initGame: function (faces) {
					numberOfFaces = faces;
					generateFaces();
				}
			}
		})(); 