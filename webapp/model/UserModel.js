var firebase = require('./FirebaseInterfacer.js');
var shelterModel = require('./ShelterModel.js');

module.exports.attemptRegistration = function(name, username, password, isAdmin, onSuccess, onFailure) {
  firebase.attemptRegistration(username, function() {
    var user = new User(name, username, password, isAdmin, -1);
    onSuccess();
  }, onFailure);
}

module.exports.attemptLogin = function(username, password, onSuccess, onFailure) {
  firebase.attemptLogin(username, function(snapshot) {
    var user = new User(snapshot.child('name').val(), snapshot.child('username').val(), snapshot.child('password').val(), snapshot.child('isAdmin').val(), -1);
    if (user.username === username && user.password === password) {
      onSuccess().status(200).send('$' + user.name + ' | ' + user.username + ' | ' + (user.isAdmin ? "Admin" : "User") + '$');
    } else {
      onFailure();
    }
  }, onFailure);
}

User = function(name, username, password, isAdmin, currentShelterUniqueKey) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.isAdmin = isAdmin;
    this.currentShelterUniqueKey = currentShelterUniqueKey;
    this.currentShelter = null;
    this.heldBeds = 0;
    module.exports.setCurrentStatus(this, currentShelterUniqueKey, this.heldBeds);
    return this;
}

module.exports.setCurrentStatus = function(user, newShelterUniqueKey, heldBeds) {
  if (user.currentShelter != null) {
    user.currentShelter.currentPatrons.remove(user);
  }
  user.currentShelterUniqueKey = -1;
  user.currentShelter = shelterModel.getShelter(newShelterUniqueKey);
  user.currentShelterUniqueKey = newShelterUniqueKey;
  if (newShelterUniqueKey !== -1) {
    user.currentShelter.vacancy += user.heldBeds;
  }
  user.heldBeds = heldBeds;
  if (newShelterUniqueKey !== -1) {
    user.currentShelter.vacancy -= heldBeds;
    firebase.updateShelter(user.currentShelter);
  }
  firebase.updateUser(user);
}

module.exports.userCanStayAt = function(username, shelter, onPossibleSuccess, onFailure) {
  firebase.getUser(username, function(user) {
    if (!user.isAdmin && (user.currentShelterUniqueKey === -1 || user.currentShelterUniqueKey === shelter.uniqueKey || user.heldBeds === 0)) {
      onPossibleSuccess();
    } else {
      onFailure();
    }
  })
}
