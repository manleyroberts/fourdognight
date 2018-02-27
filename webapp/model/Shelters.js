const fs = require('fs');
const readline = require('readline');
var shelters = [];

module.exports.shelters = shelters;

readline.createInterface({
  input: fs.createReadStream(__dirname + '/HomelessShelterDatabase.csv'),
  crlfDelay: Infinity
}).on('line', (line) => {
    console.log(line.toString().match(/("(.+?)")|([^,]+)/g));
    shelters.push(line.toString().match(/("(.+?)")|([^,]+)/g));
});