const { client } = require('../db');

async function save(user) {
  const result = await client.db().collection('users').insertOne(user);
  delete result.ops[0].password;
  return result.ops[0];
}

module.exports = {
  save,
};
