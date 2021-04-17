const express = require('express');
const { user, auth, wrap } = require('../middleware');
const { makeRequest, getRequests } = require('./request-handler');

const router = express.Router();

router.use(user, auth);

router.post(
  '/',
  wrap(async (req, res) => {
    const { body, user } = req;
    const request = await makeRequest({ body, user });
    res.status(201).json(request);
  })
);

router.get(
  '/',
  wrap(async (req, res) => {
    const requests = await getRequests({ body: req.body });
    res.status(200).json(requests);
  })
);

module.exports = router;
