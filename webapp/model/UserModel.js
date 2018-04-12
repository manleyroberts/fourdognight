var firebase = require('./FirebaseInterfacer.js');

module.exports.attemptRegistration = function(name, username, password, isAdmin, onSuccess, onFailure) {
  firebase.attemptRegistration(username, function() {
    var user = new User(name, username, password, isAdmin);
    onSuccess();
  }, onFailure);
}

module.exports.attemptLogin = function(username, password, onSuccess, onFailure) {
  firebase.attemptLogin(username, function(snapshot) {
    var user = new User(snapshot.child('name').val(), snapshot.child('username').val(), snapshot.child('password').val(), snapshot.child('isAdmin').val());
    if (user.username === username && user.password === password) {
      onSuccess().status(200).send('$' + user.name + ' | ' + user.username + ' | ' + (user.isAdmin ? "Admin" : "User") + '$');
    } else {
      onFailure();
    }
  }, onFailure);
}

User = function(name, username, password, isAdmin) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.isAdmin = isAdmin;
    firebase.updateUser(this);
    return this;
}
