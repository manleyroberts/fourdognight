var express = require('express')
var app = express()

app.get("/", function(req, res) {
    res.sendFile(__dirname + "/html/index.html");
});

app.listen(8080);