const requestSchema = require('./request-schema');
const { save, find } = require('./request-dal');
const { validateBody } = require('../util');

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

module.exports = {
  makeRequest,
  getRequests,
};
