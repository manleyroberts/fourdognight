var fs = require('fs')
var users = JSON.parse(fs.readFileSync(__dirname + '/users.json', 'utf8'));

module.exports.users = users;

module.exports.User = function(name, email, password) {
    this.name = name;
    this.email = email;
    this.password = password;
    users.push(this);
    fs.writeFileSync(__dirname + '/users.json', JSON.stringify(users));
    return this;
}

module.exports.TestValidUser = function(name, email, password) {
    var errors = null;
    if (!email.includes('@')) {
        errors = "EMAIL";
    }
    if (password.length < 8) {
        errors = (errors == null) ? "PASSWORD" : errors + "PASSWORD";
    }
    for (var user of users) {
        if (user.email == email) {
            errors = (errors == null) ? "EXISTS" : errors + "EXISTS";
            break;
        }
    }
    return errors;
}