const express = require('express');
const users = require('./user');

const app = express();

app.use(express.json());

app.use('/users', users);

module.exports = app;
