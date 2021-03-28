const { client } = require('../db');

class UserDal {
  async save(user) {
    const result = await client.db().collection('users').insertOne(user);
    return result.ops[0];
  }
}

module.exports = new UserDal();
