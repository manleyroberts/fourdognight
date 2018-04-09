var firebase = require('firebase');
var config = {
  apiKey: "AIzaSyDtrX_sjvbL8fxRLZOqvkdsaKF3yt6PxQE",
  authDomain: "casa-db572.firebaseapp.com",
  databaseURL: "https://casa-db572.firebaseio.com",
  storageBucket: "casa-db572.appspot.com",
};
var app = firebase.initializeApp(config);
var database = app.database();

module.exports.attemptRegistration = function(username, onSuccess, onFailure) {
  database.ref('userList').once('value', function(snapshot) {
    if (!snapshot.hasChild(sanitize(username))) {
      onSuccess();
    } else {
      onFailure();
    }
  });
}

module.exports.attemptLogin = function(username, onPossibleSuccess, onFailure) {
  var userRef = database.ref('userList/' + sanitize(username));
  userRef.once('value', function(snapshot) {
    if (!snapshot.exists()) {
      onFailure();
    } else {
      onPossibleSuccess(snapshot);
    }
  });
}

module.exports.updateUser = function(user) {
  var userRef = database.ref('userList');
  userRef.child(sanitize(user.username)).set(user);
}

module.exports.getShelterData = function(onSuccess) {
  database.ref('shelterList').on('value', function(snapshot) {
    snapshot.forEach(function(childSnap) {

    });
  });
}

function sanitize(dbPath) {
  var sanitized = dbPath.replace(/[\.#$\[\]]/g, '');
  if (sanitized) {
    return sanitized;
  } else {
    return ' ';
  }
}