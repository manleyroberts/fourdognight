var express = require('express');
var bodyparser = require('body-parser');
var userModel = require('./model/UserModel.js');
var shelterModel = require('./model/ShelterModel.js');
var app = express();

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

app.get("/", function(req, res) {
  res.sendFile(__dirname + "/html/index.html");
});

app.post("/login", function(req, res) {
  userModel.attemptLogin(req.body.email, req.body.password, function() {
    return res;
  }, function() {
    res.status(401).send(null);
  });
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
  } else {
    console.log('req.body.admin=' + req.body.admin);
    userModel.attemptRegistration(req.body.name, req.body.email, req.body.password, req.body.admin == 'true', function() {
      res.redirect("/login.html");
    }, function() {
      res.status(401).send("EXISTS");
    });
  }
});

app.post("/mainpage", function(req, res) {
  shelterModel.getShelterData(function(list) {
    var resbody = '';
    var query = req.body.restriction;
    for (var shelter of list) {
      if (query === null || shelter.restriction.search(new RegExp(query, 'i')) != -1 || shelter.shelterName.search(new RegExp(query, 'i')) != -1) {
        var san1 = shelter.shelterName.replace(/&/g, 'and');
        var san2 = san1.replace(/'/g, '\\\'');
        resbody += '<li onclick="displayShelter(\'' + san2 + '\')">' + san1 + '</li>';
      }
    }
    res.status(200).send(resbody);
  });
});


app.post("/shelter", function(req, res) {
  if (req.body.request === undefined) {
    var shelter = shelterModel.getShelterDataUnique(req.body.shelter);
    res.status(200).send('<h4>Name:</h4><p>' + shelter.shelterName + '</p><h4>ID:</h4><p>'
      + shelter.uniqueKey + '</p><h4>Capacity:</h4><p>' + shelter.capacity + '</p><h4>Vacancy:</h4><p>' + shelter.vacancy + '</p><h4>Notes:</h4><p>'
      + shelter.special + '</p><h4>Restrictions:</h4><p>' + shelter.restriction + '</p><h4>Phone:</h4><p>'
      + shelter.phone + '</p><h4>Longitude:</h4><p>' + shelter.location.longitude + '</p><h4>Latitude:</h4><p>'
      + shelter.location.latitude + '</p><h4>Address:</h4><p>' + shelter.location.address + '</p>');
  } else {
    userModel.userCanStayAt(req.body.user, req.body.shelter, function() {
      if (req.body.request < 0 || !shelterModel.updateVacancy(req.body.shelter, req.body.user, req.body.request)) {
        res.status(401).send("NUMBER");
      } else {
        res.status(200).send(null);
      }
    }, function() {
      res.status(401).send("ELSEWHERE");
    });
  }
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

app.get("/search.html", function(req, res) {
  res.sendFile(__dirname + "/html/search.html");
});

app.listen(8080);