const express = require('express');
const { user, auth, wrap } = require('../middleware');
const { makeOffer } = require('./offer-handler');

const router = express.Router();

router.use(user, auth);

router.post(
  '/',
  wrap(async (req, res) => {
    const { body, user } = req;
    const offer = await makeOffer({ body, user });
    res.status(201).json(offer);
  })
);

module.exports = router;
