var userModel = require('./UserModel.js');
var Admin = userModel.Admin;

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

module.exports.updateUser = function(user) {
  var userRef = database.ref('userList');
  var userUpdate = [];
  userUpdate[user.username] = user;
  userRef.update(userUpdate);
  var userUpdate1 = [];
  userRef1['isAdmin'] = user instanceof Admin;
  userRef.ref(user.username).update(userUpdate1);
}