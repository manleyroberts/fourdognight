var firebase = require('./FirebaseInterfacer.js');

const fs = require('fs');
const readline = require('readline');

var shelters = [];

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
    currentPatrons.push(user.name);
  }
  this.toString = function() {
    return shelterName + " " + restriction + " " + location.address + " " + special + " " + phone + " ";
  }
  this.updateVacancy = function(user, bedsHeld) {
    if (bedsHeld >= 0 && vacancy - bedsHeld >= 0) {
      uesr.setCurrentStatus(uniqueKey, bedsHeld);
      currentPatrons.push(user.name);
    }
  }
  return this;
}

ShelterLocation = function(longitude, latitude, address) {
  this.latitude = latitude;
  this.longitude = longitude;
  this.address = address;
  return this;
}

module.exports.getShelterData = function(onSuccess) {
  firebase.getShelterData(function(list) {
    for (var shelter of list) {
      shelters[shelter.uniqueKey] = shelter;
    }
    onSuccess(list);
  });
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