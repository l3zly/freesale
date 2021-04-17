const HttpError = require('./http-error');

class UnauthorizedError extends HttpError {
  constructor() {
    super('Unauthorized', 401, [
      { message: 'You are not authorized to access this route' },
    ]);
  }
}

module.exports = UnauthorizedError;
