const db = require('./db');
const app = require('./app');

const port = 3000;

async function start() {
  await db.connect();

  app.listen(port, () => {
    console.log(`App listening on port ${port}`);
  });
}
start();
