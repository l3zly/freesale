const { MongoClient } = require('mongodb');

const uri = 'mongodb://localhost/test';

const client = new MongoClient(uri, {
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
