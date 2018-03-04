var express = require('express');
var bodyparser = require('body-parser');
var userModel = require('./model/UserModel.js');
var Admin = userModel.Admin;
var shelterModel = require('./model/ShelterModel.js');
var readShelters = shelterModel.readShelters;
var shelters = shelterModel.shelters;
var app = express();

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());


app.get("/", function(req, res) {
  res.sendFile(__dirname + "/html/index.html");
});

app.post("/login", function(req, res) {
  var luser = userModel.attemptLogin(req.body.email, req.body.password);
  if (luser == null) {
    res.status(401).send(null);
  } else {
    res.status(200).send('$' + luser.name + ' | ' + luser.username + ' | '  + ((luser instanceof Admin) ? 'Admin' : 'User') + '$');
  }
});

app.post("/register", function(req, res) {
  var errors = "";
  if (!req.body.email.includes('@')) {
    errors += "EMAIL";
  }
  if (req.body.password.length < 8) {
    errors += "PASSWORD";
  }
  if (req.body.password !== req.body.password2) {
    errors += "MISMATCH";
  }
  if (errors !== "") {
    res.status(401).send(errors);
  } else if (userModel.attemptRegistration(req.body.name, req.body.email, req.body.password, req.body.admin)) {
    res.redirect("/login.html");
  } else {
    res.status(401).send("EXISTS");
  }
});

app.post("/mainpage", function(req, res) {
  readShelters(function() {
    var resbody = '';
    for (i = 1; i < shelters.length; i++) {
      var san1 = shelters[i][1].replace(/&/g, 'and');
      var san2 = san1.replace(/'/g, '\\\'');
      resbody += '<li onclick="displayShelter(\'' + san2 + '\')">' + san1 + '</li>';
    }
    res.status(200).send(resbody);
  });
});

app.post("/shelter", function(req, res) {
  var shelter = shelters.find(function(entry) {
    return entry[1] === req.body.shelter;
  });
  res.status(200).send('<h4>Name:</h4><p>' + shelter[1].replace(/"/g, '') + '</p><h4>ID:</h4><p>'
    + shelter[0] + '</p><h4>Capacity:</h4><p>' + shelter[2].replace(/"/g, '') + '</p><h4>Notes:</h4><p>'
    + shelter[7].replace(/"/g, '') + '</p><h4>Restrictions:</h4><p>' + shelter[3].replace(/"/g, '') + '</p><h4>Phone:</h4><p>'
    + shelter[8].replace(/"/g, '') + '</p><h4>Longitude:</h4><p>' + shelter[4].replace(/"/g, '') + '</p><h4>Latitude:</h4><p>'
    + shelter[5].replace(/"/g, '') + '</p><h4>Address:</h4><p>' + shelter[6].replace(/"/g, '') + '</p>');
});

app.get("/mainpage.html", function(req, res) {
  res.sendFile(__dirname + "/html/mainpage.html");
});

app.get("/login.html", function(req, res) {
  res.sendFile(__dirname + "/html/login.html");
});

app.get("/register.html", function(req, res) {
  res.sendFile(__dirname + "/html/register.html");
});

app.get("/shelter.html", function(req, res) {
  res.sendFile(__dirname + "/html/shelter.html");
});

app.listen(8080);