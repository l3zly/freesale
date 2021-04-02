const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('supertest');
const assert = require('assert').strict;
const app = require('../../app');

Given(
  'I provide {} and {} as my phone number and password',
  function (phone, password) {
    this.context.phone = phone;
    this.context.password = password;
  }
);

When('I try to signup', async function () {
  const { phone, password } = this.context;

  const response = await request(app).post('/users').send({ phone, password });

  this.context.status = response.status;
});

Then('the response status is {int}', function (status) {
  assert.equal(this.context.status, status);
});
