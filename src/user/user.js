const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  phone: String,
  password: String,
});

const User = mongoose.model('User', userSchema);

module.exports = User;
