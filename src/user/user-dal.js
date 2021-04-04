const { client } = require('../db');

async function save(user) {
  const result = await client.db().collection('users').insertOne(user);
  delete result.ops[0].password;
  return result.ops[0];
}

async function findByPhone(phone, includePassword = false) {
  return await client
    .db()
    .collection('users')
    .findOne({ phone }, { projection: { password: includePassword } });
}

module.exports = {
  save,
  findByPhone,
};
