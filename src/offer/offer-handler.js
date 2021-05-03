const offerSchema = require('./offer-schema');
const { validateBody } = require('../util');
const { save, findById, updateById, deleteById } = require('./offer-dal');
const { requestHandler } = require('../request');
const { NotFoundError } = require('../errors');

async function makeOffer({ body, user }) {
  body.status = 'pending';
  body.imageIds = [];
  body.userId = user.id;
  await validateBody(offerSchema, body);
  const offer = await save(body);
  if (offer.requestId) {
    try {
      await requestHandler.linkOfferToRequest(offer.requestId, offer.id);
    } catch (e) {
      await deleteById(offer.id);
      throw e;
    }
  }
  return offer;
}

async function linkImagesToOffer(offerId, imageIds) {
  const offer = await findById(offerId);
  if (!offer) {
    throw new NotFoundError('Offer');
  }
  await updateById(offerId, { imageIds: offer.imageIds.concat(imageIds) });
}

module.exports = {
  makeOffer,
  linkImagesToOffer,
};
