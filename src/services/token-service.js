const { promisify } = require('util');
const jwt = require('jsonwebtoken');
const { tokenSecret } = require('../config');

const sign = promisify(jwt.sign);

async function generateToken(payload) {
  return await sign(payload, tokenSecret);
}

module.exports = {
  generateToken,
};
