const { promisify } = require('util');
const jwt = require('jsonwebtoken');
const { tokenSecret } = require('../config');

const sign = promisify(jwt.sign);
const verify = promisify(jwt.verify);

async function generateToken(payload) {
  return await sign(payload, tokenSecret);
}

async function decodeToken(token) {
  return await verify(token, tokenSecret);
}

module.exports = {
  generateToken,
  decodeToken,
};
