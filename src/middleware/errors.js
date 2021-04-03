const { HttpError } = require('../errors');

function errors(err, req, res, next) {
  console.error(err);

  if (err instanceof HttpError) {
    return res.status(err.status).json(err.errors);
  }

  res.status(500).json([{ message: 'Something broke!' }]);
}

module.exports = errors;
