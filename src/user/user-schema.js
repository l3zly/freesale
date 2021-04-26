const Joi = require('joi');

const userSchema = Joi.object({
  phone: Joi.string()
    .pattern(/^\+[1-9]\d{1,14}$/)
    .required(),
  password: Joi.string().min(8).required(),
  postcode: Joi.string().required(),
  coords: Joi.object({
    lon: Joi.number().required(),
    lat: Joi.number().required(),
  }).required(),
});

module.exports = userSchema;
