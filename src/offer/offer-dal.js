const { ObjectID } = require('mongodb');
const { client } = require('../db');

const collection = 'offers';

async function save(offer) {
  const result = await client.db().collection(collection).insertOne(offer);
  result.ops[0].id = result.ops[0]._id.toHexString();
  delete result.ops[0]._id;
  return result.ops[0];
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

async function deleteById(id) {
  if (!ObjectID.isValid(id)) {
    return;
  }
  await client
    .db()
    .collection(collection)
    .deleteOne({ _id: ObjectID.createFromHexString(id) });
}

module.exports = {
  save,
  findById,
  updateById,
  deleteById,
};
