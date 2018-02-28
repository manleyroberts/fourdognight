const fs = require('fs');
const readline = require('readline');

module.exports.shelters = [];

module.exports.readShelters = function(onCompleted) {
  var rl = readline.createInterface({
    input: fs.createReadStream(__dirname + '/HomelessShelterDatabase.csv'),
      crlfDelay: Infinity
  });
  rl.on('line', (line) => {
    module.exports.shelters.push(line.toString().match(/("(.+?)")|([^,]+)/g));
  });
  rl.on('close', (close) => {
    onCompleted();
  });
}