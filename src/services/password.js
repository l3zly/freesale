const { promisify } = require('util');
const crypto = require('crypto');

const randomBytes = promisify(crypto.randomBytes);
const scrypt = promisify(crypto.scrypt);

async function encode(rawPassword) {
  const salt = await randomBytes(16);
  const key = await scrypt(rawPassword, salt, 64);
  return `${key.toString('hex')}.${salt.toString('hex')}`;
}

async function matches(encodedPassword, rawPassword) {
  const [key1, salt] = encodedPassword.split('.');
  const key2 = await scrypt(rawPassword, Buffer.from(salt, 'hex'), 64);
  return key2.toString('hex') === key1;
}

module.exports = {
  encode,
  matches,
};
