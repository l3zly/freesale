const express = require('express');
const { userApi } = require('./user');
const { requestApi } = require('./request');
const { offerApi } = require('./offer');
const { errors } = require('./middleware');

const app = express();

app.use(express.json());

app.use('/users', userApi);
app.use('/requests', requestApi);
app.use('/offers', offerApi);

app.use(errors);

module.exports = app;
