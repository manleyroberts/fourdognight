var express = require('express');
var bodyparser = require('body-parser');
var userModel = require('./model/UserModel.js');
var Admin = userModel.Admin;
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

app.get("/mainpage.html", function(req, res) {
  res.sendFile(__dirname + "/html/mainpage.html");
});

app.get("/login.html", function(req, res) {
  res.sendFile(__dirname + "/html/login.html");
});

app.get("/register.html", function(req, res) {
  res.sendFile(__dirname + "/html/register.html");
});

app.listen(8080);