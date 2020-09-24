const baseApiURL = 'https://pokeapi.co/api/v2/pokemon';


const fetchPokemon = async () => {
  //set visible loading status
  document.getElementById('loading').style.visibility = 'visible'
  document.getElementById('battleButton').style.visibility = 'hidden'


  // call the base API url to get the total count of pokemon
  //const initial = await fetch(baseApiURL)
  //const initialJson = await initial.json()
  
  // get the total count and find a random value for the left and right side
  const count = 151 //initialJson.count;
  const leftPokemon = Math.floor(Math.random() * count) + 1; 
  const rightPokemon = Math.floor(Math.random() * count) + 1; 

  // call the api with the left side
  const left = await fetch(baseApiURL + '/' + leftPokemon)
  const leftJson = await left.json()
  
  // call the api with the left side
  const right = await fetch(baseApiURL + '/' + rightPokemon)
  const rightJson = await right.json()


  // Set up the stage
  // put the json values from left and right into the two divs on the html page
  
  // set images
  document.getElementById('leftImg').setAttribute('src', leftJson.sprites.front_default)
  document.getElementById('rightImg').setAttribute('src', rightJson.sprites.front_default)
  
  // set names
  document.getElementById('leftName').innerHTML = leftJson.name
  document.getElementById('rightName').innerHTML = rightJson.name

  // set hp
  document.getElementById('leftHP').innerHTML = 'HP: ' + leftJson.stats[0].base_stat
  document.getElementById('rightHP').innerHTML = 'HP: ' + rightJson.stats[0].base_stat

  // set attack
  document.getElementById('leftAttack').innerHTML = 'Attack: ' + leftJson.stats[1].base_stat
  document.getElementById('rightAttack').innerHTML = 'Attack: ' + rightJson.stats[1].base_stat

  let winner = ''
  // find the winner
  if(leftJson.stats[1].base_stat >= rightJson.stats[1].base_stat)
  {
    winner = leftJson.name
    document.getElementById('leftResults').setAttribute('class', 'card bg-success')
    document.getElementById('rightResults').setAttribute('class', 'card bg-danger')
  } else {
    winner = rightJson.name
    document.getElementById('leftResults').setAttribute('class', 'card bg-danger')
    document.getElementById('rightResults').setAttribute('class', 'card bg-success')

  }

  document.getElementById('results').innerHTML = `----------Results----------`
  document.getElementById('results').innerHTML += '<br>'
  document.getElementById('results').innerHTML += 'Count: ' + count
  document.getElementById('results').innerHTML += '<br>'
  document.getElementById('results').innerHTML += 'First pokemon: ' + leftJson.name
  document.getElementById('results').innerHTML += '<br>'
  document.getElementById('results').innerHTML += 'Second pokemon: ' + rightJson.name
  document.getElementById('results').innerHTML += '<br>'
  document.getElementById('results').innerHTML += 'Winner:<b>' + winner + '</b>'


  // show no longer loading
  document.getElementById('loading').style.visibility = 'hidden';
  document.getElementById('battle').style.visibility = 'visible';

  document.getElementById('battleButton').innerHTML = 'Battle again?'
  document.getElementById('battleButton').style.visibility = 'visible'
}