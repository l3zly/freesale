const { client } = require('../db');

const collection = 'offers';

async function save(offer) {
  const result = await client.db().collection(collection).insertOne(offer);
  return result.ops[0];
}

module.exports = {
  save,
};
