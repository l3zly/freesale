const jwt = require('jsonwebtoken');
const { generateToken } = require('./token');

process.env.TOKEN_SECRET = 'shhhhh';

describe('token', () => {
  describe('generateToken', () => {
    it('generates a jwt', async () => {
      const payload = { foo: 'bar' };

      const token = await generateToken(payload);

      const decoded = jwt.verify(token, process.env.TOKEN_SECRET);

      expect(decoded.foo).toEqual(payload.foo);
    });
  });
});
