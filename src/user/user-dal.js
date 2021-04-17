const { client } = require('../db');

const collection = 'users';

async function save(user) {
  const result = await client.db().collection(collection).insertOne(user);
  delete result.ops[0].password;
  return result.ops[0];
}

async function findByPhone(phone) {
  return await client
    .db()
    .collection(collection)
    .findOne({ phone }, { projection: { password: 0 } });
}

async function getPassword(phone) {
  const user = await client
    .db()
    .collection(collection)
    .findOne({ phone }, { projection: { password: 1 } });
  if (!user) {
    return null;
  }
  return user.password;
}

module.exports = {
  save,
  findByPhone,
  getPassword,
};
