const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('supertest');
const assert = require('assert').strict;
const app = require('../../app');

Given(
  'I provide {string} and {string} as my phone number and password',
  function (phone, password) {
    this.context.phone = phone;
    this.context.password = password;
  }
);

Given(
  'a user with the provided phone number already exists',
  async function () {
    await signup(this.context.phone, 'password');
  }
);

When('I try to signup', async function () {
  const { phone, password } = this.context;

  const response = await signup(phone, password);

  this.context.status = response.status;
});

Then('the response status is {int}', function (status) {
  assert.equal(this.context.status, status);
});

async function signup(phone, password) {
  return await request(app).post('/users').send({ phone, password });
}
