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