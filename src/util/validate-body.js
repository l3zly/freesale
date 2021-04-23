const { BadRequestError } = require('../errors');

async function validateBody(schema, body) {
  try {
    await schema.validateAsync(body, { abortEarly: false });
  } catch (e) {
    const errors = e.details.map(({ message, path }) => {
      return { message, path };
    });
    throw new BadRequestError(errors);
  }
}

module.exports = validateBody;
