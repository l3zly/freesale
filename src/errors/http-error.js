class HttpError extends Error {
  #status;
  #errors;

  constructor(message, status, errors) {
    super(message);
    this.#status = status;
    this.#errors = errors;
  }

  get status() {
    return this.#status;
  }

  get errors() {
    return this.#errors;
  }
}

module.exports = HttpError;
