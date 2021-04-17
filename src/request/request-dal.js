const { client } = require('../db');

async function save(request) {
  const result = await client.db().collection('requests').insertOne(request);
  return result.ops[0];
}

async function find() {
  return await client.db().collection('requests').find();
}

module.exports = {
  save,
  find,
};
