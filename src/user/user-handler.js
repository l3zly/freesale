const userSchema = require('./user-schema');
const { save } = require('./user-dal');
const { BadRequestError } = require('../errors');

async function signup({ body }) {
  try {
    await userSchema.validateAsync(body, { abortEarly: false });
  } catch (e) {
    const errors = e.details.map(({ message, path }) => {
      return { message, path };
    });

    throw new BadRequestError(errors);
  }

  return await save(body);
}

module.exports = {
  signup,
};
