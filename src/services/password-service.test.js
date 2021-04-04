const { encode, matches } = require('./password-service');

describe('passwordService', () => {
  describe('encode', () => {
    let result;

    beforeAll(async () => {
      result = await encode('password');
    });

    it('returns key and salt', async () => {
      expect(result.split('.')).toHaveLength(2);
    });

    it('derives a 64 byte key', async () => {
      expect(Buffer.byteLength(result.split('.')[0], 'hex')).toBe(64);
    });

    it('generates a 16 byte salt', async () => {
      expect(Buffer.byteLength(result.split('.')[1], 'hex')).toBe(16);
    });
  });

  describe('matches', () => {
    const rawPassword = 'password';
    let encodedPassword;

    beforeAll(async () => {
      encodedPassword = await encode(rawPassword);
    });

    describe('correct password', () => {
      it('returns true', async () => {
        const result = await matches(encodedPassword, rawPassword);
        expect(result).toBe(true);
      });
    });

    describe('incorrect password', () => {
      it('returns false', async () => {
        const result = await matches(encodedPassword, '123');
        expect(result).toBe(false);
      });
    });
  });
});
