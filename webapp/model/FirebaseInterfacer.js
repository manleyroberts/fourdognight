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
  database.ref('test/userList').once('value', function(snapshot) {
    if (!snapshot.hasChild(sanitize(username))) {
      onSuccess();
    } else {
      onFailure();
    }
  });
}

module.exports.attemptLogin = function(username, onPossibleSuccess, onFailure) {
  var userRef = database.ref('test/userList/' + sanitize(username));
  userRef.once('value', function(snapshot) {
    if (!snapshot.exists()) {
      onFailure();
    } else {
      onPossibleSuccess(snapshot);
    }
  });
}

module.exports.updateUser = function(user) {
  database.ref('test/userList').child(sanitize(user.username)).set(user);
}

module.exports.getUser = function(username, onSuccess) {
  database.ref('test/userList/' + sanitize(username)).once('value', function(snapshot) {
    onSuccess(snapshot.val());
  });
}

module.exports.updateShelter = function(shelter) {
  database.ref('test/shelterList/').child(shelter.uniqueKey).set(shelter);
}

module.exports.getShelterData = function(onSuccess) {
  database.ref('test/shelterList').on('value', function(snapshot) {
    var results = [];
    snapshot.forEach(function(childSnap) {
      var shelter = childSnap.val();
      if (!childSnap.hasChild('currentPatrons')) {
        shelter.currentPatrons = [];
      }
      results.push(shelter);
    });
    onSuccess(results);
  });
}

module.exports.runLoadScript = function(shelters) {
    database.ref('shelterList').once('value', function(snapshot) {
        for (var shelter of shelters) {
            snapshot.ref.child(shelter[0]).set(shelter);
        }
    }).then(() => {
        console.log('Shelters loaded successfully');
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