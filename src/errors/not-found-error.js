const HttpError = require('./http-error');

class NotFoundError extends HttpError {
  constructor(resource) {
    super('Not found', 404, [{ message: `${resource} not found` }]);
  }
}

module.exports = NotFoundError;
