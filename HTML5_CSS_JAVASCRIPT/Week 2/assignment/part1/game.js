function do_game (){
	var game = new Game();
	alert (game.colorPick);
	
	while (!game.finish){
		game.askColor();
		game.checkGuess();
	}
	
}

function Game (){

	this.guesses = 0;
	this.guess_input = undefined;
	this.finish = false;
	this.colors = ["AliceBlue", "Bisque", "CornflowerBlue", "Crimson", "IndianRed", "LawnGreen", "Lime", "Linen", "Navy", "Olive", "Plum", "Silver", "Tan"];
	this.number = randomNumber(this.colors.length);
	this.colorPick = this.colors[this.number];

	this.askColor = function (){
			this.guess_input= prompt("I am thinking one this colors: \n"+ this.colors + "\nWhat color am I thinking of?");
	};

	this.checkGuess = function (){
		this.guesses ++;
		if (this.guess_input === null){
			this.finish = true;
		}else{
			if(this.isInColors()!== -1){
				if (this.guess_input != this.colorPick){
					alert("Sorry, your guess is not correct!. \nTry again!");
				}else{
					this.finish = true;
					alert( "Congratulations! You have guessed the color!\n It took you " + this.guesses + " guesses to finish the game! \n You can see the color in the background.");
				}
			}else{
				alert("Sorry, I don't recognize your color. \nTry again!");
			}	
		}
	};

	this.isInColors = function (){
		return this.colors.indexOf(this.guess_input);
	}

}

function randomNumber(max){
	return Math.floor(Math.random() * max);
}