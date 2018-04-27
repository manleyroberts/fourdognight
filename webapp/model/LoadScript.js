var firebase = require('./FirebaseInterfacer.js');

const fs = require('fs');
const readline = require('readline');

var shelters = [];

var rl = readline.createInterface({
  input: fs.createReadStream(__dirname + '/HomelessShelterDatabase.csv'),
  crlfDelay: Infinity,
});
rl.on('line', (line) => {
  var match = line.toString().match(/("(.+?)")|([^,]+)/g);
  var contains = false;
  for (var shelter of shelters) {
    if (match[0] == shelter[0]) {
      contains = true;
      break;
    }
  }
  if (!contains && match[0] !== 'Unique Key') {
    shelters.push(match);
  }
});
rl.on('close', (close) => {
  firebase.runLoadScript(shelters);
});