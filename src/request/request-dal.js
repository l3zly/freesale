const { ObjectID } = require('mongodb');
const { client } = require('../db');

const collection = 'requests';

async function save(request) {
  const result = await client.db().collection(collection).insertOne(request);
  result.ops[0].id = result.ops[0]._id.toHexString();
  delete result.ops[0]._id;
  return result.ops[0];
}

async function find() {
  return await client.db().collection(collection).find().toArray();
}

async function findById(id) {
  if (!ObjectID.isValid(id)) {
    return null;
  }
  return await client
    .db()
    .collection(collection)
    .findOne({ _id: ObjectID.createFromHexString(id) });
}

async function updateById(id, updates) {
  if (!ObjectID.isValid(id)) {
    return null;
  }
  await client
    .db()
    .collection(collection)
    .updateOne({ _id: ObjectID.createFromHexString(id) }, { $set: updates });
}

module.exports = {
  save,
  find,
  findById,
  updateById,
};
