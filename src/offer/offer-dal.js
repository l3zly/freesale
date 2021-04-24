const { ObjectID } = require('mongodb');
const { client } = require('../db');

const collection = 'offers';

async function save(offer) {
  const result = await client.db().collection(collection).insertOne(offer);
  result.ops[0].id = result.ops[0]._id.toHexString();
  delete result.ops[0]._id;
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
