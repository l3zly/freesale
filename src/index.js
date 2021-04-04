const { port } = require('./config');
const db = require('./db');
const app = require('./app');

async function start() {
  await db.connect();

  app.listen(port, () => {
    console.log(`App listening on port ${port}`);
  });
}
start();
