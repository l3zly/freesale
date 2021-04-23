const Joi = require('joi');

const offerSchema = Joi.object({
  title: Joi.string().required(),
  amount: Joi.string().required(),
  status: Joi.string().valid('accepted', 'declined', 'pending').required(),
  requestId: Joi.string(),
  userId: Joi.string().required(),
});

module.exports = offerSchema;
