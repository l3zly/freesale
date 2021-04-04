const express = require('express');
const { wrap } = require('../middleware');
const { signup, login } = require('./user-handler');

const router = express.Router();

router.post(
  '/',
  wrap(async (req, res) => {
    const { user, token } = await signup({ body: req.body });
    res.status(201).header('Authorization', `Bearer ${token}`).json(user);
  })
);

router.post(
  '/login',
  wrap(async (req, res) => {
    const { user, token } = await login({ body: req.body });
    res.status(200).header('Authorization', `Bearer ${token}`).json(user);
  })
);

module.exports = router;
