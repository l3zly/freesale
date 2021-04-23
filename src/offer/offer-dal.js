const { ObjectID } = require('mongodb');
const { client } = require('../db');

const collection = 'offers';

async function save(offer) {
  const result = await client.db().collection(collection).insertOne(offer);
  return result.ops[0];
}

async function deleteById(id) {
  await client
    .db()
    .collection(collection)
    .deleteOne({ _id: ObjectID.createFromHexString(id) });
}

module.exports = {
  save,
  deleteById,
};
