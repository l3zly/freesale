const { ObjectID } = require('mongodb');
const { client } = require('../db');

const collection = 'requests';

async function save(request) {
  const result = await client.db().collection(collection).insertOne(request);
  return result.ops[0];
}

async function find() {
  return await client.db().collection(collection).find().toArray();
}

async function findById(id) {
  return await client
    .db()
    .collection(collection)
    .findOne({ _id: ObjectID.createFromHexString(id) });
}

async function update(id, updates) {
  await client
    .db()
    .collection(collection)
    .updateOne({ _id: ObjectID.createFromHexString(id) }, { $set: updates });
}

module.exports = {
  save,
  find,
  findById,
  update,
};
