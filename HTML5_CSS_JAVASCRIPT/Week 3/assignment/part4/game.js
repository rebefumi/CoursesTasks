		var Game =(function (){
			var numberOfFaces = undefined;
			var faces = undefined;
			var theLeftSide = document.getElementById("leftSide");
			var theRightSide = document.getElementById("rightSide");
			var theBody = document.getElementsByTagName("body")[0];

			var  generateFaces = function (){
				var i=0;
				var img;
				var leftSideImages;
				removeAllFaces (theLeftSide);
				removeAllFaces (theRightSide);
				for (i; i<numberOfFaces; i++){
					img = document.createElement("img");
					img.src="smile.png";
					img.style.top = randomNumber (400) + "px";
					img.style.left = randomNumber (400) + "px";
					theLeftSide.appendChild(img);

					leftSideImages = theLeftSide.cloneNode(true);
				}

				cloneRightSide(leftSideImages);
				onClickListener ();
				bodyListener ();
			};

			var cloneRightSide = function (leftImages){
				leftImages.removeChild(leftImages.lastChild);
				rightSide.appendChild(leftImages);
			};

			var randomNumber = function (max){
				return Math.floor(Math.random() * max);
			};

			var onClickListener = function (){
				theLeftSide.lastChild.onclick = function nextLevel(event){
				        event.stopPropagation();
				        numberOfFaces += faces;
				        generateFaces();
				};
			};

			var bodyListener = function (){
				theBody.onclick = function gameOver() {
				    alert("Game Over!");
				    theBody.onclick = null;
				    theLeftSide.lastChild.onclick = null;
				    newGame();
				}; 
			};

			var newGame = function (){
				var res = confirm("Do you want to star a new game?");
				if (res === true) {
				   init(faces);
				}else{
				   removeAllFaces (theLeftSide);
				   removeAllFaces (theRightSide);
				}
			};

			var removeAllFaces = function (myNode){
				while (myNode.firstChild) {
				    myNode.removeChild(myNode.firstChild);
				}
			};

			var init = function (numFaces){
				faces = numFaces;
				numberOfFaces = faces;
				generateFaces();
			};

			return {
				initGame: init
			}
		})(); 