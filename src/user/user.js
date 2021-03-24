const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  phone: {
    type: String,
    unique: true,
    required: [true, 'Phone number required'],
    validate: {
      validator: (v) => /^\+[1-9]\d{1,14}$/.test(v),
      message: (props) => `${props.value} is not an international phone number`,
    },
  },
  password: {
    type: String,
    required: [true, 'Password required'],
    minLength: [8, 'Password must be at least 8 characters'],
  },
});

const User = mongoose.model('User', userSchema);

module.exports = User;
