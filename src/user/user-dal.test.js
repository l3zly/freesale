const db = require('../db');
const { save } = require('./user-dal');

beforeAll(async () => {
  await db.connect();
});

afterAll(async () => {
  await db.client.db().dropDatabase();
  await db.client.close();
});

describe('user-dal', () => {
  describe('save', () => {
    test('result has _id', async () => {
      const result = await save({
        phone: '+442071838750',
        password: 'password',
      });

      expect(result._id).toBeTruthy();
    });
  });
});
