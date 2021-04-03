const { BeforeAll, Before, AfterAll } = require('@cucumber/cucumber');
const { connect, client } = require('../../db');

BeforeAll(async () => {
  await connect();
});

Before(async () => {
  const collections = await client.db().collections();

  for (let i = 0; i < collections.length; i++) {
    await collections[i].deleteMany({});
  }
});

AfterAll(async () => {
  await client.close();
});
