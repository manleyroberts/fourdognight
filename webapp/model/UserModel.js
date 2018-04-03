var firebase = require('./FirebaseInterfacer.js');

module.exports.attemptRegistration = function(name, username, password, isAdmin, onSuccess, onFailure) {
  firebase.attemptRegistration(username, function() {
    if (isAdmin) {
      var admin = new Admin(name, username, password);
    } else {
      var user = new User(name, username, password);
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
  return this;
}

Admin = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  firebase.updateUser(this, true);
  return this;
}
Admin.prototype = Object.create(BaseUser.prototype);

User = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  firebase.updateUser(this, false);
  return this;
}
User.prototype = Object.create(BaseUser.prototype);
