const jwt = require('jsonwebtoken');
const { generateToken } = require('./token-service');

process.env.TOKEN_SECRET = 'shhhhh';

describe('tokenService', () => {
  describe('generateToken', () => {
    it('generates a jwt', async () => {
      const payload = { foo: 'bar' };

      const token = await generateToken(payload);

      const decoded = jwt.verify(token, process.env.TOKEN_SECRET);

      expect(decoded.foo).toEqual(payload.foo);
    });
  });
});
