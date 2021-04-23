const requestSchema = require('./request-schema');
const { save, find, findById, updateById } = require('./request-dal');
const { validateBody } = require('../util');
const { NotFoundError } = require('../errors');

async function makeRequest({ body, user }) {
  body.offerIds = [];
  body.userId = user._id;
  await validateBody(requestSchema, body);
  const request = await save(body);
  return request;
}

async function getRequests() {
  return await find();
}

async function linkOfferToRequest(requestId, offerId) {
  const request = await findById(requestId);
  if (!request) {
    throw new NotFoundError('Request');
  }
  const { offerIds } = request;
  offerIds.push(offerId);
  await updateById(requestId, { offerIds });
}

module.exports = {
  makeRequest,
  getRequests,
  linkOfferToRequest,
};
