const requestSchema = require('./request-schema');
const { save, find } = require('./request-dal');
const { BadRequestError } = require('../errors');

async function makeRequest({ body, user }) {
  body.offerIds = [];
  body.userId = user._id;
  await validateRequest(body);
  const request = await save(body);
  return request;
}

async function validateRequest(request) {
  try {
    await requestSchema.validateAsync(request, { abortEarly: false });
  } catch (e) {
    const errors = e.details.map(({ message, path }) => {
      return { message, path };
    });
    throw new BadRequestError(errors);
  }
}

async function getRequests() {
  return await find();
}

module.exports = {
  makeRequest,
  getRequests,
};
