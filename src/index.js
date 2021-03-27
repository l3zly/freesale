const { MongoClient } = require('mongodb');
const app = require('./app');

async function connectToMongo() {
  const client = new MongoClient('mongodb://localhost/test', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  });

  try {
    await client.connect();
    console.log('Connected to mongo');
  } catch (e) {
    console.error(e);
  }
}

function listen() {
  const port = 3000;

  app.listen(port, () => {
    console.log(`App listening on port ${port}`);
  });
}

async function start() {
  await connectToMongo();
  listen();
}

start();
