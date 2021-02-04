// Getting the user choice for the game
const getUserSelected = userSelected =>{
    userSelected = userSelected.toLowerCase();
    if (userSelected === 'rock' || userSelected ==='paper' || userSelected ==='scissors') {
        return userSelected;
    } else {
        console.log('select a valid choice');
    }
};
// system generated choice for the game
function getSystemSelected() {
    switch(Math.floor(Math.random()*3)) {
        case 0:
            return 'rock';
            break;
        case 1:
            return 'paper';
            break;
        case 2:
            return 'scissors';
            break;
    };
}
// comparing the above values to get the result
function determineWinner(userSelection,systemSelection) {
    if (userSelection === systemSelection) {
        return 'It\'s a tie!';
    } else if (userSelection === 'rock') {
        if (systemSelection === 'paper') {
            return 'Computer wins!';
        } else {
            return 'You win!';
        }
    } else if (userSelection === 'paper'){
        if (systemSelection === 'scissors') {
            return 'Computer wins!';
        }else {
            return 'You win!';
        }
    } else if (userSelection === 'scissors') {
        if (systemSelection === 'rock') {
            return 'Computer wins!';
        } else {
            return 'You win!';
        }
    }
};
// onclick function defining
function playGame(Selection) {
    var userSelection = getUserSelected(Selection);
    var systemSelection = getSystemSelected()
    //getting the final winner for the game
    document.getElementById('user-selection').innerHTML = `User Selected : ${userSelection}`;   
    document.getElementById('system-selection').innerHTML = `System Selected : ${systemSelection}`;   
    document.getElementById('win').innerHTML = determineWinner(userSelection, systemSelection); 
};