const { client } = require('../db');

async function save(user) {
  const result = await client.db().collection('users').insertOne(user);
  user.id = result.insertedId;
  return user;
}

module.exports = {
  save,
};
