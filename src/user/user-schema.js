const Joi = require('joi');

const userSchema = Joi.object({
  phone: Joi.string()
    .pattern(/^\+[1-9]\d{1,14}$/)
    .required(),
  password: Joi.string().min(8).required(),
  postcode: Joi.string()
    .pattern(
      /^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})$/
    )
    .required(),
  coords: Joi.object({
    lon: Joi.number().required(),
    lat: Joi.number().required(),
  }).required(),
});

module.exports = userSchema;
