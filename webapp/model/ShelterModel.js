const fs = require('fs');
const readline = require('readline');

module.exports.shelters = [];

module.exports.readShelters = function(onCompleted) {
  var rl = readline.createInterface({
    input: fs.createReadStream(__dirname + '/HomelessShelterDatabase.csv'),
    crlfDelay: Infinity,
  });
  rl.on('line', (line) => {
    var match = line.toString().match(/("(.+?)")|([^,]+)/g);
    var contains = false;
    for (var shelter of module.exports.shelters) {
      if (match[0] == shelter[0]) {
        contains = true;
        break;
      }
    }
    if (!contains) {
      module.exports.shelters.push(match);
    }
  });
  rl.on('close', (close) => {
    onCompleted();
  });
}