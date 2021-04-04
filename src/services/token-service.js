const { promisify } = require('util');
const jwt = require('jsonwebtoken');

const sign = promisify(jwt.sign);

async function generateToken(payload) {
  return await sign(payload, process.env.TOKEN_SECRET);
}

module.exports = {
  generateToken,
};
