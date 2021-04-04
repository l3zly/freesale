const userSchema = require('./user-schema');
const { save, findByPhone } = require('./user-dal');
const { BadRequestError, NotFoundError } = require('../errors');
const { passwordService, tokenService } = require('../services');

async function signup({ body }) {
  await validateUser(body);
  await checkPhoneIsUnique(body.phone);
  body.password = await passwordService.encode(body.password);
  const user = await save(body);
  const token = await tokenService.generateToken(user);
  return { user, token };
}

async function validateUser(user) {
  try {
    await userSchema.validateAsync(user, { abortEarly: false });
  } catch (e) {
    const errors = e.details.map(({ message, path }) => {
      return { message, path };
    });
    throw new BadRequestError(errors);
  }
}

async function checkPhoneIsUnique(phone) {
  if (await findByPhone(phone)) {
    const errors = [
      {
        message: `A user with the phone number ${phone} already exists`,
        path: ['phone'],
      },
    ];
    throw new BadRequestError(errors);
  }
}

async function login({ body }) {
  const { phone, password } = body;

  const user = await findByPhone(phone, true);

  if (!user) {
    throw new NotFoundError('User');
  }

  const passwordsMatch = await passwordService.matches(user.password, password);

  if (!passwordsMatch) {
    throw new NotFoundError('User');
  }

  delete user.password;
  const token = await tokenService.generateToken(user);
  return { user, token };
}

module.exports = {
  signup,
  login,
};
