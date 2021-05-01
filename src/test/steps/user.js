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
  'I provide {string}, {string} and {string} as my phone number, postcode and password',
  function (phone, postcode, password) {
    this.context.phone = phone;
    this.context.postcode = postcode;
    this.context.password = password;
  }
);

Given('a user with the provided phone number exists', async function () {
  const { phone } = this.context;
  await signup(phone, 'LN7 6DH', '12345678');
});

Given(
  'a user with the phone number {string} and password {string} exists',
  async function (phone, password) {
    await signup(phone, 'LN7 6DH', password);
  }
);

When('I try to signup', async function () {
  const { phone, postcode, password } = this.context;

  const response = await signup(phone, postcode, password);

  this.context.status = response.status;
});

When('I try to login', async function () {
  const { phone, password } = this.context;

  const response = await request(app)
    .post('/users/login')
    .send({ phone, password });

  this.context.status = response.status;
});

Then('the response status is {int}', function (status) {
  assert.equal(this.context.status, status);
});

async function signup(phone, postcode, password) {
  return await request(app).post('/users').send({ phone, postcode, password });
}
