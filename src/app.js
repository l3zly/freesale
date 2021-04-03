const express = require('express');
const { userApi } = require('./user');
const { errors } = require('./middleware');

const app = express();

app.use(express.json());

app.use('/users', userApi);

app.use(errors);

module.exports = app;
