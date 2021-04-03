const HttpError = require('./http-error');

class BadRequestError extends HttpError {
  constructor(errors) {
    super('Bad request', 400, errors);
  }
}

module.exports = BadRequestError;
