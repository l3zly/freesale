function auth(req, res, next) {
  if (!req.user) {
    throw new Error('Not authenticated');
  }

  next();
}

module.exports = auth;
