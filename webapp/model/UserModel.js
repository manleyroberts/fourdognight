var firebase = require('./FirebaseInterfacer.js');
var users = [];

module.exports.attemptRegistration = function(name, username, password, isAdmin, onSuccess, onFailure) {
  firebase.attemptRegistration(username, function() {
    if (isAdmin) {
      var admin = new module.exports.Admin(name, username, password);
    } else {
      var user = new module.exports.User(name, username, password);
    }
    onSuccess();
  }, onFailure);
}

module.exports.attemptLogin = function(username, password, onSuccess, onFailure) {
  firebase.attemptLogin(username, function(snapshot) {
    var user = snapshot.child('isAdmin').val() ? new Admin(snapshot.child('name').val(), snapshot.child('username').val(), snapshot.child('password').val()) : new User(snapshot.child('name').val(), snapshot.child('username').val(), snapshot.child('password').val());
    if (user.username === username && user.password === password) {
      onSuccess().status(200).send('$' + user.name + ' | ' + user.username + ' | ' + ((user instanceof Admin) ? 'Admin' : 'User') + '$');
    } else {
      onFailure();
    }
  }, onFailure);
}

BaseUser = function(name, username, password) {
  this.name = name;
  this.username = username;
  this.password = password;
  users[username] = this;
  pushUserChanges();
  return this;
}

Admin = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  return this;
}
Admin.prototype = Object.create(BaseUser.prototype);

module.exports.Admin = Admin;

User = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  return this;
}
User.prototype = Object.create(BaseUser.prototype);

pushUserChanges = function() {
  for (var user of users) {
    firebase.updateUser(user);
  }
}