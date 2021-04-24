const offerSchema = require('./offer-schema');
const { validateBody } = require('../util');
const { save, deleteById } = require('./offer-dal');
const { requestHandler } = require('../request');

async function makeOffer({ body, user }) {
  body.status = 'pending';
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

module.exports = {
  makeOffer,
};
