const dotenv = require('dotenv');
const path = require('path');

dotenv.config({ path: path.resolve(process.cwd(), 'src', 'config', '.env') });

module.exports = {
  port: process.env.PORT,
  mongoUri: process.env.MONGO_URI,
  tokenSecret: process.env.TOKEN_SECRET,
  mapQuestKey: process.env.MAP_QUEST_KEY,
};
