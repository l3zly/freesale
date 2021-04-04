const { client } = require('../db');

async function save(user) {
  const result = await client.db().collection('users').insertOne(user);
  delete result.ops[0].password;
  return result.ops[0];
}

async function findByPhone(phone, includePassword = false) {
  const projection = {
    phone: true,
    password: includePassword,
  };
  return await client
    .db()
    .collection('users')
    .findOne({ phone }, { projection });
}

module.exports = {
  save,
  findByPhone,
};
