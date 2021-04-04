const userSchema = require('./user-schema');
const { save, findByPhone } = require('./user-dal');
const { BadRequestError } = require('../errors');
const { password } = require('../services');

async function signup({ body }) {
  try {
    await userSchema.validateAsync(body, { abortEarly: false });
  } catch (e) {
    const errors = e.details.map(({ message, path }) => {
      return { message, path };
    });

    throw new BadRequestError(errors);
  }

  if (await findByPhone(body.phone)) {
    const errors = [
      {
        message: `A user with the phone number ${body.phone} already exists`,
        path: ['phone'],
      },
    ];

    throw new BadRequestError(errors);
  }

  body.password = await password.encode(body.password);

  return await save(body);
}

module.exports = {
  signup,
};
