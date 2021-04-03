const express = require('express');
const { signup } = require('./user-handler');

const router = express.Router();

router.post('/', async (req, res, next) => {
  try {
    const user = await signup({ body: req.body });
    res.status(201).json(user);
  } catch (e) {
    next(e);
  }
});

module.exports = router;
