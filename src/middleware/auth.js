const { UnauthorizedError } = require('../errors');

function auth(req, res, next) {
  if (!req.user) {
    throw new UnauthorizedError();
  }

  next();
}

module.exports = auth;
