const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  phone: {
    type: String,
    unique: true,
    required: [true, 'Phone number required'],
  },
  password: {
    type: String,
    required: [true, 'Password required'],
  },
});

const User = mongoose.model('User', userSchema);

module.exports = User;
