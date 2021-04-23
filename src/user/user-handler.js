const userSchema = require('./user-schema');
const { save, findByPhone, getPassword } = require('./user-dal');
const { BadRequestError, NotFoundError } = require('../errors');
const { passwordService, tokenService } = require('../services');
const { validateBody } = require('../util');

async function signup({ body }) {
  await validateBody(userSchema, body);
  await checkPhoneIsUnique(body.phone);
  body.password = await passwordService.encode(body.password);
  const user = await save(body);
  const token = await tokenService.generateToken(user);
  return { user, token };
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
  const { phone, password: rawPassword } = body;

  const encodedPassword = await getPassword(phone);

  if (!encodedPassword) {
    throw new NotFoundError('User');
  }

  const match = await passwordService.matches(encodedPassword, rawPassword);

  if (!match) {
    throw new NotFoundError('User');
  }

  const user = await findByPhone(phone);
  const token = await tokenService.generateToken(user);
  return { user, token };
}

module.exports = {
  signup,
  login,
};
