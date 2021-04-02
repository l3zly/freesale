const { setWorldConstructor } = require('@cucumber/cucumber');

class CustomWorld {
  constructor() {
    this.context = {};
  }
}

setWorldConstructor(CustomWorld);
