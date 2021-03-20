const request = require('supertest');
const app = require('./app');

describe('app', () => {
  describe('GET /', () => {
    it('says hello', async () => {
      await request(app).get('/').expect(200, 'hello');
    });
  });
});
