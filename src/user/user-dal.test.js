const db = require('../db');
const userDal = require('./user-dal');

const phone = '+442071838750';
const password = 'password';
const user = { phone, password };

beforeAll(async () => {
  await db.connect();
});

afterAll(async () => {
  await db.client.db().dropDatabase();
  await db.client.close();
});

describe('UserDal', () => {
  describe('save', () => {
    it('returns user with _id', async () => {
      const result = await userDal.save(user);

      expect(result.phone).toEqual(phone);
      expect(result.password).toEqual(password);
      expect(result._id).toBeTruthy();
    });
  });
});
