var firebase = require('./FirebaseInterfacer.js');

const fs = require('fs');
const readline = require('readline');

shelters = [];

Shelter = function(uniqueKey, shelterName, capacity, vacancy, restriction, location, special, phone, newPatrons) {
  this.shelterName = shelterName;
  this.capacity = capacity;
  this.vacancy = vacancy;
  this.uniqueKey = uniqueKey;
  this.restriction = restriction;
  this.location = location;
  this.special = special;
  this.phone = phone;
  this.currentPatrons = [];
  for (var user of newPatrons) {
    currentPatrons.push(user);
  }
  this.toString = function() {
    return shelterName + " " + restriction + " " + location.address " " + special + " " + phone + " ";
  }
  return this;
}

ShelterLocation = function(longitude, latitude, address) {
  this.latitude = latitude;
  this.longitude = longitude;
  this.address = address;
  return this;
}

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