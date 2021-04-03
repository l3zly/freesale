const express = require('express');
const { userApi } = require('./user');

const app = express();

app.use(express.json());

app.use('/users', userApi);

module.exports = app;
