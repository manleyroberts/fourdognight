var firebase = require('firebase');
// Initialize Firebase
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
    if (!snapshot.hasChild(username)) {
      onSuccess();
    } else {
      onFailure();
    }
  });
}

module.exports.attemptLogin = function(username, onPossibleSuccess, onFailure) {
  var userRef = database.ref('userList/' + username);
  userRef.once('value', function(snapshot) {
    if (!snapshot.exists()) {
      onFailure();
    } else {
      onPossibleSuccess(snapshot);
    }
  });
}

module.exports.updateUser = function(user, isAdmin) {
  console.log('updateUser called');
  var userRef = database.ref('userList');
  userRef.child(user.username).set(user).then(function() {
    console.log('user synchronized to database');
  }).catch(function(error) {
    console.log('user synchronization failed');
  });
  userRef.child(user.username).child('isAdmin').set(isAdmin).then(function() {
    console.log('isAdmin synchronized to database');
  }).catch(function(error) {
    console.log('isAdmin synchronization failed');
  });
}