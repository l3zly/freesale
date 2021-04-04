const { MongoClient } = require('mongodb');
const { mongoUri } = require('./config');

const client = new MongoClient(mongoUri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

async function connect() {
  try {
    await client.connect();
    console.log('Connected to mongo');
  } catch (e) {
    console.error(e);
  }
}

module.exports = {
  client,
  connect,
};
