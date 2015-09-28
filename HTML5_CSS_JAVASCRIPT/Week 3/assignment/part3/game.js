		var Game =(function (){
			var numberOfFaces = undefined;
			var theLeftSide = document.getElementById("leftSide");
			var theRightSide = document.getElementById("rightSide");

			var  generateFaces = function (){
				var i=0;
				var img;
				var leftSideImages;
				for (i; i<numberOfFaces; i++){
					img = document.createElement("img");
					img.src="smile.png";
					img.style.top = randomNumber (400) + "px";
					img.style.left = randomNumber (400) + "px";
					theLeftSide.appendChild(img);

					leftSideImages = theLeftSide.cloneNode(true);
				}

				cloneRightSide(leftSideImages);
			};

			var cloneRightSide = function (leftImages){
				leftImages.removeChild(leftImages.lastChild);
				rightSide.appendChild(leftImages);
			};

			var randomNumber = function (max){
				return Math.floor(Math.random() * max);
			};

			return {
				initGame: function (faces) {
					numberOfFaces = faces;
					generateFaces();
				}
			}
		})(); 