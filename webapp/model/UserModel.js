var fs = require('fs');
var users = JSON.parse(fs.readFileSync(__dirname + '/users.json', 'utf8'));

module.exports.attemptRegistration = function(name, username, password, isAdmin) {
  for (var user of users) {
    if (user.email === username) {
      return false;
    }
  }
  if (isAdmin) {
    users.push(new module.exports.Admin(name, username, password));
  } else {
    users.push(new User(name, username, password));
  }
  fs.writeFileSync(__dirname + '/users.json', JSON.stringify(users));
  return true;
}

module.exports.attemptLogin = function(username, password) {
  for (var user of users) {
    if (user.username === username && user.password === password) {
      return user;
    }
  }
  return null;
}

BaseUser = function(name, username, password) {
  this.name = name;
  this.username = username;
  this.password = password;
  return this;
}

module.exports.Admin = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  return this;
}
module.exports.Admin.prototype = Object.create(BaseUser.prototype);

User = function(name, username, password) {
  BaseUser.call(this);
  this.name = name;
  this.username = username;
  this.password = password;
  return this;
}
User.prototype = Object.create(BaseUser.prototype);