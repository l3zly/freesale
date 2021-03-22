const mongoose = require('mongoose');
const app = require('./app');

const port = 3000;

async function start() {
  try {
    await mongoose.connect('mongodb://localhost/test', {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    });
    console.log('Connected to mongo');
  } catch (e) {
    console.error(e);
  }

  app.listen(port, () => {
    console.log(`App listening on port ${port}`);
  });
}
start();
