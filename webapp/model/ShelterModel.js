var firebase = require('./FirebaseInterfacer.js');
var userModel = require('./UserModel.js'); // yay cyclic dependencies...

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
  return this;
}

module.exports.updateVacancy = function(shelterName, user, bedsHeld) {
  console.log(shelterName);
  console.log(user);
  console.log(bedsHeld);
  var shelter = shelters.find(function(element) {
    return shelterName === element.shelterName;
  });
  if (bedsHeld >= 0 && shelter.vacancy - bedsHeld >= 0) {
    shelter.currentPatrons.push(user.name);
    userModel.setCurrentStatus(user, shelter.uniqueKey, bedsHeld);
    return true;
  }
  return false;
}

ShelterLocation = function(longitude, latitude, address) {
  this.latitude = latitude;
  this.longitude = longitude;
  this.address = address;
  return this;
}

module.exports.getShelter = function(uniqueKey) {
  if (uniqueKey === -1) {
    return null;
  }
  return shelters[uniqueKey];
}

module.exports.getShelterData = function(onSuccess) {
  firebase.getShelterData(function(list) {
    for (var shelter of list) {
      shelters[shelter.uniqueKey] = shelter;
    }
    onSuccess(list);
  });
}

module.exports.getShelterDataUnique = function(name) {
  return shelters.find(function(entry) {
    return entry.shelterName === name;
  });
}