var express = require('express');
var bodyparser = require('body-parser');
var userModel = require('./model/User.js');
var User = userModel.User;
var users = userModel.users;
var app = express();

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());


app.get("/", function(req, res) {
  res.sendFile(__dirname + "/html/index.html");
});

app.post("/login", function(req, res) {
  for (var user of users) {
    if (user.email === req.body.email && user.password === req.body.password) {
      res.status(200).send(null);
      return;
    }
  }
  res.status(401).send(null);
});

app.post("/register", function(req, res) {
  new User(req.body.name, req.body.email, req.body.password);
  res.redirect("/login.html");
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