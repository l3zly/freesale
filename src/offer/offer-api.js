const express = require('express');
const { user, auth, wrap } = require('../middleware');
const { makeOffer, linkImagesToOffer } = require('./offer-handler');
const upload = require('../upload');

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

router.put(
  '/:id/images',
  upload.array('images'),
  wrap(async (req, res) => {
    const imageIds = req.files.map((file) => file.id);
    await linkImagesToOffer(req.params.id, imageIds);
    res.status(200).end();
  })
);

module.exports = router;
