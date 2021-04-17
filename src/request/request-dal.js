const { client } = require('../db');

const collection = 'requests';

async function save(request) {
  const result = await client.db().collection(collection).insertOne(request);
  return result.ops[0];
}

async function find() {
  return await client.db().collection(collection).find();
}

module.exports = {
  save,
  find,
};
