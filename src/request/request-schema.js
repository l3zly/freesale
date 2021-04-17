const Joi = require('joi');

const requestSchema = Joi.object({
  title: Joi.string().required(),
  offerIds: Joi.array().items(Joi.string()).required(),
  userId: Joi.string().required(),
});

module.exports = requestSchema;
