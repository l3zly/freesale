const { tokenService } = require('../services');

async function user(req, res, next) {
  const token = req.get('Authorization').split('Bearer ')[1];
  if (!token) {
    return next();
  }
  try {
    req.user = await tokenService.decodeToken(token);
  } catch (e) {
  } finally {
    next();
  }
}

module.exports = user;
