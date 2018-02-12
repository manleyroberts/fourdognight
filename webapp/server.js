var express = require('express');
var app = express();
var bodyparser = require('body-parser');

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

var sessions = new Set();

app.get("/", function(req, res) {
  res.sendFile(__dirname + "/html/index.html");
});

app.post("/login", function(req, res) {
  if (req.body.username === "user" && req.body.password === "pass") {
    res.status(200).send(null);
  } else {
    res.status(401).send(null);
  }
});

app.get("/mainpage.html", function(req, res) {
  res.sendFile(__dirname + "/html/mainpage.html");
});

app.get("/login.html", function(req, res) {
  res.sendFile(__dirname + "/html/login.html");
});

app.listen(8080);