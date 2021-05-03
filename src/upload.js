const GridFsStorage = require('multer-gridfs-storage');
const multer = require('multer');
const { mongoUri: url } = require('./config');

const storage = new GridFsStorage({ url });

const upload = multer({ storage });

module.exports = upload;
