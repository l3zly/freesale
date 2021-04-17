const { tokenService } = require('../services');

async function user(req, res, next) {
  const auth = req.get('Authorization');

  if (!auth) {
    return next();
  }
  try {
    req.user = await tokenService.decodeToken(auth.split('Bearer ')[1]);
  } catch (e) {
  } finally {
    next();
  }
}

module.exports = user;
