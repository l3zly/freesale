const auth = require('./auth');

describe('auth', () => {
  let req, res, next;

  beforeEach(() => {
    req = {};
    res = {};
    next = jest.fn();
  });

  describe('when user', () => {
    it('calls next', () => {
      req.user = {};
      auth(req, res, next);
      expect(next).toHaveBeenCalled();
    });
  });

  describe('when no user', () => {
    it('throws error', () => {
      expect(() => {
        auth(req, res, next);
      }).toThrow();
    });

    it('does not call next', () => {
      try {
        auth(req, res, next);
      } catch (e) {
        expect(next).toHaveBeenCalledTimes(0);
      }
    });
  });
});
