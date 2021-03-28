const db = require('../db');
const userDal = require('./user-dal');

beforeAll(async () => {
  await db.connect();
});

afterAll(async () => {
  await db.client.db().dropDatabase();
  await db.client.close();
});

describe('UserDal', () => {
  describe('save', () => {
    test('result has _id', async () => {
      const result = await userDal.save({
        phone: '+442071838750',
        password: 'password',
      });

      expect(result._id).toBeTruthy();
    });
  });
});
